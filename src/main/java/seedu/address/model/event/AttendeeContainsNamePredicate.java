package seedu.address.model.event;

import java.util.function.Predicate;

//@@author jieliangang
/**
 * Tests that a {@code Event}'s {@code Attendee} contains the person name given.
 */
public class AttendeeContainsNamePredicate implements Predicate<Event> {
    private final String personName;

    public AttendeeContainsNamePredicate(String personName) {
        this.personName = personName;
    }

    @Override
    public boolean test(Event event) {
        return event.hasAttendee(personName);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AttendeeContainsNamePredicate // instanceof handles nulls
                && personName.equals(((AttendeeContainsNamePredicate) other).personName)); // state check
    }
}
