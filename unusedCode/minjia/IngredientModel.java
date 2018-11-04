package seedu.address.model;

import java.util.List;

import seedu.address.model.ingredient.Ingredient;
import seedu.address.model.ingredient.IngredientQuantity;


// TODO: add more methods
/**
 * The API of the Ingredient DrinkModel component.
 */
public interface IngredientModel {

    /**
     * Clears existing backing model and replaces with the provided new data.
     * TODO: use the new data to replace original data
     */
    void resetData();

    /**
     * @return the list of ingredients
     * TODO: supposed to be ReadOnlyIngredientList - to be added
     */

    List<Ingredient> getAllIngredientsList();

    /**
     * Adds the given ingredient.
     * {@code ingredient} must not already exist in the ingredient list.
     */
    void addIngredient(Ingredient ingredient);

    /**
     * Updates the quantity of the given {@code ingredient}.
     * {@code quantity} should have an integer value that is positive or 0.
     */
    void updateIngredientQuantity(Ingredient ingredient, IngredientQuantity quantity);

    /**
     * Decrements the quantity of the given {@code ingredient} by stated quantity.
     * {@code quantity} should have an integer value that is greater than 0.
     */
    void decrementIngredientQuantity(Ingredient ingredient, IngredientQuantity quantity);

    /**
     * Deletes given ingredient.
     * {@code ingredient} must exist in the ingredient list.
     */
    void deleteIngredient(Ingredient ingredient);

    /**
     * @return true if an ingredient with the same identity as {@code ingredient}
     * exists in the list of ingredients.
     */
    boolean hasIngredient(Ingredient ingredient);
}
