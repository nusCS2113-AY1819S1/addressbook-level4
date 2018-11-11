package seedu.address.model.transaction;

/**
 * Represents the ID of a transaction.
 * Value uses type Long.
 */
public class TransactionId {
    public static final String MESSAGE_TRANSACTION_ID_CONSTRAINTS = "Transaction Id must be a long value";
    public static final String TRANSACTION_ID_VALIDATION_REGEX = "\\d+";

    private final long value;
    /**
     * Constructs a {@code TransactionId}.
     *
     */
    public TransactionId() {
        this.value = new java.util.Date().getTime();
    }

    public TransactionId(String input) {
        this.value = Long.parseLong(input);
    }

    public long getValue() {
        return value;
    }

    /**
     * Returns true if a given string is a valid transaction id.
     */
    public static boolean isValidTransactionId(String test) {
        return test.matches(TRANSACTION_ID_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return Long.toString(value);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TransactionId // instanceof handles nulls
                && value == (((TransactionId) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return Math.toIntExact(value);
    }
}
