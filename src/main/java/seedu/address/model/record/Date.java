package seedu.address.model.record;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Record's date in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidDate(String)}
 */
public class Date {


    public static final String MESSAGE_DATE_CONSTRAINTS =
            "Date parameter should be in the format of dd-mm-yyyy "
            + "with dd and mm being 2 digits, and yyyy being 4 digits.";
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
        checkArgument(isValidDate(date), MESSAGE_DATE_CONSTRAINTS);
        value = date;
        splitDate(date);
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
     * Returns true if a given string is a valid date.
     */
    public static boolean isValidDate(String test) {
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
