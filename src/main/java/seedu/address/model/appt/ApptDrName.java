package seedu.address.model.appt;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

//@@author brandonccm1996
/**
 * Represents a doctor (taking an appt) in an appt in the address book.
 * Guarantees: immutable; is always valid
 */
public class ApptDrName {
    public static final String MESSAGE_DRNAME_CONSTRAINTS =
            "Doctor names should only contain alphanumeric characters and spaces, and it should not be blank.";

    public static final String DRNAME_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";
    public final String value;

    public ApptDrName(String apptDrName) {
        requireNonNull(apptDrName);
        checkArgument(isValidDrName(apptDrName), MESSAGE_DRNAME_CONSTRAINTS);
        value = apptDrName;
    }

    public static boolean isValidDrName(String test) {
        return test.matches(DRNAME_VALIDATION_REGEX);
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
