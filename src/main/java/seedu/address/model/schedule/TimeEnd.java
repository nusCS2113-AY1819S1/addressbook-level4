package seedu.address.model.schedule;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a End Time of a Schedule in address book.
 */
public class TimeEnd {
    public static final String MESSAGE_TIME_END_CONSTRAINTS =
            "End Time should only be in numeric HHMM 24 hour format, and it should not be blank";

    /*
     * The first character of the end time must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String TIME_END_VALIDATION_REGEX = "\\d{4}";

    public final String value;

    /**
     * Constructs a {@code Name}.
     *
     * @param timeEnd A valid time.
     */
    public TimeEnd(String timeEnd) {
        requireNonNull(timeEnd);
        checkArgument(isValidTimeEnd(timeEnd), MESSAGE_TIME_END_CONSTRAINTS);
        value = timeEnd;
    }

    /**
     * Returns true if a given string is a valid time.
     */
    public static boolean isValidTimeEnd(String test) {
        if (test.matches(TIME_END_VALIDATION_REGEX)) {
            int testInteger = Integer.parseInt(test);
            return (testInteger < 2400) && ((testInteger % 100) < 60);
        }
        return false;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TimeEnd // instanceof handles nulls
                && value.equals(((TimeEnd) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
