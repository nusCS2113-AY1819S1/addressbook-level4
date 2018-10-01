package seedu.address.model.fooditem;

/**
 * Represents a Food Item's name in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */

public class FoodName extends Name {
    public FoodName(String input) {
        super(input);
    }
}
