package poc.nosql.publishedodds.relationships;

import org.springframework.data.neo4j.annotation.EndNode;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.RelationshipEntity;
import org.springframework.data.neo4j.annotation.StartNode;
import poc.nosql.publishedodds.entities.Event;
import poc.nosql.publishedodds.entities.Repository;

@RelationshipEntity(type = RepositoryToEventRelationship.REPOSITORY_CONTAINS_EVENT)
public class RepositoryToEventRelationship {

    public static final String REPOSITORY_CONTAINS_EVENT = "REPOSITORY_CONTAINS_EVENT";

    @GraphId
    private Long graphId;

    @StartNode
    private Repository repository;

    @EndNode
    private Event event;

    public RepositoryToEventRelationship() {
    }

    public RepositoryToEventRelationship(Repository repo, Event event) {
        this.repository = repo;
        this.event = event;
    }

    public Long getGraphId() {
        return graphId;
    }

    public void setGraphId(Long graphId) {
        this.graphId = graphId;
    }

    public Repository getRepository() {
        return repository;
    }

    public void setRepository(Repository repository) {
        this.repository = repository;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }
}
