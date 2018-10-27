package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Tag in the address book.
 * Guarantees: immutable; name is valid as declared in {@link #isValidTagName(String)}
 */
public class Time {

    //TODO: CHANGE TIME TO ONLY ACCEPT 4 DIGITS
    public static final String MESSAGE_TIME_CONSTRAINTS = "Time should be 4 digits in 24HRS format";
    public static final String TIME_VALIDATION_REGEX = "\\p{Digit}+";

    public final String theTime;

    /**
     * Constructs a {@code Time}.
     *
     * @param theTime A valid time format.
     */
    public Time(String theTime) {
        requireNonNull(theTime);
        checkArgument(isValidTime(theTime), MESSAGE_TIME_CONSTRAINTS);
        this.theTime = theTime;
    }

    /**
     * Returns true if a given string is a valid tag name.
     */
    public static boolean isValidTime(String test) {
        return test.matches(TIME_VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Time // instanceof handles nulls
                && theTime.equals(((Time) other).theTime)); // state check
    }

    @Override
    public int hashCode() {
        return theTime.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    @Override
    public String toString() {
        return '[' + theTime + ']';
    }

}