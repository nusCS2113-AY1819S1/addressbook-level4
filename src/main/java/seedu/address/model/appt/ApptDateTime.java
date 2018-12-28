package seedu.address.model.appt;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.model.util.DateTimeUtil.DATE_TIME_CONSTRAINTS;
import static seedu.address.model.util.DateTimeUtil.DATE_TIME_VALIDATION_REGEX;

import java.time.LocalDateTime;

import seedu.address.model.util.DateTimeUtil;

//@@author brandonccm1996
/**
 * Represents an appt date and time in an appt in the address book.
 * Guarantees: immutable; is always valid
 */
public class ApptDateTime {

    public final LocalDateTime value;

    public ApptDateTime(String apptDateTime) {
        requireNonNull(apptDateTime);
        checkArgument(isValidDateTime(apptDateTime), DATE_TIME_CONSTRAINTS);
        value = DateTimeUtil.parseDateTime(apptDateTime);
    }

    public static boolean isValidDateTime(String test) {
        return test.matches(DATE_TIME_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return DateTimeUtil.format(value);
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
