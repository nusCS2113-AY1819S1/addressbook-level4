package seedu.address.model.person.exceptions;

/**
 * Signals that the {@code TimeTable} is empty and does not have an earliest or latest time.
 */
public class TimeTableEmptyException extends RuntimeException {
    public TimeTableEmptyException() {
        super("The timetable is empty!");
    }
}
