package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Represents a Person's date of birth in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidDateOfBirth(String)}
 */
public class DateOfBirth {
    public static final String DATEOFBIRTH_VALIDATION_REGEX =
            "(0?[0-9]|[12][0-9]|3[01])/(0?[0-9]|1[0-2])/(19[0-9]{2}|200[0-2])";

    // Error message for different date cases
    public static final String MESSAGE_DATEOFBIRTH_CONSTRAINTS_DEFAULT =
            "Date of Birth should only be integers in the format of DD/MM/YYYY, it should not be blank and within "
                    + "01/01/1900 to 31/12/2002";
    private static final String MESSAGE_DATE_INVALID_FEB_DATE_LEAPYEAR =
            "There are only 29 days in February on a leap year ";
    private static final String MESSAGE_DATE_INVALID_FEB_DATE_NONLEAPYEAR =
            "There are only 28 days in February ";
    private static final String MESSAGE_DATE_INVALID_MONTH_DATE =
            "There are only 30 days in April, June, September and November";
    private static final String DAY_THIRTYFIRST = "31";
    private static final int INDEX_DAY = 0;
    private static final int INDEX_MONTH = 1;
    private static final int INDEX_YEAR = 2;
    private static final List<String> DAYS_INVALID_FEBRUARY_LEAPYEAR =
            new ArrayList<>(Arrays.asList("30", "31"));
    private static final List<String> DAYS_INVALID_FEBRUARY_NONLEAPYEAR =
            new ArrayList<>(Arrays.asList("29", "30", "31"));
    private static final List<String> FEBRUARY =
            new ArrayList<>(Arrays.asList("2", "02"));
    private static final List<String> MONTHS_WITHOUT_THIRSTYFIRST =
            new ArrayList<>(Arrays.asList("4", "04", "6", "06", "9", "09", "11"));

    private static String messageDateOfBirthConstraints = MESSAGE_DATEOFBIRTH_CONSTRAINTS_DEFAULT;
    public final String value;

    /**
     * Constructs a {@code DateOfBirth}.
     *
     * @param dateOfBirth A valid date of birth.
     */

    public DateOfBirth(String dateOfBirth) {
        requireNonNull(dateOfBirth);
        dateOfBirth = formatDateOfBirth(dateOfBirth);
        checkArgument(isValidDateOfBirth(dateOfBirth), messageDateOfBirthConstraints);
        value = dateOfBirth;
    }

    public static String getMessageDateOfBirthConstraints() {
        return messageDateOfBirthConstraints;
    }

    public static void setMessageDateOfBirthConstraints(String error) {
        messageDateOfBirthConstraints = error;
    }

    /**
     * Formats date of birth to pad leading zeroes at the front to form length of 2 for day and month
     * @param dateOfBirth A non-padded date of birth string
     */
    public static String formatDateOfBirth (String dateOfBirth) {
        String[] dateOfBirthPadding = dateOfBirth.split("/");

        String day = String.format("%02d", Integer.parseInt(dateOfBirthPadding[INDEX_DAY]));
        String month = String.format("%02d", Integer.parseInt(dateOfBirthPadding[INDEX_MONTH]));
        String year = dateOfBirthPadding[INDEX_YEAR];

        return String.format(day + "/" + month + "/" + year);
    }

    /**
     * Returns true if a given string is a valid date of birth.
     */
    public static boolean isValidDateOfBirth(String test) {
        String day;
        String month;
        String year;

        if (test.matches(DATEOFBIRTH_VALIDATION_REGEX)) {
            String[] date = test.split("/");

            day = date[INDEX_DAY];
            month = date[INDEX_MONTH];
            year = date[INDEX_YEAR];

            return checkDateValidity(day, month, year);
        }

        setMessageDateOfBirthConstraints(MESSAGE_DATEOFBIRTH_CONSTRAINTS_DEFAULT);
        return false;
    }

    /**
     * Check if the given date is a valid date on the calendar.
     * @param day
     * @param month
     * @param year
     */

    public static boolean checkDateValidity (String day, String month, String year) {
        // Algorithm for leap year adapted from https://stackoverflow.com/questions/725098/leap-year-calculation
        boolean isLeapYear = ((Integer.valueOf(year) % 4 == 0) && ((Integer.valueOf(year) % 100 != 0)
                || (Integer.valueOf(year) % 400 == 0)));

        // Check whether given date is valid if its in February
        if (isLeapYear && FEBRUARY.contains(month) && DAYS_INVALID_FEBRUARY_LEAPYEAR.contains(day)) {
            setMessageDateOfBirthConstraints(MESSAGE_DATE_INVALID_FEB_DATE_LEAPYEAR);
            return false;
        } else if (!isLeapYear && FEBRUARY.contains(month) && DAYS_INVALID_FEBRUARY_NONLEAPYEAR.contains(day)) {
            setMessageDateOfBirthConstraints(MESSAGE_DATE_INVALID_FEB_DATE_NONLEAPYEAR);
            return false;
        }

        // Check whether given date is valid if its in April, June, September or November
        if (MONTHS_WITHOUT_THIRSTYFIRST.contains(month) && day.equals(DAY_THIRTYFIRST)) {
            setMessageDateOfBirthConstraints(MESSAGE_DATE_INVALID_MONTH_DATE);
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DateOfBirth // instanceof handles nulls
                && value.equals(((DateOfBirth) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
