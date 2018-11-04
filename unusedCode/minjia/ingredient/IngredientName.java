package seedu.address.model.ingredient;

import static java.util.Objects.requireNonNull;

import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents an Ingredient's name in the list of ingredients.
 * Guarantees: TODO
 */

public class IngredientName {
    public static final String MESSAGE_NAME_CONSTRAINTS =
            "Names should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String NAME_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String ingredientName;

    /**
     * Constructs an {@code IngredientName}.
     *
     * @param name A valid ingredient name.
     */
    public IngredientName(String name) {
        requireNonNull(name);
        checkArgument(isValidName(name), MESSAGE_NAME_CONSTRAINTS);
        ingredientName = name;
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidName(String test) {
        return test.matches(NAME_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return ingredientName;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof IngredientName // instanceof handles nulls
                && ingredientName.equals(((IngredientName) other).ingredientName)); // state check
    }

    @Override
    public int hashCode() {
        return ingredientName.hashCode();
    }
}
