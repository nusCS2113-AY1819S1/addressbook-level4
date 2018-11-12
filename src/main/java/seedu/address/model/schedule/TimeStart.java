package seedu.address.model.schedule;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Start Time of a Schedule in address book.
 */
public class TimeStart {
    public static final String MESSAGE_TIME_START_CONSTRAINTS =
            "Start Time should only be in numeric HHMM 24 hour format, and it should not be blank";

    /*
     * The first character of the start time must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String TIME_START_VALIDATION_REGEX = "\\d{4,}";

    public final String value;

    /**
     * Constructs a {@code Name}.
     *
     * @param timeStart A valid time
     */
    public TimeStart(String timeStart) {
        requireNonNull(timeStart);
        checkArgument(isValidTimeStart(timeStart), MESSAGE_TIME_START_CONSTRAINTS);
        value = timeStart;
    }

    /**
     * Returns true if a given string is a valid time.
     */
    public static boolean isValidTimeStart(String test) {
        if (test.matches(TIME_START_VALIDATION_REGEX)) {
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
                || (other instanceof TimeStart // instanceof handles nulls
                && value.equals(((TimeStart) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
