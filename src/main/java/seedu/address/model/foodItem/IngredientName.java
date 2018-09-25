package seedu.address.model.foodItem;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Ingredient's name in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */

// To be replaced with a extends once I understand more

public class IngredientName {
    public static final String MESSAGE_NAME_CONSTRAINTS =
            "Ingredient names should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String NAME_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String ingredientName;

    /**
     * Constructs a {@code Name}.
     *
     * @param name A valid name.
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
    public int hashCode() { return ingredientName.hashCode(); }

}
