package seedu.address.model.book;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a book's cost price in the book inventory
 * Guarantee: immutable; is a valid as declared in (@link #isValidCost(String)
 */
public class Cost {

    public static final String MESSAGE_COST_CONSTRAINTS =
            "Costs should be numerical and in 2 decimal places or none at all\n"
            + "E.g. $4, $3.02";
    // "$" can be omitted and is optional, prices can be in 2 decimal places or none at all
    // e.g. $4 or $3.02 is accepted
    public static final String COST_VALIDATION_REGEX = "(\\$)?\\d+(\\.\\d{2})?";

    public final String value;

    /**
     * Constructs an {@code Cost}.
     *
     * @param cost A valid cost
     */
    public Cost(String cost) {
        requireNonNull(cost);
        checkArgument(isValidCost(cost), MESSAGE_COST_CONSTRAINTS);
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
