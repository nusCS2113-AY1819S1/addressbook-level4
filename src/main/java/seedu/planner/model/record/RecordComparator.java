package seedu.planner.model.record;

import java.util.Comparator;

/**
 * Comparator to sort {@code Record}s by name, date and moneyflow attributes.
 */
public class RecordComparator {

    public static Comparator<Record> compareNameAttribute() {
        return Comparator.comparing(A -> A.getName().fullName.toLowerCase());
    }

    public static Comparator<Record> compareDateAttribute() {
        return (A, B) -> A.getDate().dateComparator(B.getDate());
    }

    public static Comparator<Record> compareMoneyflowAttribute() {
        return Comparator.comparing(A -> A.getMoneyFlow().valueDouble);
    }

}

