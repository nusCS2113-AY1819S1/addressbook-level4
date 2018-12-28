package seedu.address.model.appt;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

//@@author brandonccm1996
/**
 * Represents appt info in an appt in the address book.
 * Guarantees: immutable; is always valid
 */
public class ApptInfo {

    public static final String MESSAGE_INFO_CONSTRAINTS =
            "Appt info can take any values, and it should not be blank.";

    public static final String APPTINFO_VALIDATION_REGEX = "[^\\s].*";
    public final String value;

    public ApptInfo(String apptInfo) {
        requireNonNull(apptInfo);
        checkArgument(isValidApptInfo(apptInfo), MESSAGE_INFO_CONSTRAINTS);
        value = apptInfo;
    }

    public static boolean isValidApptInfo(String test) {
        return test.matches(APPTINFO_VALIDATION_REGEX);
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
