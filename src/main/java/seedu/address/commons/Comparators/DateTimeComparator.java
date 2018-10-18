package seedu.address.commons.Comparators;

import java.util.Comparator;

import seedu.address.model.event.Event;

public class DateTimeComparator implements Comparator<Event> {
    @Override
    public int compare(Event e1, Event e2) {
        return e1.getDateTime().dateTime.compareTo(e2.getDateTime().dateTime);
    }
}
