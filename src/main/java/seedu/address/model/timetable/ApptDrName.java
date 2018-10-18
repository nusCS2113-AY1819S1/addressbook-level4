package seedu.address.model.timetable;

import static java.util.Objects.requireNonNull;

/**
 * Represents a doctor (taking an appt) in an appt in the address book.
 * Guarantees: immutable; is always valid
 */
public class ApptDrName {
    public static final String MESSAGE_NAME_CONSTRAINTS =
            "Doctor names should only contain alphanumeric characters and spaces, and it should not be blank.";

    public final String value;

    public ApptDrName(String apptDrName) {
        requireNonNull(apptDrName);
        value = apptDrName;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ApptDrName // instanceof handles nulls
                && value.equals(((ApptDrName) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
