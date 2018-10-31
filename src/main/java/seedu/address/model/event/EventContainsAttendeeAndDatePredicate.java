package seedu.address.model.event;

//@@author jieliangang

import java.util.function.Predicate;

/**
 * Tests that a {@code Event}'s  {@code EventDate} matches and {@code Attendee} contains the person name given.
 */
public class EventContainsAttendeeAndDatePredicate implements Predicate<Event> {
    private final String personName;
    private final String date;

    public EventContainsAttendeeAndDatePredicate(String personName, String eventDate) {
        this.personName = personName;
        this.date = eventDate;
    }

    @Override
    public boolean test(Event event) {
        boolean eventHasAttendee = event.hasAttendee(personName);
        boolean eventMatchesDate = event.getDate().eventDate.matches(date);
        return eventHasAttendee && eventMatchesDate;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EventContainsAttendeeAndDatePredicate // instanceof handles nulls
                && personName.equals(((EventContainsAttendeeAndDatePredicate) other).personName) // state check
                && date.equals(((EventContainsAttendeeAndDatePredicate) other).date));
    }
}
