package seedu.address.model.book;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a book's cost price in the book inventory
 * Guarantee: immutable; is a valid as declared in (@link #isValidCost(String)
 */
public class Cost {

    public static final String COST_CONSTRAINTS = "Price should be of the format 19.99 "
            + "and adhere to the following constraints:\n"
            + "1. The first 2 characters should only contain digits\n"
            + "2. This is followed by a '.' and then 2 digits. ";
    // digits
    private static final String DOLLAR_REGEX = "\\d{1,}";
    private static final String CENT_REGEX = "\\d{2,}"; // digits
    public static final String COST_VALIDATION_REGEX = DOLLAR_REGEX + "."
            + CENT_REGEX;

    public final String value;

    /**
     * Constructs an {@code Cost}.
     *
     * @param cost A valid cost
     */
    public Cost(String cost) {
        requireNonNull(cost);
        checkArgument(isValidCost(cost), COST_CONSTRAINTS);
        value = cost;
    }

    /**
     * Returns if a given string is a valid cost
     */
    public static boolean isValidCost(String test) {
        return test.matches(COST_VALIDATION_REGEX);
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
