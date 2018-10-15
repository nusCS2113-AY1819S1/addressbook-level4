package seedu.address.model.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Event's end time in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidTime(String)}
 */
public class EndTime implements Comparable<EndTime> {

    public static final String MESSAGE_TIME_CONSTRAINTS =
            "End time should only be given in HH:MM in 24 hour format";

    /*
     * The first character of the start time must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String TIME_VALIDATION_REGEX = "^(0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$";

    public final String endTime;

    /**
     * Constructs a {@code Name}.
     *
     * @param time A valid time.
     */
    public EndTime(String time) {
        requireNonNull(time);
        checkArgument(isValidTime(time), MESSAGE_TIME_CONSTRAINTS);
        endTime = time;
    }

    /**
     * Returns true if a given string is a valid time.
     */
    public static boolean isValidTime(String test) {
        return test.matches(TIME_VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return endTime;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EndTime // instanceof handles nulls
                && endTime.equals(((EndTime) other).endTime)); // state check
    }

    @Override
    public int hashCode() {
        return endTime.hashCode();
    }

    @Override
    public int compareTo(EndTime other) {
        return this.endTime.compareTo(other.endTime);
    }

}
