//@@author emobeany

package seedu.address.model.task;

import java.util.Objects;

/**
 * Represents a deadline in the task book.
 * Guarantees: field values are validated, immutable, details are present and not null.
 */

public class Deadline {
    private final String day;
    private final String month;
    private final String year;

    public Deadline(String day, String month, String year) {
        this.day = day;
        this.month = month;
        this.year = year;
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

    public boolean isValidDeadline(Deadline deadline) {
        if (Integer.parseInt(deadline.day) < 0 || Integer.parseInt(deadline.day) > 31) {
            return false;
        } else if (Integer.parseInt(deadline.month) < 0 || Integer.parseInt(deadline.month) > 12) {
            return false;
        } else if (Integer.parseInt(deadline.year) < 1000 || Integer.parseInt(deadline.year) > 10000) {
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
}
