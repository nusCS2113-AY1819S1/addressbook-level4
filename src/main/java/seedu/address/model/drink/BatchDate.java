//@@author Lunastryke
package seedu.address.model.drink;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;

/**
 * Represents the import date of a batch of drink
 * Guarantees: TODO
 */
public class BatchDate {
    public static final String MESSAGE_DATE_CONSTRAINTS =
            "Date should be in this format DD/MM/YYYY";
    public static final String DATE_VALIDATION_REGEX =
            "^(3[01]|2[0-9]|1[0-9]|0[0-9]|[0-9])[/](1[0-2]|0[1-9]|[1-9])[/]([12]\\d{3})$";
    public static final String DATE_FORMAT = "d/MM/uuuu";
    private static final DateTimeFormatter FORMAT =
            DateTimeFormatter.ofPattern(DATE_FORMAT).withResolverStyle(ResolverStyle.STRICT);

    private LocalDate batchDate;

    /**
     * Constructs an {@code Date}.
     *
     * @param date A valid quantity value expressed as a string.
     */
    public BatchDate(String date) {
        requireNonNull(date);
        checkArgument(isValidDate(date), MESSAGE_DATE_CONSTRAINTS);
        this.batchDate = LocalDate.parse(date, DateTimeFormatter.ofPattern(DATE_FORMAT));
    }

    public LocalDate getBatchDate() {
        return batchDate;
    }

    /**
     * Returns true if a given string is a valid date value.
     */
    public static boolean isValidDate(String test) {
        return test.matches(DATE_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return batchDate.format(DateTimeFormatter.ofPattern(DATE_FORMAT));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof BatchDate // instanceof handles nulls
                && batchDate.equals(((BatchDate) other).batchDate)); // state check
    }

    @Override
    public int hashCode() {
        return batchDate.hashCode();
    }
}
