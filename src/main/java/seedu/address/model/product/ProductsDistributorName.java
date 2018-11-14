package seedu.address.model.product;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Product's email in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidEmail(String)}
 */
public class ProductsDistributorName {

    public static final String EMAIL_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";
    public static final String MESSAGE_EMAIL_CONSTRAINTS =
            "Names should only contain alphanumeric characters and spaces, and it should not be blank";

    public final String value;

    /**
     * Constructs an {@code ProductsDistributorName}.
     *
     * @param email A valid email address.
     */
    public ProductsDistributorName(String email) {
        requireNonNull(email);
        checkArgument(isValidEmail(email), MESSAGE_EMAIL_CONSTRAINTS);
        value = email;
    }

    /**
     * Returns if a given string is a valid email.
     */
    public static boolean isValidEmail(String test) {
        return test.matches(EMAIL_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ProductsDistributorName // instanceof handles nulls
                && value.equals(((ProductsDistributorName) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
