package seedu.address.model.ingredient;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;


/**
 * Represents an ingredient's quantity in the list of ingredients
 * Guarantees: TODO
 */
public class IngredientQuantity {
    public static final String MESSAGE_QUANTITY_CONSTRAINTS =
            "Quantity should only contain numbers";
    public static final String QUANTITY_VALIDATION_REGEX = "\\d+";

    private int value;

    /**
     * Constructs an {@code IngredientQuantity}.
     *
     * @param quantity A valid quantity value expressed as a string.
     */
    public IngredientQuantity(String quantity) {
        requireNonNull(quantity);
        checkArgument(isValidQuantity(quantity), MESSAGE_QUANTITY_CONSTRAINTS);
        this.value = Integer.parseInt(quantity);
    }

    /**
     * Returns true if a given string is a valid quantity value.
     */
    public static boolean isValidQuantity(String test) {
        return test.matches(QUANTITY_VALIDATION_REGEX);
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
                || (other instanceof IngredientQuantity // instanceof handles nulls
                && value == (((IngredientQuantity) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value; // primitive int is its own hash
    }

}
