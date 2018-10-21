package seedu.address.commons.comparators;

import java.util.Comparator;

import seedu.address.model.event.Event;

/**
 * Comparator use to compare @event by @DateTime objects
 * @return a @Comparator
 */
public class NameComparator implements Comparator<Event> {
    @Override
    public int compare(Event e1, Event e2) {
        return e1.getName().fullName.compareTo(e2.getName().fullName);
    }
}
