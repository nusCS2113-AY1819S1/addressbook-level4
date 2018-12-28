package seedu.address.model.medicalreport;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.model.util.DateTimeUtil.DATE_CONSTRAINTS;
import static seedu.address.model.util.DateTimeUtil.DATE_VALIDATION_REGEX;

import java.time.LocalDate;

import seedu.address.model.util.DateTimeUtil;

//@@author chewkahmeng
/**
 * Represents a Medical Report's date in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidDate(String)}
 */
public class ReportDate {

    public final LocalDate fullDate;

    /**
     * Constructs a {@code ReportDate}.
     *
     * @param date A valid date.
     */
    public ReportDate(String date) {
        requireNonNull(date);
        checkArgument(isValidDate(date), DATE_CONSTRAINTS);
        fullDate = DateTimeUtil.parseDate(date);
    }

    /**
     * Returns true if a given string is a valid date.
     */
    public static boolean isValidDate(String test) {
        return test.matches(DATE_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return DateTimeUtil.format(fullDate);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReportDate // instanceof handles nulls
                && fullDate.equals(((ReportDate) other).fullDate)); // state check
    }

    @Override
    public int hashCode() {
        return fullDate.hashCode();
    }
}
