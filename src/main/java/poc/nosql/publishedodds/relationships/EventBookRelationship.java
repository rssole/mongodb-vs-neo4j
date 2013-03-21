package poc.nosql.publishedodds.relationships;

import org.springframework.data.neo4j.annotation.EndNode;
import org.springframework.data.neo4j.annotation.RelationshipEntity;
import org.springframework.data.neo4j.annotation.StartNode;
import poc.nosql.publishedodds.entities.Book;
import poc.nosql.publishedodds.entities.Event;

@RelationshipEntity(type = "HAS_BOOK")
public class EventBookRelationship {
    @StartNode
    Event event;

    @EndNode
    Book book;

    public EventBookRelationship() {
    }

    public EventBookRelationship(Event event, Book book) {
        this.event = event;
        this.book = book;
    }
}
