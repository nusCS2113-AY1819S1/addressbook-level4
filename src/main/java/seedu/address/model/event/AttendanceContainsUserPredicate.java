package seedu.address.model.event;

import java.util.function.Predicate;

import seedu.address.model.user.User;

/**
 * Predicate to find user with given name
 */
public class AttendanceContainsUserPredicate implements Predicate<Event> {
    private final String username;

    public AttendanceContainsUserPredicate(User name) {
        this.username = name.getUsername().value;
    }
    //Todo: to reimplement later

    //Todo: To reimplement later when Attendance field is add
    @Override
    public boolean test (Event event) {
        return true;
        //username.equalsIgnoreCase(event.getAttendance().toString());
    }
}
