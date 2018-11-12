package seedu.address.model.medhistory;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.model.util.DateTimeUtil.DATE_VALIDATION_REGEX;

import java.time.LocalDate;

import seedu.address.model.util.DateTimeUtil;

//@@author xShaivan
/**
 * Represents the date of medical history.
 * Guarantees: immutable; is valid as declared in {@link #isValidMedHistDate(String)}
 */

public class MedHistDate {
    public static final String MESSAGE_MEDHISTDATE_CONSTRAINTS =
            "MedHistDate must comply with dd-MM-yyyy format";
    public final LocalDate value;

    /**
     * Constructs an {@code MedHistDate}.
     *
     * @param medHistDate A valid date.
     */
    public MedHistDate(String medHistDate) {
        requireNonNull(medHistDate);
        // isValidDate will is found in test file.
        checkArgument(isValidMedHistDate(medHistDate), MESSAGE_MEDHISTDATE_CONSTRAINTS);
        value = DateTimeUtil.parseDate(medHistDate);
    }

    public static boolean isValidMedHistDate(String test) {
        return test.matches(DATE_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return DateTimeUtil.format(value);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MedHistDate // instanceof handles nulls
                && value.equals(((MedHistDate) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
