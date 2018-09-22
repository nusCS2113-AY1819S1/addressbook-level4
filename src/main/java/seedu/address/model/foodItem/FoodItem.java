package seedu.address.model.foodItem;


import seedu.address.model.person.Name;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

/**
 * Represents a Food Item in the address book.
 * Guarantees: details are present and not null, field values are validated.
 */
public class FoodItem {
    private final Name name;
    private final Recipe recipe;
    private Price price;

    public FoodItem(Name name, Recipe recipe, Price price) {
        requireAllNonNull(name, recipe, price);
        this.name = name;
        this.recipe = recipe;
        this.price = price;
    }

    public Name getName() { return name; }

    public Recipe getRecipe() { return recipe; }

    public Price getPrice() { return price; }


    /**
     * Returns true if both food items of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSameFoodItem(FoodItem otherFoodItem) {
        if (otherFoodItem == this) {
            return true;
        }

        return otherFoodItem != null
                && otherFoodItem.getName().equals(getName())
                && otherFoodItem.getRecipe().equals(getRecipe());
    }

    /**
     * Returns true if both food items have the same identity and data fields.
     * This defines a stronger notion of equality between two food items.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof FoodItem)) {
            return false;
        }

        FoodItem otherFoodItem = (FoodItem) other;
        return otherFoodItem.getName().equals(getName())
                && otherFoodItem.getRecipe().equals(getRecipe())
                && otherFoodItem.getPrice().equals(getPrice());
    }
}
