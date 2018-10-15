package seedu.address.model.drink;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents the identification number of a specific batch
 */

public class BatchId {
    public static final String MESSAGE_ID_CONSTRAINTS =
            "ID should only contain numbers";
    public static final String ID_VALIDATION_REGEX = "\\d+";

    private final int value;

    /**
     * Constructs an {@code BatchId}.
     *
     * @param id A valid ID value expressed as a string.
     */
    public BatchId(String id) {
        requireNonNull(id);
        checkArgument(isValidId(id), MESSAGE_ID_CONSTRAINTS);
        this.value = Integer.parseInt(id);
    }

    /**
     * Returns true if a given string is a valid id value.
     */
    public static boolean isValidId(String test) {
        return test.matches(ID_VALIDATION_REGEX);
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof BatchId // instanceof handles nulls
                && value == (((BatchId) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value; // primitive int is its own hash
    }
}
