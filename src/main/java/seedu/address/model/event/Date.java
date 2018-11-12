//@@author ian-tjahjono
package seedu.address.model.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Represents an event's date in the Student Planner.
 * Guarantees: immutable;
 */

public class Date {

    public static final String MESSAGE_EVENT_DATE_CONSTRAINTS =
            "Event date should be in the DD/MM/YYYY format";
    public final String value;

    /**
     * Constructs a {@code Date}.
     *
     * @param date A valid event date.
     */
    public Date(String date) {
        requireNonNull(date);
        checkArgument(isValidDate(date), MESSAGE_EVENT_DATE_CONSTRAINTS);
        value = date;
    }

    /**
     * Returns true if a given string is a valid event date.
     */
    public static boolean isValidDate(String test) {
        String pattern = "dd/MM/yyyy";
        if (test.length() != pattern.length()) {
            return false;
        }
        DateFormat eventDate = new SimpleDateFormat(pattern);
        eventDate.setLenient(false);
        try {
            eventDate.parse(test);
            return true;
        } catch (ParseException pe) {
            return false;
        }
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Date // instanceof handles nulls
                && value.equals(((Date) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
