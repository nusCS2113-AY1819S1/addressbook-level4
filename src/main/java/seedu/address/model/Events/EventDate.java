package seedu.address.model.Events;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Presents the eventDate in the event.
 */
public class EventDate {
    public static final String DATE_VALIDATION_REGEX = "^(0[1-9]||[1-2][0-9]||3[0-1])/(0[0-9]||1[0-2])$";
    public static final String MESSAGE_EVENTDATE_CONSTRAINTS =
            "EventDate should be in the format DD/MM, and it should not be blank";
    public final String ThisDate;
    public EventDate(String date) {
        requireNonNull(date);
        checkArgument(checkValid(date), MESSAGE_EVENTDATE_CONSTRAINTS);
        ThisDate = date;
    }
    public static boolean checkValid(String date) {
        return date.matches(DATE_VALIDATION_REGEX);
    }

    public String toString() {
        return ThisDate;
    }

}
