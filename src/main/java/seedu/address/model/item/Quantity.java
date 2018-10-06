package seedu.address.model.item;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import seedu.address.commons.util.StringUtil;

/**
 * Represents an Item's quantity in the stock list.
 * Guarantees: immutable; is valid as declared in {@link #isValidQuantity(String)}
 */
public class Quantity {

    public static final String MESSAGE_QUANTITY_CONSTRAINTS =
            "Quantities should only contain non-zero unsigned integers, and it should not be blank";
    public static final String MESSAGE_MIN_QUANTITY_CONSTRAINTS =
            "Minimum Quantities should only contain non-zero unsigned integers, and it should not be blank";


    public final Integer quantity;

    /**
     * Constructs a {@code Quantity}.
     *
     * @param inputQuantity A valid quantity.
     */
    public Quantity(String inputQuantity) {
        requireNonNull(inputQuantity);
        checkArgument(isValidQuantity(inputQuantity), MESSAGE_QUANTITY_CONSTRAINTS);
        quantity = Integer.parseInt(inputQuantity);
    }

    /**
     * Returns true if a given integer is a valid quantity.
     */
    public static boolean isValidQuantity(String test) {
        return StringUtil.isNonZeroUnsignedInteger(test);
    }

    @Override
    public String toString() {
        return quantity.toString();
    }

    public Integer toInteger() {
        return quantity;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Quantity // instanceof handles nulls
                && quantity.equals(((Quantity) other).quantity)); // state check
    }

    @Override
    public int hashCode() {
        return quantity.hashCode();
    }
}
