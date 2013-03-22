package poc.nosql.publishedodds.values;

import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;

import javax.xml.bind.annotation.XmlTransient;

@NodeEntity
public class EventPopularityData {

    @XmlTransient
    @GraphId
    private Long graphId;

    private String partnerId;
    private Integer betCount;

    public EventPopularityData() {
    }

    public EventPopularityData(String partnerId, int betCount) {
        this.partnerId = partnerId;
        this.betCount = betCount;
    }

    public Long getGraphId() {
        return graphId;
    }

    public void setGraphId(Long graphId) {
        this.graphId = graphId;
    }

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
