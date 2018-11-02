package seedu.address.model.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Event's time in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidTime(String)}
 */
public class Time implements Comparable<Time> {

    public static final String MESSAGE_TIME_CONSTRAINTS =
            "Time should only be given in HH:MM in 24 hour format and valid";

    /*
     * Input should adhere to the standard HH:MM 24 hour format.
     */
    public static final String TIME_VALIDATION_REGEX = "^(0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$";

    public final String time;

    /**
     * Constructs a {@code Time}.
     *
     * @param inputTime A valid time.
     */
    public Time(String inputTime) {
        requireNonNull(inputTime);
        checkArgument(isValidTime(inputTime), MESSAGE_TIME_CONSTRAINTS);
        time = inputTime;
    }

    /**
     * Returns true if a given string is a valid time.
     */
    public static boolean isValidTime(String test) {
        return test.matches(TIME_VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return time;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Time // instanceof handles nulls
                && time.equals(((Time) other).time)); // state check
    }

    @Override
    public int hashCode() {
        return time.hashCode();
    }

    @Override
    public int compareTo(Time other) {
        return this.time.compareTo(other.time);
    }


}
