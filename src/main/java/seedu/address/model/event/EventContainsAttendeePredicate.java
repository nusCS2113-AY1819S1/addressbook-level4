package seedu.address.model.event;

import java.util.function.Predicate;

/**
 * Tests that a {@code Event}'s {@code Attendee} contains the person name given.
 */
public class EventContainsAttendeePredicate implements Predicate<Event> {
    private final String personName;

    public EventContainsAttendeePredicate(String personName) {
        this.personName = personName;
    }

    @Override
    public boolean test(Event event) {
        return event.getAttendees().hasName(personName);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EventContainsAttendeePredicate // instanceof handles nulls
                && personName.equals(((EventContainsAttendeePredicate) other).personName)); // state check
    }
}
