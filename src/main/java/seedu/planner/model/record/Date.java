package seedu.planner.model.record;

import static java.util.Objects.requireNonNull;
import static seedu.planner.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.planner.commons.util.AppUtil.checkArgument;

import seedu.planner.commons.util.DateUtil;

/**
 * Represents a Record's date in the financial planner.
 * Guarantees: immutable; is valid as declared in {@link #isValidDateFormat(String)}
 */
public class Date {


    public static final String MESSAGE_DATE_CONSTRAINTS =
            "Date parameter should be in the format of dd-mm-yyyy "
            + "with dd and mm being 2 digits, and yyyy being 4 digits.";
    public static final String MESSAGE_DATE_LOGICAL_CONSTRAINTS =
            "Date should follow the modern calendar. Day parameter must fit within the constraints of each month. \n"
            + "For e.g, February has only 28 days so the day parameter must be less than or equal to 28 if the month "
            + "parameter is 2.";
    public static final String DATE_VALIDATION_REGEX = "\\d{1,2}-\\d{1,2}-\\d{4}";
    public final String value;
    private int day;

    private int month;
    private int year;

    /**
     * Constructs a {@code Date}.
     *
     * @param date A valid date.
     */
    public Date(String date) {
        requireNonNull(date);
        checkArgument(isValidDateFormat(date), String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                MESSAGE_DATE_CONSTRAINTS));
        value = date;
        splitDate(date);
        checkArgument(DateUtil.isValidDate(day, month), String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                MESSAGE_DATE_LOGICAL_CONSTRAINTS));
    }

    /**
     * Splits a date into the different parameters and assigns them to day,month,year
     * Format specified: dd-mm-yyyy
     * @param date
     */
    private void splitDate(String date) {
        String[] dateParams = date.split("-");
        day = Integer.parseInt(dateParams[0]);
        month = Integer.parseInt(dateParams[1]);
        year = Integer.parseInt(dateParams[2]);
    }


    /**
     * Returns true if a given string is in a valid date format.
     */
    public static boolean isValidDateFormat(String test) {
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

    /**
     * Checks whether the current object {@code Date} is later than the given {@code Date}
     * @param other
     * @return True if date is later and False if date is earlier
     */
    public boolean isLaterThan(Date other) {
        if (this.year > other.getYear()) {
            return true;
        } else if (this.year == other.getYear()) {
            if (this.month > other.getMonth()) {
                return true;
            } else if (this.month == other.getMonth()) {
                return this.day > other.getDay();
            }
        }
        return false;
    }

    /**
     * Checks whether the current object {@code Date} is earlier than the given {@code Date}
     * @param other
     * @return True if date is earlier and False if date is later
     */
    public boolean isEarlierThan(Date other) {
        if (this.year < other.getYear()) {
            return true;
        } else if (this.year == other.getYear()) {
            if (this.month < other.getMonth()) {
                return true;
            } else if (this.month == other.getMonth()) {
                return this.day < other.getDay();
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    // TODO: Decide as a group whether we want days/month/year to be accessed separately
    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }
}
