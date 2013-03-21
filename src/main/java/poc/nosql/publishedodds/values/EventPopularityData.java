package poc.nosql.publishedodds.values;

public class EventPopularityData {

    private String partnerId;
    private Integer betCount;

    public EventPopularityData() {
    }

    public EventPopularityData(String partnerId, int betCount) {
        this.partnerId = partnerId;
        this.betCount = betCount;
    }

//    public Long getNodeId() {
//        return nodeId;
//    }

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public int getBetCount() {
        return betCount;
    }

    public void setBetCount(Integer betCount) {
        this.betCount = betCount;
    }
}
