package seedu.planner.commons.util;

import java.util.Comparator;

import seedu.planner.model.Month;
import seedu.planner.model.record.Date;
import seedu.planner.model.record.Record;

/**
 * Comparator to sort {@code Record}s by name, date and moneyflow attributes.
 */
public class CompareUtil {

    public static Comparator<Record> compareNameAttribute() {
        return Comparator.comparing(a -> a.getName().fullName.toLowerCase());
    }

    public static Comparator<Record> compareDateAttribute() {
        return (a, b) -> compareDate().compare(a.getDate(),b.getDate());
    }

    public static Comparator<Record> compareMoneyflowAttribute() {
        return Comparator.comparing(a -> a.getMoneyFlow().valueDouble);
    }

    /**
     * This function compares date1 and date2 and returns an integer.
     * If date1 is earlier than date2, it returns -1.
     * If date1 is later than date2, it returns 1.
     * If date1 is equal to date2, it returns 0.
     */
    public static Comparator<Date> compareDate() {
        return (date1, date2) -> {
            if (date1.getYear() < date2.getYear()) {
                return -1;
            } else if (date1.getYear() == date2.getYear()) {
                if (date1.getMonth() < date2.getMonth()) {
                    return -1;
                } else if (date1.getMonth() == date2.getMonth()) {
                    if (date1.getDay() < date2.getDay()) {
                        return -1;
                    } else if (date1.getDay() == date2.getDay()) {
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
        };
    }

    /**
     * This function compares month1 and month2 and returns an integer.
     * If month1 is earlier than month2, it returns -1.
     * If month1 is later than month2, it returns 1.
     * If month1 is equal to month2, it returns 0.
     */
    public static Comparator<Month> compareMonth() {
        return (month1, month2) -> {
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
        };
    }

}

