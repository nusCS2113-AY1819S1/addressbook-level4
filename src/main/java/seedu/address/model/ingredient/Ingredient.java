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

    /**
     * @param ingredient is the ingredient to check.
     * @return true if both ingredients of the same name have
     * at least one other identity field that is the same.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSameIngredient(Ingredient ingredient) {
        if (ingredient == this) {
            return true;
        }

        return ingredient != null
                && ingredient.getName().equals(getName());
                // && (ingredient.getQuantity().equals(getQuantity()));
                // TODO: consider whether this is necessary
    }
    @Override
    public abstract boolean equals(Object other);

    @Override
    public abstract int hashCode();
}
