package seedu.address.model.attendee;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents an Attendee in the event manager.
 * Guarantees: immutable; attendee name is valid as declared in {@link #isValidAttendeeName(String)}
 */
public class Attendee {

    public static final String MESSAGE_ATTENDEE_CONSTRAINTS =
            "Attendee names should only contain alphanumeric characters and spaces";
    public static final String ATTENDEE_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String attendeeName;

    /**
     * Constructs a {@code Attendee}.
     *
     * @param attendeeName A valid Attendee name.
     */
    public Attendee(String attendeeName) {
        requireNonNull(attendeeName);
        checkArgument(isValidAttendeeName(attendeeName), MESSAGE_ATTENDEE_CONSTRAINTS);
        this.attendeeName = attendeeName;
    }

    /**
     * Returns true if a given string is a valid attendee name.
     */
    public static boolean isValidAttendeeName(String test) {
        return test.matches(ATTENDEE_VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Attendee // instanceof handles nulls
                && attendeeName.equals(((Attendee) other).attendeeName)); // state check
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
