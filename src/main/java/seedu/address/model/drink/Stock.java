package seedu.address.model.drink;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents an Drink's total stock (i.e. quantity) in terms of unit.
 * Guarantees: TODO
 */
public class Stock {
    public static final String MESSAGE_QUANTITY_CONSTRAINTS =
            "Stock should only contain numbers";
    public static final String STOCK_VALIDATION_REGEX = "\\d+";
    public static final String UNIT = "carton";

    private int value;

    /**
     * Constructs an {@code Stock}.
     *
     * @param quantity A valid stock value expressed as a string.
     */
    public Stock(String quantity) {
        requireNonNull(quantity);
        checkArgument(isValidStock(quantity), MESSAGE_QUANTITY_CONSTRAINTS);
        this.value = Integer.parseInt(quantity);
    }

    /**
     * Returns true if a given string is a valid quantity value.
     */
    public static boolean isValidStock(String test) {
        return test.matches(STOCK_VALIDATION_REGEX);
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Stock // instanceof handles nulls
                && value == (((Stock) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value; // primitive int is its own hash
    }

}
