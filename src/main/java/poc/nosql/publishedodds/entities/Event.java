package poc.nosql.publishedodds.entities;

import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;

import java.util.Date;
import java.util.List;

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

    @Indexed
    private List<String> partnerIds;

    @Indexed
    private List<Integer> betCounts;

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

    public List<String> getPartnerIds() {
        return partnerIds;
    }

    public void setPartnerIds(List<String> partnerIds) {
        this.partnerIds = partnerIds;
    }

    public List<Integer> getBetCounts() {
        return betCounts;
    }

    public void setBetCounts(List<Integer> betCounts) {
        this.betCounts = betCounts;
    }
}
