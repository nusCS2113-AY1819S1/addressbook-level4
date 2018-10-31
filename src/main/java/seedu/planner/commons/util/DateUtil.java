package seedu.planner.commons.util;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import seedu.planner.model.Month;
import seedu.planner.model.record.Date;

/**
 * Contains helper methods to determine whether a calendar parameter is logical or not
 */
public class DateUtil {
    private static final int JAN = 1;
    private static final int FEB = 2;
    private static final int MAR = 3;
    private static final int APR = 4;
    private static final int MAY = 5;
    private static final int JUN = 6;
    private static final int JUL = 7;
    private static final int AUG = 8;
    private static final int SEP = 9;
    private static final int OCT = 10;
    private static final int NOV = 11;
    private static final int DEC = 12;

    private static final int TWENTYEIGHTDAYS = 28;
    private static final int TWENTYNINEDAYS = 29;
    private static final int THIRTYDAYS = 30;
    private static final int THIRTYONEDAYS = 31;

    private static final List<Integer> maxDaysInEachMonth = Arrays.asList(31, 28, 31, 30, 31, 30,
            31, 31, 30, 31, 30, 31);

    /**
     * This function checks whether the given day and month falls within the constraints of modern calendars
     * @param day
     * @param month
     * @param year
     * @return true if valid and false if otherwise
     */
    public static boolean isValidDate(int day, int month, int year) {
        boolean isValid;
        switch (month) {
        case JAN:
        case MAR:
        case MAY:
        case JUL:
        case AUG:
        case OCT:
        case DEC:
            isValid = day <= THIRTYONEDAYS;
            break;
        case FEB:
            isValid = day <= (isLeapYear(year) ? TWENTYNINEDAYS : TWENTYEIGHTDAYS);
            break;
        case APR:
        case JUN:
        case SEP:
        case NOV:
            isValid = day <= THIRTYDAYS;
            break;
        default:
            isValid = false;
        }
        return isValid;
    }
    /**
     * This function checks whether the given day and month falls within the constraints of modern calendars
     */
    public static boolean isValidDate(int day, int month) {
        if (month > maxDaysInEachMonth.size()) {
            return false;
        }
        return day <= maxDaysInEachMonth.get(month - 1);
    }

    /**
     * This function compares month1 and month2 and returns an integer.
     * If month1 is earlier than month2, it returns -1.
     * If month1 is later than month2, it returns 1.
     * If month1 is equal to month2, it returns 0.
     */
    public static int compareMonth(Month month1, Month month2) {
        if (month1.getYear() < month2.getYear()) {
            return -1;
        } else if (month1.getYear() == month2.getYear()) {
            if (month1.getMonth() < month2.getMonth()) {
                return -1;
            } else if (month1.getMonth() == month2.getMonth()) {
                return 0;
            } else {
                return 1;
            }
        } else {
            return 1;
        }
    }
    //@@author tenvinc
    /**
     * Generates a {@code Date} based on the first day of the given month
     */
    public static Date generateFirstOfMonth(Month month) {
        String dateString = "1" + String.format("-%d-%d", month.getMonth(), month.getYear());
        return new Date(dateString);
    }

    /**
     * Generates a {@code Date} based on the last day of the given month
     */
    public static Date generateLastOfMonth(Month month) {
        String dateString = maxDaysInEachMonth.get(month.getMonth() - 1).toString()
                + String.format("-%d-%d", month.getMonth(), month.getYear());
        return new Date(dateString);
    }

    /**
     * Checks if the year is Leap Year or not.
     * @param year
     * @return the result whether the year is Leap year.
     */
    public static boolean isLeapYear (int year) {
        if (year % 400 == 0) {
            return true;
        } else if (year % 100 == 0) {
            return false;
        } else {
            return year % 4 == 0;
        }
    }

    /**
     * Return whether the day is in the given period or not.
     * @param startDate start of the period.
     * @param endDate end of the period.
     * @param target the Date I want to check.
     * @return the boolean value to indicate whether the date we want to check is within the period.
     */
    public static boolean isWithinPeriod(Date startDate, Date endDate, Date target) {
        return target.equals(startDate) || target.equals(endDate)
                || (target.isLaterThan(startDate) && target.isEarlierThan(endDate));
    }
    /**
     * Computes today's date using Java library {@link LocalDate} by processing the date in yyyy-mm-dd into dd-mm-yyyy.
     * @return Date
     */
    public static Date getDateToday() {
        String dateToday = LocalDate.now().toString();
        String[] args = dateToday.split("-");
        return new Date(String.format("%s-%s-%s", args[2], args[1], args[0]));
    }
}
