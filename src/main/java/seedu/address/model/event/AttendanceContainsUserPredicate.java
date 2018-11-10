package seedu.address.model.event;

import java.util.function.Predicate;

import seedu.address.model.attendee.Attendee;
import seedu.address.model.user.Username;

/**
 * Predicate to find user with given name
 */
public class AttendanceContainsUserPredicate implements Predicate<Event> {
    private final String username;

    public AttendanceContainsUserPredicate(Username userName) {
        this.username = userName.value;
    }

    @Override
    public boolean test (Event event) {
        return event.getAttendance().contains(new Attendee(username));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AttendanceContainsUserPredicate // instanceof handles nulls
                && username.equals(((AttendanceContainsUserPredicate) other).username)); // state check
    }
}
