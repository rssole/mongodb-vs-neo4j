package poc.nosql.publishedodds.entities;

import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import poc.nosql.publishedodds.values.EventPopularityData;

import java.util.Date;
import java.util.List;
import java.util.Set;

@NodeEntity
public class Event {

    @GraphId
//    long nodeId;
    Long nodeId; // neo4j spring data support require this not to be of primitive but reference type

    @Indexed(unique = true)
    private String id;

    private String documentType;
    private String name;
    private Integer displayOrder;
    private Date lastModified;
    private String absolutePath;
    private List<String> eventClassIds;
    private Set<EventPopularityData> eventPopularity;

    public Event() {
    }

    public Event(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    public String getAbsolutePath() {
        return absolutePath;
    }

    public void setAbsolutePath(String absolutePath) {
        this.absolutePath = absolutePath;
    }

    public List<String> getEventClassIds() {
        return eventClassIds;
    }

    public void setEventClassIds(List<String> eventClassIds) {
        this.eventClassIds = eventClassIds;
    }

    public Set<EventPopularityData> getEventPopularity() {
        return eventPopularity;
    }

    public void setEventPopularity(Set<EventPopularityData> eventPopularity) {
        this.eventPopularity = eventPopularity;
    }
}
