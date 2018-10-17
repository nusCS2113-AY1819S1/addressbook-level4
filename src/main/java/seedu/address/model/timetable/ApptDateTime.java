package seedu.address.model.timetable;

import static java.util.Objects.requireNonNull;

/**
 * Represents an appt date and time in an appt in the address book.
 * Guarantees: immutable; is always valid
 */
public class ApptDateTime {
    public static final String MESSAGE_NAME_CONSTRAINTS = "The full Date and Time should be of the format: "
            + "DD/MM/YYYY HH:MM.";

    public final String value;

    public ApptDateTime(String apptDateTime) {
        requireNonNull(apptDateTime);
        value = apptDateTime;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ApptDateTime // instanceof handles nulls
                && value.equals(((ApptDateTime) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
