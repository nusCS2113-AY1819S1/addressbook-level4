package seedu.address.model.person;

import java.util.function.Predicate;

import seedu.address.model.event.Event;

/**
 * Tests that a {@code Person} is within an event's list of attendees
 */
public class PersonAttendingEventPredicate implements Predicate<Person> {
    private final Event event;

    public PersonAttendingEventPredicate(Event event) {
        this.event = event;
    }

    @Override
    public boolean test(Person person) {
        return event.hasAttendee(person.getEmail().toString());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PersonAttendingEventPredicate // instanceof handles nulls
                && event.equals(((PersonAttendingEventPredicate) other).event)); // state check
    }

}
