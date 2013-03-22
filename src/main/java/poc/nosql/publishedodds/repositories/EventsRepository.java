package poc.nosql.publishedodds.repositories;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import poc.nosql.publishedodds.entities.Event;

import java.util.List;

public interface EventsRepository extends GraphRepository<Event> {
    @Query(value = "START ev=node:Event(\"*:*\") " +
            "MATCH ev-[r:HAS_POPULARITY_DATA]->evPop " +
            "WHERE evPop.partnerId = {0} " +
            "RETURN ev " +
            "ORDER BY evPop.betCount DESC")
    public List<Event> findByPartnerId(String partnerId);
}
