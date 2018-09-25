package seedu.address.model.foodItem;

import java.util.TreeMap;

/**
 * Represents a Food Item's recipe in the address book.
 * A list of ingredients that enforces uniqueness between its element and does not allow nulls.
 * An ingredient is considered unique....
 *
 * Supports a minimal set of list operations.
 *
 */

public class FoodRecipe {

    TreeMap<Float, FoodName> foodRecipe = new TreeMap<>();

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FoodRecipe // instanceof handles nulls
                && foodRecipe.equals(((FoodRecipe) other).foodRecipe));
    }

    @Override
    public int hashCode() {
        return foodRecipe.hashCode();
    }
}
