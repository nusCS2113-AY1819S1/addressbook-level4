package seedu.address.model.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Event's start time in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidTime(String)}
 */
public class StartTime implements Comparable<StartTime> {

    public static final String MESSAGE_TIME_CONSTRAINTS =
            "Start time should only be given in HH:MM in 24 hour format";

    /*
     * The first character of the start time must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String TIME_VALIDATION_REGEX = "^(0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$";

    public final String startTime;

    /**
     * Constructs a {@code StartTime}.
     *
     * @param time A valid time.
     */
    public StartTime(String time) {
        requireNonNull(time);
        checkArgument(isValidTime(time), MESSAGE_TIME_CONSTRAINTS);
        startTime = time;
    }

    /**
     * Returns true if a given string is a valid time.
     */
    public static boolean isValidTime(String test) {
        return test.matches(TIME_VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return startTime;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof StartTime // instanceof handles nulls
                && startTime.equals(((StartTime) other).startTime)); // state check
    }

    @Override
    public int hashCode() {
        return startTime.hashCode();
    }

    @Override
    public int compareTo(StartTime other) {
        return this.startTime.compareTo(other.startTime);
    }
}
