package seedu.address.model.event;

import java.util.function.Predicate;

/**
 * Tests that an {@code Event} clashes a {@code personName}'s {@code Event}
 */
public class EventClashPredicate implements Predicate<Event> {
    private final Event personEvent;
    private final String personEmail;

    public EventClashPredicate(Event personEvent, String personEmail) {
        this.personEvent = personEvent;
        this.personEmail = personEmail;
    }

    @Override
    public boolean test(Event event) {
        return !event.isSameEvent(personEvent) && event.hasAttendee(personEmail) && event.hasClash(personEvent);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EventClashPredicate // instanceof handles nulls
                && personEmail.equals(((EventClashPredicate) other).personEmail)
                && personEvent.equals(((EventClashPredicate) other).personEvent)); // state check
    }

}
