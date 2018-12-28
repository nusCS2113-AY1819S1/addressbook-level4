package seedu.address.model.appt;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;

import seedu.address.model.util.DateTimeUtil;

//@@author brandonccm1996
/**
 * Represents an appt date in an appt in the address book.
 * Guarantees: immutable; is always valid
 */
public class ApptDate {
    public static final String MESSAGE_NAME_CONSTRAINTS = "The date should be of the format: " + "DD-MM-YYYY";
    public static final String APPT_DATE_VALIDATION_REGEX = "[0-9]{2}" + "[-]" + "[0-9]{2}" + "[-]" + "[0-9]{4}";

    public final LocalDate value;

    public ApptDate(String apptDate) {
        requireNonNull(apptDate);
        value = DateTimeUtil.parseDate(apptDate);
    }

    public static boolean isValidDate(String test) {
        return test.matches(APPT_DATE_VALIDATION_REGEX);
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
