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
import poc.nosql.publishedodds.entities.Event;
import poc.nosql.publishedodds.entities.Repository;
import poc.nosql.publishedodds.relationships.EventToEventPopularityRelationship;
import poc.nosql.publishedodds.relationships.RepositoryToEventRelationship;
import poc.nosql.publishedodds.repositories.BooksRepository;
import poc.nosql.publishedodds.repositories.EventPopularityRepository;
import poc.nosql.publishedodds.repositories.EventsRepository;
import poc.nosql.publishedodds.values.EventPopularityData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;


@ContextConfiguration("classpath:appcontext.xml")
public class MongoDataImportPlayground extends AbstractTestNGSpringContextTests {

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
    public void shouldImportMongoData() throws IOException {
        Assert.assertNotNull(databaseService);
        DBCursor eventsCursor = mongoClient.getDB("podds").getCollection("events").find();
        Iterator<DBObject> iterator = eventsCursor.iterator();

        Repository r = new Repository("WPF");

        Transaction transaction = databaseService.beginTx();

        try {
            neoTemplate.save(r);
            List<Event> eventsList = new ArrayList<Event>();
            while (iterator.hasNext()) {
                DBObject eventDbObject = iterator.next();
                Event e = fromDBObject(eventDbObject);
                eventsList.add(e);
                eventsRepository.save(e);

                neoTemplate.createRelationshipBetween(r, e, RepositoryToEventRelationship.class, RepositoryToEventRelationship.REPOSITORY_CONTAINS_EVENT, false);

                List<EventPopularityData> eventPopularityList = getEventPopularity(eventDbObject);
                for (EventPopularityData popularityData : eventPopularityList) {
                    popularityRepository.save(popularityData);
                    neoTemplate.createRelationshipBetween(e, popularityData,
                            EventToEventPopularityRelationship.class,
                            EventToEventPopularityRelationship.HAS_POPULARITY_DATA,
                            true);
                }
            }

            Assert.assertEquals(eventsList.size(), 1252, "Number of events must match number of documents in mongodb");
            transaction.success();
        } finally {
            transaction.finish();
        }
    }

    private static List<EventPopularityData> getEventPopularity(DBObject mongoDbObject) {
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
        List<String> eventClassIds = (List<String>) mongoDbObject.get("eventClassIds");

        Event e = new Event(id, name);
        e.setDisplayOrder(displayOrder);
        e.setLastModified(lastModified);
        e.setAbsolutePath(absolutePath);
        e.setEventClassIds(eventClassIds);

        return e;
    }
}
