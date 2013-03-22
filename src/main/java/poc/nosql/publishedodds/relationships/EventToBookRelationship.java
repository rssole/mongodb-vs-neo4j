package poc.nosql.publishedodds.relationships;

import org.springframework.data.neo4j.annotation.EndNode;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.RelationshipEntity;
import org.springframework.data.neo4j.annotation.StartNode;
import poc.nosql.publishedodds.entities.Book;
import poc.nosql.publishedodds.entities.Event;

@RelationshipEntity(type = EventToBookRelationship.HAS_BOOK)
public class EventToBookRelationship {
    public static final String HAS_BOOK = "HAS_BOOK";

    @GraphId
    private Long graphId;

    @StartNode
    private Event event;

    @EndNode
    private Book book;

    public EventToBookRelationship() {
    }

    public EventToBookRelationship(Event event, Book book) {
        this.event = event;
        this.book = book;
    }

    public Long getGraphId() {
        return graphId;
    }

    public void setGraphId(Long graphId) {
        this.graphId = graphId;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }
}
