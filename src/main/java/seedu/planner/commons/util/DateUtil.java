package seedu.planner.commons.util;

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

    /**
     * This function checks whether the given day and month falls within the constraints of modern calendars
     */
    public static boolean isValidDate(int day, int month) {
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
}
