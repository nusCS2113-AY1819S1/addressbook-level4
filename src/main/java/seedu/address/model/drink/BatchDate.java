//@@author Lunastryke
package seedu.address.model.drink;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

/**
 * Represents the import date of a batch of drink
 * Guarantees: TODO
 */
public class BatchDate {
    public static final String MESSAGE_DATE_CONSTRAINTS =
            "Date should be in this format DD/MM/YYYY or D/MM/YYYY or DD/M/YYYY";
    public static final String MESSAGE_NON_EXISTING_DATE =
            "Please enter a date that exists";
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
        try {
            this.batchDate = LocalDate.parse(date, FORMAT);
        } catch (DateTimeParseException e) {
            throw new RuntimeException(MESSAGE_NON_EXISTING_DATE);
        }
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

    /**
     * @param other a valid BatchDate object
     * @return Returns true if other holds a date after that of the current object
     */
    public boolean isAfter(BatchDate other) {
        return this.batchDate.isAfter(other.batchDate);
    }

    /**
     * @param other a valid BatchDate object
     * @return Returns true if other holds a date before that of the current object
     */
    public boolean isBefore(BatchDate other) {
        return this.batchDate.isBefore(other.batchDate);
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
