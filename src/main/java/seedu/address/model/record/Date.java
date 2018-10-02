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
            + "with dd and mm being 2 digits, and yyyy being 4 digits."
            + " Please take note that inappropriate date will result in errors, for example: 30/02/2018";
    public static final String DATE_VALIDATION_REGEX = "\\d{1,2}-\\d{1,2}-\\d{4}";
    public final String value;
    private String day;

    private String month;
    private String year;

    /**
     * Constructs a {@code Date}.
     *
     * @param date A valid date.
     */
    public Date(String date) {
        requireNonNull(date);
        checkArgument(isValidDate(date), MESSAGE_DATE_CONSTRAINTS);
        // TODO: Change this part to split dates into dd, mm, yyyy.
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
        day = dateParams[0];
        month = dateParams[1];
        year = dateParams[2];
    }


    /**
     * Returns true if a given string is a valid date.
     */
    //TODO: add more constraint to the Date
    public static boolean isValidDate(String test) {
        if (test.matches(DATE_VALIDATION_REGEX)){
            String[] dateParams = test.split("-");
            int day = Integer.parseInt(dateParams[0]);
            int month = Integer.parseInt(dateParams[1]);
            int year = Integer.parseInt(dateParams[2]);
            if (day <= 0 || day > 31 || month <= 0 || month > 12 || year <= 0){
                return false;
            } else if (month == 2 || month == 4 || month == 6 || month == 9 || month == 11){
                if (day <= 30){
                    if (month == 2 && day > 28 && isLeapYear(year)){
                        return false;
                    } else {
                        return true;
                    }
                }
            } else {
                return false;
            }
        }
        return false;
    }

    public static boolean isLeapYear (int year) {
        if (year % 400 == 0) {
            return true;
        } else if (year % 100 == 0) {
            return false;
        } else if (year % 4 == 0) {
            return true;
        } else
            return false;
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

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    // TODO: Decide as a group whether we want days/month/year to be accessed separately
    public String getDay() {
        return day;
    }

    public int getDayInt() {
        return Integer.parseInt(day);
    }

    public String getMonth() {
        return month;
    }

    public int getMonthInt() {
        return Integer.parseInt(month);
    }

    public String getYear() {
        return year;
    }

    public int getYearInt() {
        return Integer.parseInt(year);
    }
}
