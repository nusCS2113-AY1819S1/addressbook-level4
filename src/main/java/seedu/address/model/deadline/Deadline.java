package seedu.address.model.deadline;
import java.util.Objects;
/**
 * Represents a deadline in the task book.
 * Guarantees: field values are validated, immutable, details are present and not null.
 */
public class Deadline {
    private final Integer day;
    private final Integer month;
    private final Integer year;
    public Deadline(Integer day, Integer month, Integer year) {
        this.day = day;
        this.month = month;
        this.year = year;
    }
    public Integer getDay() {
        return day;
    }
    public Integer getMonth() {
        return month;
    }
    public Integer getYear() {
        return year;
    }
    /**
     * Returns false if any fields are not within the limits (not a valid date).
     */
    public boolean isValid(Deadline deadline) {
        if (deadline.day < 0 || deadline.day > 31) {
            return false;
        } else if (deadline.month < 0 || deadline.month > 12) {
            return false;
        } else if (deadline.year < 1000 || deadline.year > 10000) {
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