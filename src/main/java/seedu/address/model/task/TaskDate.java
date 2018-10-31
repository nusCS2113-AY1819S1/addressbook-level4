package seedu.address.model.task;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Task's due date in the to-do list.
 * Guarantees: immutable; is valid as declared in {@link #isValidDate(String)}
 */
public class TaskDate {

    public static final String MESSAGE_DATE_CONSTRAINTS =
            "Dates should be of the format DD-MM, where DD and MM are numbers.\n"
                    + "DD must be in the range of 01 to 31, and MM must be in the range of 01 to 12.";
    public static final String DATE_VALIDATION_REGEX = "[\\d]{2}" + "-" + "[\\d]{2}";

    private static final int DAY_MAX_LIMIT = 31;
    private static final int DAY_MIN_LIMIT = 1;
    private static final int MONTH_MAX_LIMIT = 12;
    private static final int MONTH_MIN_LIMIT = 1;

    public final String value;

    /**
     * Constructs an {@code TaskDate}.
     *
     * @param date A valid date.
     */
    public TaskDate(String date) {
        requireNonNull(date);
        checkArgument(isValidDate(date), MESSAGE_DATE_CONSTRAINTS);
        value = date;
    }

    /**
     * Returns if a given string is a valid date.
     */
    public static boolean isValidDate(String test) {
        if (test.matches(DATE_VALIDATION_REGEX)) {
            String[] numbers = test.split("-");
            int day = Integer.parseInt(numbers[0]);
            int month = Integer.parseInt(numbers[1]);
            if (day > DAY_MAX_LIMIT || day < DAY_MIN_LIMIT) {
                return false;
            } else if (month > MONTH_MAX_LIMIT || month < MONTH_MIN_LIMIT) {
                return false;
            } else {
                return true;
            }
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
                || (other instanceof TaskDate // instanceof handles nulls
                && value.equals(((TaskDate) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
