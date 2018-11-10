//@@author Lunastryke
package seedu.address.model.drink;

import java.time.Instant;

/**
 * Represents the identification number of a specific batch
 */

public class BatchId {

    public static final String MESSAGE_BATCH_ID_CONSTRAINTS = "Batch Id must be an integer value";
    public static final String BATCH_ID_VALIDATION_REGEX = "\\d+";

    private final int value;
    /**
     * Constructs an {@code BatchId}.
     *
     */
    public BatchId() {
        this.value = (int) Instant.now().getEpochSecond();
    }

    public BatchId(String input) {
        this.value = Integer.parseInt(input);
    }

    public int getValue() {
        return value;
    }

    /**
     * Returns true if a given string is a valid batch id.
     */
    public static boolean isValidBatchId(String test) {
        return test.matches(BATCH_ID_VALIDATION_REGEX);
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
