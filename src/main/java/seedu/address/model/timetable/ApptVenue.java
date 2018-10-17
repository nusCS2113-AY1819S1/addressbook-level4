package seedu.address.model.timetable;

import static java.util.Objects.requireNonNull;

/**
 * Represents an appt venue in an appt in the address book.
 * Guarantees: immutable; is always valid
 */
public class ApptVenue {
    public static final String MESSAGE_NAME_CONSTRAINTS =
            "Venues should only contain alphanumeric characters and spaces, and it should not be blank.";

    public final String value;

    public ApptVenue(String apptVenue) {
        requireNonNull(apptVenue);
        value = apptVenue;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ApptVenue // instanceof handles nulls
                && value.equals(((ApptVenue) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
