package poc.nosql.publishedodds.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import poc.nosql.publishedodds.entities.Event;
import poc.nosql.publishedodds.repositories.EventsRepository;
import poc.nosql.publishedodds.values.EventsList;

import java.util.List;

@Controller
@RequestMapping("/events")
public class EventsController {

    // IntelliJ does not recognize Neo4j spring data stuff
    /**
     * @noinspection SpringJavaAutowiringInspection
     */
    @Autowired
    EventsRepository eventsRepository;

//    @RequestMapping(method = RequestMethod.GET)
//    public ModelAndView singleEvent() {
//        List<Event> events = Arrays.asList(new Event[]{new Event()});
//        return new ModelAndView("events", "events", events);
//    }

    @RequestMapping(value = "/{partnerId}", method = RequestMethod.GET)
    public ModelAndView fetchEventsForPartner(@PathVariable String partnerId) {
        List<Event> eventsByPartnerId = eventsRepository.findByPartnerId(partnerId);

        return new ModelAndView("xmlViewer", "events", new EventsList(eventsByPartnerId));
    }
}
