package seedu.address.model.ingredient;

import java.util.Objects;

/**
 * Represents an ice cream flavour in a food item or recipe
 */
public class IceCream extends Ingredient {
    public IceCream(IngredientName name, IngredientQuantity quantity) {
        super(name, quantity);
    }

    public IceCream(IngredientName name) {
        super(name);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (!(other instanceof IceCream)) {
            return false;
        }

        IceCream otherIceCream = (IceCream) other;
        return otherIceCream.getName().equals(getName())
                && otherIceCream.getQuantity() == getQuantity();
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
