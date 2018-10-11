package seedu.address.model.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents an event's date in the Student Planner.
 * Guarantees: immutable;
 */

public class Date {

    public static final String MESSAGE_EVENT_DATE_CONSTRAINTS =
            "Event date should only contain numbers, and it should be 8 digits long in DDMMYYYY format";
    public static final String DATE_VALIDATION_REGEX = "\\d{8}";
    public final String value;

    /**
     * Constructs a {@code Date}.
     *
     * @param date A valid event date.
     */
    public Date(String date) {
        requireNonNull(date);
        checkArgument(isValidDate(date), MESSAGE_EVENT_DATE_CONSTRAINTS);
        value = date;
    }

    /**
     * Returns true if a given string is a valid event date.
     */
    public static boolean isValidDate(String test) {
        return test.matches(DATE_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Date // instanceof handles nulls
                && value.equals(((Date) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
