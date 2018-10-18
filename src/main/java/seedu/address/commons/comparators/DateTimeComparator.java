package seedu.address.commons.comparators;

import seedu.address.model.event.Event;

import java.util.Comparator;

public class DateTimeComparator implements Comparator<Event> {
    @Override
    public int compare(Event e1, Event e2) {
        return e1.getDateTime().dateTime.compareTo(e2.getDateTime().dateTime);
    }
}
