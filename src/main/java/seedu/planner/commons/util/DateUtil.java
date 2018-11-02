package seedu.planner.commons.util;

import static seedu.planner.commons.util.SortUtil.compareDate;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import seedu.planner.model.Month;
import seedu.planner.model.record.Date;

/**
 * Contains helper methods to determine whether a calendar parameter is logical or not
 */
public class DateUtil {

    private static final List<Integer> maxDaysInEachMonth = Arrays.asList(31, 28, 31, 30, 31, 30,
            31, 31, 30, 31, 30, 31);

    private static final List<Integer> maxDaysInEachMonthLeapYear = Arrays.asList(31, 29, 31, 30, 31, 30,
            31, 31, 30, 31, 30, 31);

    /**
     * This function checks whether the given day and month falls within the constraints of modern calendars
     */
    public static boolean isValidDate(int day, int month, int year) {
        if (isLeapYear(year)){
            if (month > maxDaysInEachMonthLeapYear.size()) {
                return false;
            }
            return day <= maxDaysInEachMonthLeapYear.get(month - 1);
        }
        else {
            if (month > maxDaysInEachMonth.size()) {
                return false;
            }
            return day <= maxDaysInEachMonth.get(month - 1);
        }
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
     * Checks whether a {@code Date} is earlier than another given {@code Date}
     * @param date1
     * @param date2
     * @return True if date1 is earlier than date 2and False otherwise
     */
    public static boolean isEarlierThan(Date date1, Date date2) {
        return compareDate().compare(date1, date2) <= -1;
    }

    /**
     * Checks whether a {@code Date} is later than another given {@code Date}
     * @param date1
     * @param date2
     * @return True if date1 is later than date 2and False otherwise
     */
    public static boolean isLaterThan(Date date1, Date date2) {
        return compareDate().compare(date1, date2) >= 1;
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
     * Computes today's date using Java library {@link LocalDate} by processing the date in yyyy-mm-dd into dd-mm-yyyy.
     * @return Date
     */
    public static Date getDateToday() {
        String dateToday = LocalDate.now().toString();
        String[] args = dateToday.split("-");
        return new Date(String.format("%s-%s-%s", args[2], args[1], args[0]));
    }
}
