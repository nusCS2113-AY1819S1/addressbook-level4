package seedu.address.model.event;

import java.util.function.Predicate;

//@@author jieliangang
/**
 * Tests that a {@code Event}'s {@code Attendee} contains the person email given.
 */
public class AttendeeContainsEmailPredicate implements Predicate<Event> {
    private final String personEmail;

    public AttendeeContainsEmailPredicate(String personEmail) {
        this.personEmail = personEmail;
    }

    @Override
    public boolean test(Event event) {
        return event.hasAttendee(personEmail);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AttendeeContainsEmailPredicate // instanceof handles nulls
                && personEmail.equals(((AttendeeContainsEmailPredicate) other).personEmail)); // state check
    }
}
