package poc.nosql.publishedodds.dataimport;

import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.support.Neo4jTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;
import poc.nosql.publishedodds.entities.Book;
import poc.nosql.publishedodds.entities.Event;
import poc.nosql.publishedodds.entities.Repository;
import poc.nosql.publishedodds.relationships.EventToBookRelationship;
import poc.nosql.publishedodds.relationships.EventToEventPopularityRelationship;
import poc.nosql.publishedodds.relationships.RepositoryToEventRelationship;
import poc.nosql.publishedodds.repositories.BooksRepository;
import poc.nosql.publishedodds.repositories.EventPopularityRepository;
import poc.nosql.publishedodds.repositories.EventsRepository;
import poc.nosql.publishedodds.values.EventPopularityData;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


@ContextConfiguration("classpath:appcontext.xml")
public class MongoDataImportPlayground extends AbstractTestNGSpringContextTests {

    private final SimpleDateFormat sdf = new SimpleDateFormat ("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");

    @Autowired
    MongoClient mongoClient;

    // IntelliJ does not recognize neo4j stuff

    /**
     * @noinspection SpringJavaAutowiringInspection
     */
    @Autowired
    EventsRepository eventsRepository;

    /**
     * @noinspection SpringJavaAutowiringInspection
     */
    @Autowired
    BooksRepository booksRepository;

    /**
     * @noinspection SpringJavaAutowiringInspection
     */
    @Autowired
    Neo4jTemplate neoTemplate;

    /**
     * @noinspection SpringJavaAutowiringInspection
     */
    @Autowired
    EventPopularityRepository popularityRepository;

    /**
     * @noinspection SpringJavaAutowiringInspection
     */
    @Autowired
    GraphDatabaseService databaseService;


    @Test
    public void shouldImportMongoData() throws IOException, ParseException {
        Assert.assertNotNull(databaseService);
        DBCursor eventsCursor = mongoClient.getDB("podds").getCollection("events").find();
        DBCursor booksCursor = mongoClient.getDB("podds").getCollection("books").find();

        Iterator<DBObject> iterator = eventsCursor.iterator();

        Repository r = new Repository("WPF");

        Transaction transaction = databaseService.beginTx();
        try {
            neoTemplate.save(r);
            List<Event> eventsList = new ArrayList<Event>();
            while (iterator.hasNext()) {
                DBObject eventDbObject = iterator.next();
                Event e = fromDBObject(eventDbObject);

                eventsRepository.save(e);
                eventsList.add(e);
                neoTemplate.createRelationshipBetween(r, e, RepositoryToEventRelationship.class, RepositoryToEventRelationship.REPOSITORY_CONTAINS_EVENT, false);

                List<EventPopularityData> eventPopularityList = getEventPopularity(eventDbObject);
                if (eventPopularityList.size() > 0) {
                    for (EventPopularityData epd : eventPopularityList) {
                        popularityRepository.save(epd);
                        neoTemplate.createRelationshipBetween(e, epd,
                                EventToEventPopularityRelationship.class, EventToEventPopularityRelationship.HAS_POPULARITY_DATA, true);
                    }
                }
            }

            iterator = booksCursor.iterator();
            while (iterator.hasNext()) {
                DBObject bookDBObject = iterator.next();
                Book aBook = createBookFromDBObject(bookDBObject);
                booksRepository.save(aBook);
                addEventToBookRelationship(aBook, eventsList);
            }

            Assert.assertEquals(eventsList.size(), 1252, "Number of events must match number of documents in mongodb");
            transaction.success();
        } finally {
            transaction.finish();
        }

        transaction = databaseService.beginTx();
    }

    private void addEventToBookRelationship(Book aBook, List<Event> eventsList) {
        Collections.sort(eventsList);
        int index = Collections.binarySearch(eventsList, new Event(aBook.getEventid(), ""));

        Event event = eventsList.get(index);
        neoTemplate.createRelationshipBetween(event, aBook, EventToBookRelationship.class, EventToBookRelationship.HAS_BOOK, false);
    }

    private Book createBookFromDBObject(DBObject bookDBObject) throws ParseException {
        String sportId = (String) bookDBObject.get("sportId");
        String eventId = (String) bookDBObject.get("eventId");
        Boolean isForInRunning = Boolean.parseBoolean((String) bookDBObject.get("isForInRunning"));
        Boolean isForStreaming = Boolean.parseBoolean((String) bookDBObject.get("isForStreaming"));
        Date startDateTime =  sdf.parse((String) bookDBObject.get("startDatetime"));
        Boolean isVisible = Boolean.parseBoolean((String) bookDBObject.get("isVisible"));

        Object inRunningDelay = bookDBObject.get("inRunningDelay");
        Integer inRunningDelayNumber = null;
        if (inRunningDelay != null && !"".equals(inRunningDelay)) {
            inRunningDelayNumber = Integer.parseInt((String) inRunningDelay);
        }

        Book b = new Book(sportId, eventId);
        b.setForInRunning(isForInRunning);
        b.setForStreaming(isForStreaming);
        b.setStartDateTime(startDateTime);
        b.setVisible(isVisible);
        b.setInRunningDelay(inRunningDelayNumber);

        return b;
    }

    private static List<EventPopularityData> getEventPopularity(DBObject mongoDbObject) {
        @SuppressWarnings("unchecked")
        List<DBObject> eventPopularities = (List<DBObject>) mongoDbObject.get("eventPopularity");
        List<EventPopularityData> eventPopularityDataList = new ArrayList<EventPopularityData>();

        for (DBObject evPopularity : eventPopularities) {
            EventPopularityData epd =
                    new EventPopularityData((String) evPopularity.get("partnerId"), Integer.parseInt((String) evPopularity.get("betCount")));
            eventPopularityDataList.add(epd);
        }

        return eventPopularityDataList;
    }

    private static Event fromDBObject(DBObject mongoDbObject) {
        String id = (String) mongoDbObject.get("id");
        String name = (String) mongoDbObject.get("name");
        Integer displayOrder = Integer.parseInt((String) mongoDbObject.get("displayOrder"));
        Date lastModified = (Date) mongoDbObject.get("lastModified");
        String absolutePath = (String) mongoDbObject.get("absoluteFilePath");

        @SuppressWarnings("unchecked")
        List<String> eventClassIds = (List<String>) mongoDbObject.get("eventClassIds");

        Event e = new Event(id, name);
        e.setDisplayOrder(displayOrder);
        e.setLastModified(lastModified);
        e.setAbsolutePath(absolutePath);
        e.setEventClassIds(eventClassIds);

        return e;
    }
}
