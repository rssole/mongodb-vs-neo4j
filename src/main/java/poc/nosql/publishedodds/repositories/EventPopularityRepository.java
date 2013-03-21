package poc.nosql.publishedodds.repositories;

import org.springframework.data.neo4j.repository.GraphRepository;
import poc.nosql.publishedodds.values.EventPopularityData;

public interface EventPopularityRepository extends GraphRepository<EventPopularityData> {
}
