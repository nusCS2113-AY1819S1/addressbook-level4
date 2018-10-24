package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

import seedu.address.model.ingredient.IceCream;
import seedu.address.model.ingredient.Ingredient;
import seedu.address.model.ingredient.IngredientQuantity;
import seedu.address.model.ingredient.Topping;
import seedu.address.model.ingredient.exceptions.DuplicateIngredientException;
import seedu.address.model.ingredient.exceptions.IngredientNotFoundException;





/**
 * Represents the list of ingredients in the store
 */
public class IngredientManager implements IngredientModel {
    private List<Topping> toppings;
    private List<IceCream> iceCreams;


    public IngredientManager() {
        // TODO: for now, always create new lists (i.e. doesn't save data yet)
        toppings = new ArrayList<>();
        iceCreams = new ArrayList<>();
    }


    @Override
    public void resetData() {
        toppings.clear();
        iceCreams.clear();
    }

    @Override
    public List<Ingredient> getAllIngredientsList() {
        // TODO: return an immutable list in the future
        List<Ingredient> allIngredients = new ArrayList<> ();

        allIngredients.addAll(iceCreams);
        allIngredients.addAll(toppings);

        return allIngredients;
    }

    @Override
    public void addIngredient(Ingredient ingredient) {
        // check if it is duplicate
        if (containsIngredient(ingredient)) {
            throw new DuplicateIngredientException();
        }

        if (ingredient instanceof IceCream) {
            IceCream ingredientAsIceCream = (IceCream) ingredient;
            iceCreams.add(ingredientAsIceCream);
        } else if (ingredient instanceof Topping) {
            Topping ingredientAsTopping = (Topping) ingredient;
            toppings.add(ingredientAsTopping);
        } else {
            assert false;
        }
    }

    /**
     * @param toCheck is the potential duplicate ingredient
     * @return true if {@code iceCreams} or {@code toppings} contains given ingredient.
     */
    private boolean containsIngredient(Ingredient toCheck) {
        requireNonNull(toCheck);

        if (toCheck instanceof IceCream) {
            return iceCreams.stream().anyMatch(toCheck::isSameIngredient); // NOTE: only checks for same name
        } else if (toCheck instanceof Topping) {
            return toppings.stream().anyMatch(toCheck::isSameIngredient);
        } else {
            assert false;

            return false;
        }
    }

    @Override
    public void updateIngredientQuantity(Ingredient ingredient, IngredientQuantity quantity) {
        if (!containsIngredient(ingredient)) {
            throw new IngredientNotFoundException();
        }

        Ingredient ingredientRef = findIngredientByName(ingredient);
        ingredientRef.setQuantity(quantity);
    }

    @Override
    public void decrementIngredientQuantity(Ingredient ingredient, IngredientQuantity quantity) {
        if (!containsIngredient(ingredient)) {
            throw new IngredientNotFoundException();
        }

        Ingredient ingredientRef = findIngredientByName(ingredient);
        int currentStock = ingredientRef.getQuantity().getValue();
        int newStock = currentStock - quantity.getValue();
        ingredientRef.getQuantity().setValue(newStock);
    }

    @Override
    public void deleteIngredient(Ingredient ingredient) {
        if (!containsIngredient(ingredient)) {
            throw new IngredientNotFoundException();
        }

        Ingredient ingredientRef = findIngredientByName(ingredient);
        if (ingredient instanceof IceCream) {
            iceCreams.remove(ingredientRef);
        } else if (ingredient instanceof Topping) {
            toppings.remove(ingredientRef);
        } else {
            assert false;
        }
    }

    @Override
    public boolean hasIngredient(Ingredient ingredient) {
        return containsIngredient(ingredient);
    }

    /**
     * Gets reference to ingredient object that is in the list of ingredients.
     * @return reference to ingredient object.
     */
    private Ingredient findIngredientByName(Ingredient ingredient) {
        if (ingredient instanceof IceCream) {
            int index = iceCreams.indexOf(ingredient);
            return iceCreams.get(index);
        } else if (ingredient instanceof Topping) {
            int index = toppings.indexOf(ingredient);
            return toppings.get(index);
        } else {
            assert false;

            return null;
        }
    }

}
