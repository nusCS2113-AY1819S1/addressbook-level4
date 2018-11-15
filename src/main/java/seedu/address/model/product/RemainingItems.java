package seedu.address.model.product;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Product's Remaining  items in the Product database.
 * Guarantees: immutable; is valid as declared in {@link #isValidRemainingItems(String)}
 */
public class RemainingItems {


    public static final String MESSAGE_REMAINING_ITEMS_CONSTRAINTS =
            "Remaining items should only contain numbers, and it can not be negative";
    public static final String REMAINING_ITEMS_VALIDATION_REGEX = "^\\d+$";
    public final String value;

    /**
     * Constructs a {@code RemainingItems}.
     *
     * @param remainingItems A valid remaining items.
     */
    public RemainingItems(String remainingItems) {
        requireNonNull(remainingItems);
        checkArgument(isValidRemainingItems(remainingItems), MESSAGE_REMAINING_ITEMS_CONSTRAINTS);
        value = remainingItems;
    }

    /**
     * Returns true if a given string is a valid phone number.
     */
    public static boolean isValidRemainingItems(String test) {
        return test.matches(REMAINING_ITEMS_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RemainingItems // instanceof handles nulls
                && value.equals(((RemainingItems) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
