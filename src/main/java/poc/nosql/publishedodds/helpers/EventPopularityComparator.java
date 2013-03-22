package poc.nosql.publishedodds.helpers;

import poc.nosql.publishedodds.entities.Event;

import java.util.Comparator;

public class EventPopularityComparator implements Comparator<Event> {

    String partnerId;

    public EventPopularityComparator(String partnerId) {
        this.partnerId = partnerId;
    }

    public int compare(Event o1, Event o2) {
        int index1 = o1.getPartnerIds().indexOf(partnerId);
        int index2 = o2.getPartnerIds().indexOf(partnerId);

        int betCount1 = o1.getBetCounts().get(index1);
        int betCount2 = o2.getBetCounts().get(index2);

        if (betCount1 == betCount2) {
            return 0;
        } else if (betCount1 > betCount2) {
            return -1;
        } else {
            return 1;
        }
    }
}
