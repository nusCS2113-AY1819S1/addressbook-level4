package seedu.address.model.timetable;

import static java.util.Objects.requireNonNull;

/**
 * Represents appt info in an appt in the address book.
 * Guarantees: immutable; is always valid
 */
public class ApptInfo {

    public static final String MESSAGE_NAME_CONSTRAINTS =
            "Appt information should only contain alphanumeric characters and spaces, and it should not be blank.";

    public final String value;

    public ApptInfo(String apptInfo) {
        requireNonNull(apptInfo);
        value = apptInfo;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ApptInfo // instanceof handles nulls
                && value.equals(((ApptInfo) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
