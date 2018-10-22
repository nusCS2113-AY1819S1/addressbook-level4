package seedu.address.model.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a user's attendance in the event manager.
 * Guarantees: immutable; is valid as declared in {@link #isValidAttendance(String)}
 */
public class Attendance {

    public static final String MESSAGE_ATTENDANCE_CONSTRAINTS =
            "Attendee names should be alphanumeric and without spaces.";
    public final String attendeeName;

    /**
     * Constructs an {@code Attendance}.
     *
     * @param attendeeName A valid attendance.
     */
    public Attendance(String attendeeName) {
        requireNonNull(attendeeName);
        checkArgument(isValidAttendance(attendeeName), MESSAGE_ATTENDANCE_CONSTRAINTS);
        this.attendeeName = attendeeName;
    }

    /**
     * Returns true if a given string is a valid attendance status.
     */
    public static boolean isValidAttendance(String test) {
        return true;    //TODO
    }

    @Override
    public String toString() {
        return attendeeName;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Attendance // instanceof handles nulls
                && attendeeName.equals(((Attendance) other).attendeeName)); // state check
    }

    @Override
    public int hashCode() {
        return attendeeName.hashCode();
    }

}
