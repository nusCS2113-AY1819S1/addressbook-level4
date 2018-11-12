package seedu.address.model.event;

import java.util.function.Predicate;

/**
 * Tests that an edited {@code Event} clashes with a {@code personEmail}'s {@code Event}
 */
public class EventClashAfterEditPredicate implements Predicate<Event> {
    private final Event eventBeforeEdit;
    private final Event eventAfterEdit;
    private final String personEmail;

    public EventClashAfterEditPredicate(Event eventBeforeEdit, Event eventAfterEdit, String personEmail) {
        this.eventBeforeEdit = eventBeforeEdit;
        this.eventAfterEdit = eventAfterEdit;
        this.personEmail = personEmail;
    }

    @Override
    public boolean test(Event event) {
        boolean isEventBeforeEdit = event.equals(eventBeforeEdit);
        boolean hasAttendee = event.hasAttendee(personEmail);
        boolean hasClash = event.hasClash(eventAfterEdit);
        return !isEventBeforeEdit && hasAttendee && hasClash;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EventClashAfterEditPredicate // instanceof handles nulls
                && personEmail.equals(((EventClashAfterEditPredicate) other).personEmail)
                && eventBeforeEdit.equals(((EventClashAfterEditPredicate) other).eventBeforeEdit)
                && eventAfterEdit.equals(((EventClashAfterEditPredicate) other).eventAfterEdit)); // state check
    }
}
