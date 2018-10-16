package seedu.address.model.timeidentifiedclass.shopday.exceptions;

/**
 * This class represents Exceptions that are raised when there is a duplicate transaction.
 */
public class DuplicateTransactionException extends Exception {
    private static final String EXCEPTION_MESSAGE = "This day has already recorded another transaction"
            + " at the given time.";

    public DuplicateTransactionException() {}

    public static String getExceptionMessage() {
        return EXCEPTION_MESSAGE;
    }
}
