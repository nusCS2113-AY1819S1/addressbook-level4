package seedu.address.model.item;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 *  Represents a item's location in the inventory.
 *  Guarantees: immutable; is valid as declared in {@link #isValidItemLocation(String)}
 */
public class ItemLocation {

    public static final String MESSAGE_ITEM_LOCATION_CONSTRAINTS =
            "Locations should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String LOCATION_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String fullItemLocation;

    /**
     * Constructs a {@code ItemLocation}.
     *
     * @param itemLocation A valid item location.
     */
    public ItemLocation(String itemLocation) {
        requireNonNull(itemLocation);
        checkArgument(isValidItemLocation(itemLocation), MESSAGE_ITEM_LOCATION_CONSTRAINTS);
        fullItemLocation = itemLocation;
    }

    /**
     * Returns true if a given string is a valid location.
     */
    public static Boolean isValidItemLocation(String test) {
        return test.matches(LOCATION_VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return fullItemLocation;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ItemLocation // instanceof handles nulls
                && fullItemLocation.equals(((ItemLocation) other).fullItemLocation)); // state check
    }

    @Override
    public int hashCode() {
        return fullItemLocation.hashCode();
    }

}
