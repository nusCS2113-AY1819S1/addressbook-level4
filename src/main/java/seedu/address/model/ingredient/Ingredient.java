package seedu.address.model.ingredient;

/**
 * Represents an ingredient that makes up a food item
 * Guarantees: TODO
 */
public abstract class Ingredient {

    // Identity field
    protected final IngredientName name;

    // Data fields
    protected IngredientQuantity quantity;
    // TODO: ingredient cost

    public Ingredient(IngredientName name, IngredientQuantity quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public Ingredient(IngredientName name) {
        this.name = name;
        quantity = new IngredientQuantity("0");
    }

    public IngredientName getName() {
        return name;
    }

    public IngredientQuantity getQuantity() {
        return quantity;
    }

    public void setQuantity(IngredientQuantity quantity) {
        this.quantity = quantity;
    }

    @Override
    public abstract boolean equals(Object other);

    @Override
    public abstract int hashCode();
}
