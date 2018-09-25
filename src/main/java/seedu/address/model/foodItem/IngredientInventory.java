package seedu.address.model.foodItem;

import seedu.address.model.foodItem.exceptions.DuplicateFoodItemException;

import java.util.TreeMap;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

/**
 * Represents a list of ingredients in the shop's inventory stock
 * A list of ingredients that enforces uniqueness between its element and does not allow nulls.
 * An ingredient is considered unique....
 *
 * Supports a minimal set of list operations.
 *
 */

public class IngredientInventory {

    TreeMap<IngredientName, Float> ingredientInventory = new TreeMap<>();

    /**
     * Adds a new ingredient to the shop's inventory stock
     * The ingredient must not already exist in the list
     */
    public void addIngredient(IngredientName toAdd, Float quantity) {
        requireAllNonNull(toAdd);
        if(contains(toAdd)) {
            throw new DuplicateFoodItemException();
        }
        ingredientInventory.put(toAdd, quantity);
    }

    /**
     * Returns true if the list contains an equivalent ingredient as the given argument.
     */
    public boolean contains(IngredientName toCheck) {
        requireNonNull(toCheck);
        return ingredientInventory.containsKey(toCheck);
    }


    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof IngredientInventory // instanceof handles nulls
                && ingredientInventory.equals(((IngredientInventory) other).ingredientInventory));
    }

    @Override
    public int hashCode() {
        return ingredientInventory.hashCode();
    }

}
