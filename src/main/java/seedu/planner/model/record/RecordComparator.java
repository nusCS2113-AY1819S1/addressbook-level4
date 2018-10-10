package seedu.planner.model.record;

import java.util.Comparator;

public class RecordComparator {

    public static Comparator<Record> compareNameAttribute() {
        return Comparator.comparing(A -> A.getName().fullName);
    }

    /*public static Comparator<Record> compareNameAttribute() {
        return new Comparator<>() {
            public int compare(Record A, Record B) {
                return A.getName().fullName.compareTo(B.getName().fullName);
            }
        };
    }*/

    public static Comparator<Record> compareDateAttribute() {
        return (A, B) -> A.getDate().dateComparator(B.getDate());
    }

    /*public static Comparator<Record> compareDateAttribute() {
        return new Comparator<Record>() {
            public int compare(Record A, Record B) {
                return A.getDate().dateComparator(B.getDate());
            }
        };
    }*/

    /*
    static Comparator<Record> compareMoneyflowAttribute() {
        return new Comparator<Record>() {
        };
    }*/
}

