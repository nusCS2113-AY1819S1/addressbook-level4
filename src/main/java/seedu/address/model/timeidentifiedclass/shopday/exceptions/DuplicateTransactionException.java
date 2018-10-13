package seedu.address.model.timeidentifiedclass.shopday.exceptions;

/**
 * TODO
 */
public class DuplicateTransactionException extends Exception {
    private static final String EXCEPTION_MESSAGE = "This day has already recorded another transaction at the given time.";

    public DuplicateTransactionException() {}

    public static String getExceptionMessage() {
        return EXCEPTION_MESSAGE;
    }
}
