package seedu.planner.commons.util;

import java.util.Comparator;

import seedu.planner.model.record.Record;

/**
 * Comparator to sort {@code Record}s by name, date and moneyflow attributes.
 */
public class SortUtil {

    public static Comparator<Record> compareNameAttribute() {
        return Comparator.comparing(a -> a.getName().fullName.toLowerCase());
    }

    public static Comparator<Record> compareDateAttribute() {
        return (a, b) -> a.getDate().dateComparator(b.getDate());
    }

    public static Comparator<Record> compareMoneyflowAttribute() {
        return Comparator.comparing(a -> a.getMoneyFlow().valueDouble);
    }

}

