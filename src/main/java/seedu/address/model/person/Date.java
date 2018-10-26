package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents the date of the event in JitHub.
 * Guarantees: immutable; is valid as declared in {@link #isValidDate(String)}
 */
public class Date {
    public static final String MESSAGE_DATE_CONSTRAINTS =
            "Dates should only contain numbers in DDMMYYYY format, and it should not be blank";

    /*
     * The first character of the date must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String DATE_VALIDATION_REGEX = "[\\d{Digit}][\\d{Digit} ]*";

    public final String date;

    /**
     * Constructs a {@code Name}.
     *
     * @param date A valid date.
     */
    public Date(String theDate) {
        requireNonNull(theDate);
        checkArgument(isValidDate(theDate), MESSAGE_DATE_CONSTRAINTS);
        date = theDate;
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidDate(String test) {
        return test.matches(DATE_VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return date;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Date // instanceof handles nulls
                && date.equals(((Date) other).date)); // state check
    }

    @Override
    public int hashCode() {
        return date.hashCode();
    }

}