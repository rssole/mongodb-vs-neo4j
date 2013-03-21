package poc.nosql.publishedodds.relationships;

import org.springframework.data.neo4j.annotation.EndNode;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.RelationshipEntity;
import org.springframework.data.neo4j.annotation.StartNode;
import poc.nosql.publishedodds.entities.Event;
import poc.nosql.publishedodds.values.EventPopularityData;

@RelationshipEntity(type = EventToEventPopularityRelationship.HAS_POPULARITY_DATA)
public class EventToEventPopularityRelationship {

    public static final String HAS_POPULARITY_DATA = "HAS_POPULARITY_DATA";

    @GraphId
    private Long graphId;

    @StartNode
    private Event event;

    @EndNode
    private EventPopularityData popularityData;

    public EventToEventPopularityRelationship() {
    }

    public EventToEventPopularityRelationship(Event event, EventPopularityData popularityData) {
        this.event = event;
        this.popularityData = popularityData;
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

    public EventPopularityData getPopularityData() {
        return popularityData;
    }

    public void setPopularityData(EventPopularityData popularityData) {
        this.popularityData = popularityData;
    }
}
