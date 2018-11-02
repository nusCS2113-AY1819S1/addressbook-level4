package seedu.address.model.event;

import java.util.function.Predicate;

/**
 * Tests that an {@code Event} clashes a {@code personName}'s {@code Event}
 */
public class EventClashPredicate implements Predicate<Event> {
    private final Event personEvent;
    private final String personName;

    public EventClashPredicate(Event personEvent, String personName) {
        this.personEvent = personEvent;
        this.personName = personName;
    }

    @Override
    public boolean test(Event event) {
        return !event.isSameEvent(personEvent) && event.hasAttendee(personName) && event.hasClash(personEvent);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EventClashPredicate // instanceof handles nulls
                && personName.equals(((EventClashPredicate) other).personName)
                && personEvent.equals(((EventClashPredicate) other).personEvent)); // state check
    }

}
