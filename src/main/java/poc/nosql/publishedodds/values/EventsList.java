package poc.nosql.publishedodds.values;

import poc.nosql.publishedodds.entities.Event;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: rsoskic
 * Date: 21/03/13
 * Time: 16:28
 * To change this template use File | Settings | File Templates.
 */
@XmlRootElement(name = "events")
public class EventsList {

    @XmlElement(name = "event")
    private List<Event> events;

    public EventsList() {
    }

    public EventsList(List<Event> events) {
        this.events = events;
    }
}
