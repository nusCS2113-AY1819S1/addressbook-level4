package seedu.planner.model.record;

import static java.util.Objects.requireNonNull;
import static seedu.planner.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.planner.commons.util.AppUtil.checkArgument;

import java.util.logging.Logger;

import seedu.planner.commons.core.LogsCenter;
import seedu.planner.commons.util.DateUtil;

/**
 * Represents a Record's date in the financial planner.
 * Guarantees: immutable; is valid as declared in {@link #isValidDateFormat(String)}
 */
public class Date {
    public static final String MESSAGE_DATE_CONSTRAINTS =
            "Date parameter should be in the format of dd-mm-yyyy "
            + "with dd and mm being 2 digits, and yyyy being 4 digits."
            + " Please take note that inappropriate date will result in errors, for example: 30/02/2018";
    public static final String MESSAGE_DATE_LOGICAL_CONSTRAINTS =
            "Date should follow the modern calendar. "
            + "Day parameter must fit within the constraints of each month. \n"
            + "For e.g, February has only 28 days for the non-Leap year "
            + "so the day parameter must be less than or equal to 28 if the month "
            + "parameter is 2.";
    public static final String DATE_VALIDATION_REGEX = "\\d{1,2}-\\d{1,2}-\\d{4}";

    public final String value;

    private Logger logger = LogsCenter.getLogger(Date.class);

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
        checkArgument(DateUtil.isValidDate(day, month, year), String.format(MESSAGE_INVALID_COMMAND_FORMAT,
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

    public static boolean isValidDateFormat(String test) {
        return test.matches(DATE_VALIDATION_REGEX);
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
    /**
     * Compares whether the current object {@code Date} is earlier or later than the given {@code Date}
     * @param other
     * @return a positive value if date is later and negative value if date is earlier and zero if dates are the same
     */
    public int dateComparator(Date other) {
        if (this.year < other.getYear()) {
            return -1;
        } else if (this.year == other.getYear()) {
            if (this.month < other.getMonth()) {
                return -1;
            } else if (this.month == other.getMonth()) {
                if (this.day < other.getDay()) {
                    return -1;
                } else if (this.day == other.getDay()) {
                    return 0;
                } else {
                    return 1;
                }
            } else {
                return 1;
            }
        } else {
            return 1;
        }
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    public String getValue() {
        return value;
    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Date // instanceof handles nulls
                && day == ((Date) other).getDay()
                && month == ((Date) other).getMonth()
                && year == ((Date) other).getYear()); // state check
    }
}
