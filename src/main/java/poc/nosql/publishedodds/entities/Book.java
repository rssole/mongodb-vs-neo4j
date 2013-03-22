package poc.nosql.publishedodds.entities;

import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;

import javax.xml.bind.annotation.XmlTransient;
import java.util.Date;

@NodeEntity
public class Book {

    @GraphId
    @XmlTransient
    private Long graphId;

    private boolean isForInRunning;
    private boolean isForStreaming;
    private Date startDateTime;
    private boolean isVisible;
    private String sportId;
    @Indexed
    private String eventId;
    private Integer inRunningDelay;

    public Book() {
    }

    public Book(String sportId, String eventId) {
        this.sportId = sportId;
        this.eventId = eventId;
    }

    public Long getGraphId() {
        return graphId;
    }

    public void setGraphId(Long graphId) {
        this.graphId = graphId;
    }

    public boolean isForInRunning() {
        return isForInRunning;
    }

    public void setForInRunning(boolean forInRunning) {
        isForInRunning = forInRunning;
    }

    public boolean isForStreaming() {
        return isForStreaming;
    }

    public void setForStreaming(boolean forStreaming) {
        isForStreaming = forStreaming;
    }

    public Date getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(Date startDateTime) {
        this.startDateTime = startDateTime;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    public String getSportId() {
        return sportId;
    }

    public void setSportId(String sportId) {
        this.sportId = sportId;
    }

    public String getEventid() {
        return eventId;
    }

    public void setEventid(String eventid) {
        this.eventId = eventid;
    }

    public int getInRunningDelay() {
        return inRunningDelay;
    }

    public void setInRunningDelay(Integer inRunningDelay) {
        this.inRunningDelay = inRunningDelay;
    }
}
