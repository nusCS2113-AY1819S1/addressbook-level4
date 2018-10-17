package seedu.address.model.attendance;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents an Attendance in the address book.
 * Guarantees: immutable; user name is valid as declared in {@link #isValidAttendeeName(String)}
 */
public class Attendance {

    public static final String MESSAGE_ATTENDANCE_CONSTRAINTS = "Tags names should be alphanumeric";
    public static final String ATTENDANCE_VALIDATION_REGEX = "\\p{Alnum}+";

    public final String attendeeName;

    /**
     * Constructs a {@code Attendance}.
     *
     * @param attendeeName A valid Attendance name.
     */
    public Attendance(String attendeeName) {
        requireNonNull(attendeeName);
        checkArgument(isValidAttendeeName(attendeeName), MESSAGE_ATTENDANCE_CONSTRAINTS);
        this.attendeeName = attendeeName;
    }

    /**
     * Returns true if a given string is a valid tag name.
     */
    public static boolean isValidAttendeeName(String test) {
        return test.matches(ATTENDANCE_VALIDATION_REGEX);
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

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return '[' + attendeeName + ']';
    }

}
