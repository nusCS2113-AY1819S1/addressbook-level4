package seedu.address.model.schedule;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Represents a Schedule's date in the Schedule list.
 * Guarantees: immutable; is valid as declared in {@link #Date(String)}
 */
public class Date {
    public static final String DATE_PATTERN = "dd/MM/yyyy";

    public static final String DATE_VALIDATION_REGEX = "^(0?[1-9]|[12][0-9]|3[01])\\/(0?[1-9]|1[012])\\/((20)\\d\\d)$";

    public static final String MESSAGE_DATE_CONSTRAINTS_DEFAULT =
            "Date should only be integers in the format of DD/MM/YYYY, it should not be blank and within "
                    + "01/01/2000 to 31/12/2099";

    public static final String MESSAGE_DATE_OF_SCHEDULE_BEFORE_TODAY_DATE =
            "Date of schedule %1$s should not be before today's date %2$s. "
                    + "\nScheduling for past dates is not allowed! ";

    private static final String MESSAGE_DATE_INVALID_FEB_DATE =
            "29, 30 and 31 are invalid dates of February ";
    private static final String MESSAGE_DATE_INVALID_FEB_DATE_LEAP_YEAR =
            "30 and 31 are invalid dates on a leap year of February ";

    private static final String MESSAGE_DATE_INVALID_MONTH_DATE =
            "april, june, sep, nov does not have 31 days";

    private static String dateConstraintsError = MESSAGE_DATE_CONSTRAINTS_DEFAULT;

    public final String value;

    /**
     * Constructs a {@code date}.
     * @param date A valid date of Schedule.
     */

    public Date(String date) {
        requireNonNull(date);
        checkArgument(isValidScheduleDate(date), dateConstraintsError);
        date = formatDate(date);
        value = date;
    }

    public static void setDateConstraintsError(String error) {
        dateConstraintsError = error;
    }

    public static String getDateConstraintsError() {
        return dateConstraintsError;
    }

    /**
     * Checks whether the date has past.
     * @param inputDate to be checked with today's date
     * @return Boolean, true if input date is before today's date.
     */
    public static boolean isBeforeTodayDate (String inputDate) {
        inputDate = formatDate(inputDate);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_PATTERN);
        LocalDate todayDate = LocalDate.now();
        LocalDate toLocalInputDate = LocalDate.parse(inputDate, formatter);
        if (toLocalInputDate.isBefore(todayDate)) {
            dateConstraintsError = String.format(MESSAGE_DATE_OF_SCHEDULE_BEFORE_TODAY_DATE, inputDate, todayDate());
            return true;
        }
        return false;
    }

    /**
     * Get today's date
     * @return String, today's date.
     */
    public static String todayDate () {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_PATTERN);
        return formatter.format(LocalDate.now());
    }

    /**
     * Formats date to add leading 0's to form width of 2 for day and month.
     * @param inputDate date to be padded with 0 for day and month
     * @return String, date padded with 0's if necessary for day and month.
     */
    public static String formatDate (String inputDate) {
        String day;
        String month;
        String year;

        String[] date = inputDate.split("/");

        day = String.format("%02d", Integer.parseInt(date[0]));
        month = String.format("%02d", Integer.parseInt(date[1]));
        year = date[2];

        return String.format(day + "/" + month + "/" + year);
    }

    /**
     * Returns true if a given string is a valid date found in calendar, and not before today's date
     * @param inputDate date to be checked if valid for scheduling.
     * @return Boolean, true if it passes the regular expression and {@code checkValidDate()} checks.
     */
    public static boolean isValidScheduleDate(String inputDate) {
        String day;
        String month;
        String year;

        if (inputDate.matches(DATE_VALIDATION_REGEX)) {
            inputDate = formatDate(inputDate);
            String[] date = inputDate.split("/");

            day = date[0];
            month = date[1];
            year = date[2];
            return checkValidDate(year, month, day);
        }
        setDateConstraintsError(MESSAGE_DATE_CONSTRAINTS_DEFAULT);
        return false;
    }

    /**
     * Check if date is a valid date on the Calendar.
     * @param year year to check
     * @param month month to check
     * @param day day to check
     */
    public static boolean checkValidDate (String year, String month, String day) {
        boolean isLeapYear = ((Integer.valueOf(year) % 4 == 0)
                && (Integer.valueOf(year) % 100 != 0) || (Integer.valueOf(year) % 400 == 0));

        if (("02".equals(month)) || ("2".equals(month))) {
            if ((isLeapYear) && ((
                    "30".equals(day)) || ("31".equals(day)))) {
                setDateConstraintsError(MESSAGE_DATE_INVALID_FEB_DATE_LEAP_YEAR + year);
                return false; //29 Feb is a valid leap year. 30, 31 is invalid.
            } else if ((!isLeapYear) && (("29".equals(day)) || ("30".equals(day)) || ("31".equals(day)))) {
                setDateConstraintsError(MESSAGE_DATE_INVALID_FEB_DATE + year);
                return false; //29,30,31 Feb is a invalid in non-leap year
            }
        }

        if (("31".equals(day)) && ((
                "04".equals(month)) || ("4".equals(month)) || ("06".equals(month)) || ("6".equals(month))
                || ("09".equals(month)) || ("9".equals(month)) || ("11".equals(month)))) {
            setDateConstraintsError(MESSAGE_DATE_INVALID_MONTH_DATE);
            return false; // april, june, sep, nov does not have 31 days
        }
        setDateConstraintsError(MESSAGE_DATE_CONSTRAINTS_DEFAULT);
        return true;
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

}
