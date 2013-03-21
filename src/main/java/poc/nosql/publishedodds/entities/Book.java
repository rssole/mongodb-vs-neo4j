package poc.nosql.publishedodds.entities;

import org.springframework.data.neo4j.annotation.NodeEntity;

@NodeEntity
public class Book {
    private String documentType;
    private boolean isForInRunning;
    private boolean isForStreaming;
    private String startDateTime;
    private boolean isVisible;
    private int sportId;
    private int eventId;
    private int inRunningDelay;

    public Book() {
    }

    public Book(int sportId, int eventId) {
        this.sportId = sportId;
        this.eventId = eventId;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
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

    public String getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(String startDateTime) {
        this.startDateTime = startDateTime;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    public int getSportId() {
        return sportId;
    }

    public void setSportId(int sportId) {
        this.sportId = sportId;
    }

    public int getEventid() {
        return eventId;
    }

    public void setEventid(int eventid) {
        this.eventId = eventid;
    }

    public int getInRunningDelay() {
        return inRunningDelay;
    }

    public void setInRunningDelay(int inRunningDelay) {
        this.inRunningDelay = inRunningDelay;
    }
}
