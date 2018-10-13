package seedu.address.model.transaction.exceptions;

/**
 * todo
 */
public class ClosedTransactionException extends Exception {
    private static final String EXCEPTION_MESSAGE = "Cannot edit a closed transaction.";

    public ClosedTransactionException() {}

    public String message() {
        return EXCEPTION_MESSAGE;
    }
}

