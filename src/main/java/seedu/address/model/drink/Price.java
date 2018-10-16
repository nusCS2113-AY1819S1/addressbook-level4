package seedu.address.model.drink;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Food Item's price in the address book.
 * Guarantees: Is valid as declared in {@link #isValidPrice(String)}
 */
public class Price {
    public static final String MESSAGE_PRICE_CONSTRAINTS =
            "Price should contain only numbers and at most 1 decimal point. "
                    + "It must be least 1 digit long with a maximum of 2 digits after the decimal point";
    public static final String PRICE_VALIDATION_REGEX = "[0-9]+([.][0-9]{1,2})?";
    private float value;

    /**
     * Constructs a {@code Price}.
     *
     * @param price A valid price.
     */
    public Price(String price) {
        requireNonNull(price);
        checkArgument(isValidPrice(price), MESSAGE_PRICE_CONSTRAINTS);
        value = Float.parseFloat(price);
    }

    public float getValue() {
        return value;
    }

    public void setValue(String price) {
        requireNonNull(price);
        checkArgument(isValidPrice(price), MESSAGE_PRICE_CONSTRAINTS);
        value = Float.parseFloat(price);
    }

    /**
     * Returns true if a given string is a valid price.
     */
    public static boolean isValidPrice(String test) {
        return test.matches(PRICE_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return (Float.toString(value));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Price // instanceof handles nulls
                && (value == (((Price) other).value))); // state check
    }

    @Override
    public int hashCode() {
        return Float.hashCode(value);
    }

}


