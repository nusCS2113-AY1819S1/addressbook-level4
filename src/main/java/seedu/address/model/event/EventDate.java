package seedu.address.model.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Event's date in the event list.
 * Guarantees: immutable;
 */
public class EventDate {

    public static final String MESSAGE_DATE_CONSTRAINTS =
            "Date should only be given in YYYY-MM-DD format and in valid form. Leap year anomalies are not validated.";

    /*
     * The date should be valid and in the correct YYYY-MM-DD format.
     */
    public static final String DATE_VALIDATION_REGEX =
            "^[0-9]{4}-(((0[13578]|(10|12))-(0[1-9]|[1-2][0-9]|3[0-1]))|(02-(0[1-9]|[1-2][0-9]))|((0[469]|11)"
                    + "-(0[1-9]|[1-2][0-9]|30)))$";


    public final String eventDate;

    /**
     * Constructs a {@code EventDate}.
     *
     * @param date A valid date.
     */
    public EventDate(String date) {
        requireNonNull(date);
        checkArgument(isValidDate(date), MESSAGE_DATE_CONSTRAINTS);
        eventDate = date;
    }

    /**
     * Returns true if a given string is a valid date.
     */
    public static boolean isValidDate(String test) {
        return test.matches(DATE_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return eventDate;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EventDate // instanceof handles nulls
                && eventDate.equals(((EventDate) other).eventDate)); // state check
    }

    @Override
    public int hashCode() {
        return eventDate.hashCode();
    }


}
