package seedu.address.model.book;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Book's address in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidAddress(String)}
 */
public class Quantity {

    public static final String MESSAGE_ADDRESS_CONSTRAINTS =
            "Quantity can take any values, and it should not be blank";

    /*
     * Quantity only accepts from 0 to 999
     */
    public static final String QUANTITY_VALIDATION_REGEX = "\\d{1,3}";

    private String value;
    /**
     * Constructs an {@code Quantity}.
     *
     * @param quantity A valid quantity.
     */
    public Quantity(String quantity) {
        requireNonNull(quantity);
        checkArgument(isValidAddress(quantity), MESSAGE_ADDRESS_CONSTRAINTS);
        value = quantity;
    }
    public String getValue() {
        return value;
    }

    public void increase(int amount) {
        this.value = Integer.toString(Integer.parseInt(value) + amount);
    }

    public int toInteger() {
        return Integer.parseInt(value);
    }

    public void setValue(String value) {
        this.value = value;
    }


    /**
     * Returns true if a given string is a valid quantity.
     */
    public static boolean isValidAddress(String test) {
        return test.matches(QUANTITY_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Quantity // instanceof handles nulls
                && value.equals(((Quantity) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
