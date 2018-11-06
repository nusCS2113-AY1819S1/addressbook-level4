package seedu.address.model.ingredient;

import java.util.Objects;

/**
 * Represents a topping in a food item or recipe
 */
public class Topping extends Ingredient {

    public Topping(IngredientName name, IngredientQuantity quantity) {
        super(name, quantity);
    }

    public Topping(IngredientName name) {
        super(name);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (!(other instanceof Topping)) {
            return false;
        }

        Topping otherTopping = (Topping) other;
        return otherTopping.getName().equals(getName())
                && otherTopping.getQuantity() == getQuantity();
    }


    @Override
    public int hashCode() {
        return Objects.hash(name, quantity);
    }
    //author @tianhang
    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName());
        return builder.toString();
    }
    //author @tianhang


}
