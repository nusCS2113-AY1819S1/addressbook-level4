package seedu.address.model.fooditem;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

/**
 * Represents a Food Item in the address book.
 * Guarantees: details are present and not null, field values are validated.
 */

public class FoodItem {
    private final FoodName foodName;
    private final FoodRecipe foodRecipe;
    private FoodPrice foodPrice;

    public FoodItem(FoodName name, FoodRecipe recipe, FoodPrice price) {
        requireAllNonNull(name, recipe, price);
        this.foodName = name;
        this.foodRecipe = recipe;
        this.foodPrice = price;
    }

    public FoodName getFoodName() {
        return foodName;
    }

    public FoodRecipe getFoodRecipe() {
        return foodRecipe;
    }

    public FoodPrice getFoodPrice() {
        return foodPrice;
    }


    /**
     * Returns true if both food items of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSameFoodItem(FoodItem otherFoodItem) {
        if (otherFoodItem == this) {
            return true;
        }

        return otherFoodItem != null
                && otherFoodItem.getFoodName().equals(getFoodName())
                && otherFoodItem.getFoodRecipe().equals(getFoodRecipe());
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
        return otherFoodItem.getFoodName().equals(getFoodName())
                && otherFoodItem.getFoodRecipe().equals(getFoodRecipe())
                && otherFoodItem.getFoodPrice().equals(getFoodPrice());
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getFoodName())
                .append(" Price: $")
                .append(getFoodPrice())
                .append(" Recipe: ")
                .append(getFoodRecipe());
        return builder.toString();
    }
}
