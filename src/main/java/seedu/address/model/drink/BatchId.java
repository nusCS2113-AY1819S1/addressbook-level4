//@@author Lunastryke
package seedu.address.model.drink;

import java.time.LocalDateTime;

/**
 * Represents the identification number of a specific batch
 */

public class BatchId {
    private final int value;

    /**
     * Constructs an {@code BatchId}.
     *
     */
    public BatchId() {
        this.value = Integer.parseInt(LocalDateTime.now().toString());
    }

    public BatchId(String input) {
        this.value = Integer.parseInt(input);
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
