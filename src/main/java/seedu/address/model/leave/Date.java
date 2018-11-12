package seedu.address.model.leave;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.text.SimpleDateFormat;
import java.time.DateTimeException;

//@@author Hafizuddin-NUS
/**
 * Represents a Leave's date in the leave list.
 * Guarantees: immutable; is valid as declared in {@link #isValidDate(String)}
 */
public class Date {

    public static final String MESSAGE_DATE_CONSTRAINTS = "Wrong date format (DD/MM/YYYY) / invalid date due "
            + "to today's date or date has passed / invalid calendar date.";

    public final String date;

    /**
     * Constructs an {@code Date}.
     *
     * @param date A valid date.
     */
    public Date (String date) {
        requireNonNull(date);
        checkArgument(isValidDate(date), MESSAGE_DATE_CONSTRAINTS);
        this.date = date;
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidDate(String date) throws DateTimeException, NumberFormatException {

        String[] dateComponents = date.split("/");
        int day = Integer.parseInt(dateComponents[0]);
        int month = Integer.parseInt(dateComponents[1]);
        int year = Integer.parseInt(dateComponents[2]);

        if (date.length() != 10) {
            return false;
        }

        if (dateComponents.length != 3) {
            return false;
        }

        if (month < 1 || month > 12) {
            return false;
        }

        if (year < 2018) {
            return false;
        }

        if (!checkValidCalendarDate(date)) {
            return false;
        }

        try {
            if (new SimpleDateFormat("dd/MM/yyyy").parse(date).before(new java.util.Date())) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }

        return day >= 1 && day <= 31;

    }

    @Override
    public String toString() {
        return date;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Date // instanceof handles nulls
                && date.equals(((Date) other).date)); // state check
    }

    @Override
    public int hashCode() {
        return date.hashCode();
    }

    /**
     * Check if date is a valid date on the Calendar.
     */
    public static boolean checkValidCalendarDate (String inputDate) {

        String[] date = inputDate.split("/");

        String day = date[0];
        String month = date[1];
        String year = date[2];

        boolean isLeapYear = ((Integer.valueOf(year) % 4 == 0)
                && (Integer.valueOf(year) % 100 != 0) || (Integer.valueOf(year) % 400 == 0));

        if (("02".equals(month)) || ("2".equals(month))) {
            if ((isLeapYear) && ((
                    "30".equals(day)) || ("31".equals(day)))) {
                return false;
            } else if ((!isLeapYear) && (("29".equals(day)) || ("30".equals(day)) || ("31".equals(day)))) {
                return false;
            }
        }

        if (("31".equals(day)) && ((
                "04".equals(month)) || ("4".equals(month)) || ("06".equals(month)) || ("6".equals(month))
                || ("09".equals(month)) || ("9".equals(month)) || ("11".equals(month)))) {
            return false;
        }
        return true;
    }

}
