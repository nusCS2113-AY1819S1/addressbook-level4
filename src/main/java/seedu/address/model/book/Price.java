package seedu.address.model.book;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Book's email in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidPhone(String)}
 */
public class Price {

    public static final String MESSAGE_PRICE_CONSTRAINTS = "Prices should be numerical and in 2 decimal places or none at all\n"
            + "E.g. $4, $3.02";
     // "$" can be omitted and is optional, prices can be in 2 decimal places or none at all
     // e.g. $4 or $3.02 is accepted
     public static final String PRICE_VALIDATION_REGEX = "(\\$)?\\d+(\\.\\d{2})?";

    public final String value;

    /**
     * Constructs an {@code Price}.
     *
     * @param email A valid email address.
     */
    public Price(String email) {
        requireNonNull(email);
        checkArgument(isValidPhone(email), MESSAGE_PRICE_CONSTRAINTS);
        value = email;
    }

    /**
     * Returns if a given string is a valid email.
     */
    public static boolean isValidPhone(String test) {
        return test.matches(PRICE_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Price // instanceof handles nulls
                && value.equals(((Price) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
