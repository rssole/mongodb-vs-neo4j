package poc.nosql.publishedodds.repositories;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import poc.nosql.publishedodds.entities.Event;

import java.util.List;

public interface EventsRepository extends GraphRepository<Event> {
    @Query("START ev=node:Event(\"*:*\") " +
            "WHERE has(ev.betCounts) AND ({0} IN ev.partnerIds) " +
            "RETURN ev")
    public List<Event> findByPartnerId(String partnerId);
}
