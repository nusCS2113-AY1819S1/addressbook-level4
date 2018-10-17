package seedu.address.model.task;

import java.util.Objects;
//@@author emobeany

/**
 * Represents a deadline in the task book.
 * Guarantees: field values are validated, immutable, details are present and not null.
 */

public class Deadline {
    public static final String MESSAGE_DEADLINE_CONSTRAINTS =
        "Deadline can only have dd/mm/yyyy format";

    private final String day;
    private final String month;
    private final String year;

    public Deadline(String day, String month, String year) {
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public Deadline(String deadline) {
        String[] entries = deadline.split("/");
        this.day = entries[0];
        this.month = entries[1];
        this.year = entries[2];
    }

    /*
    public Deadline(String day, String month) {
        this.day = day;
        this.month = month;
    }
    */

    public String getDay() {
        return day;
    }

    public String getMonth() {
        return month;
    }

    public String getYear() {
        return year;
    }

    /**
     * Returns false if any fields are not within the limits (not a valid date).
     */

    public static boolean isValidDeadline(String test) {
        String[] entries = test.split("/");
        String day = entries[0];
        String month = entries[1];
        String year = entries[2];

        // Check that all the characters are numeric first.
        if (!isNumeric(day) || !isNumeric(month) || !isNumeric(year)) {
            return false;
        } else if (Integer.parseInt(day) < 0 || Integer.parseInt(day) > 31) {
            return false;
        } else if (Integer.parseInt(month) < 0 || Integer.parseInt(month) > 12) {
            return false;
        } else if (Integer.parseInt(year) < 1000 || Integer.parseInt(year) > 10000) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        // custom fields hashing
        return Objects.hash(day, month, year);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getDay())
                .append("/")
                .append(getMonth())
                .append("/")
                .append(getYear());
        return builder.toString();
    }

    /**
     * Referenced online: Checking if String is numeric
     * @param s
     * @return true if String is completely numeric
     */
    public static boolean isNumeric(String s) {
        //s.matches("[-+]?\\d*\\.?\\d+");
        return s != null && s.matches("-?\\d+(\\.\\d+)?");
    }
}
