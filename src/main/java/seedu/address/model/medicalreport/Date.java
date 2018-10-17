package seedu.address.model.medicalreport;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Medical Report's date in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidDate(String)}
 */
public class Date {

    public static final String MESSAGE_DATE_CONSTRAINTS =
            "The full Date should be of the format: DD/MM/YYYY";

    /*
     * The first character of the date must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String DATE_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String fullDate;

    /**
     * Constructs a {@code Date}.
     *
     * @param date A valid date.
     */
    public Date(String date) {
        requireNonNull(date);
        fullDate = date;
    }

    /**
     * Returns true if a given string is a valid date.
     */
    public static boolean isValidDate(String test) {
        return test.matches(DATE_VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return fullDate;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Date // instanceof handles nulls
                && fullDate.equals(((Date) other).fullDate)); // state check
    }

    @Override
    public int hashCode() {
        return fullDate.hashCode();
    }

    public boolean isFull() {
        return true;
    }
}
