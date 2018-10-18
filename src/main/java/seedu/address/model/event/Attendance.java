package seedu.address.model.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Event's attendance in the event manager.
 * Guarantees: immutable; is valid as declared in {@link #isValidAttendance(String)}
 */
public class Attendance {


    public static final String MESSAGE_ATTENDANCE_CONSTRAINTS =
            "Attendance should be a Boolean value of 'true' or 'false'.";
    public final Boolean value;

    /**
     * Constructs an {@code Attendance}.
     *
     * @param attendance A valid attendance.
     */
    public Attendance(String attendance) {
        requireNonNull(attendance);
        checkArgument(isValidAttendance(attendance), MESSAGE_ATTENDANCE_CONSTRAINTS);
        value = Boolean.valueOf(attendance);
    }

    public Attendance(Boolean attendance) {
        requireNonNull(attendance);
        value = attendance;
    }

    /**
     * Returns true if a given string is a valid attendance status.
     */
    public static boolean isValidAttendance(String test) {
        return Boolean.toString(true).equals(test) || Boolean.toString(false).equals(test);
    }

    @Override
    public String toString() {
        return Boolean.toString(value);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Attendance // instanceof handles nulls
                && value.equals(((Attendance) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
