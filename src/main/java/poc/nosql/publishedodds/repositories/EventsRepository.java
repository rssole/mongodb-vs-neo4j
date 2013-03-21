package poc.nosql.publishedodds.repositories;

import org.springframework.data.neo4j.repository.GraphRepository;
import poc.nosql.publishedodds.entities.Event;

public interface EventsRepository extends GraphRepository<Event> {
}
