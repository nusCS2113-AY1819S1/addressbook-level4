package seedu.address.model.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents an event's date in the Student Planner.
 * Guarantees: immutable;
 */

public class Time {

    public static final String MESSAGE_EVENT_TIME_CONSTRAINTS =
            "Event time should only contain numbers, and it should be 4 digits long in the 24-hour format";
    public static final String TIME_VALIDATION_REGEX = "\\d{4}";
    public final String value;

    /**
     * Constructs a {@code Time}.
     *
     * @param time A valid event time.
     */
    public Time(String time) {
        requireNonNull(time);
        checkArgument(isValidTime(time), MESSAGE_EVENT_TIME_CONSTRAINTS);
        value = time;
    }

    /**
     * Returns true if a given string is a valid event time.
     */
    public static boolean isValidTime(String test) {
        return test.matches(TIME_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Time // instanceof handles nulls
                && value.equals(((Time) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
