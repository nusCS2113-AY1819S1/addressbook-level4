package seedu.address.model.saleshistory.exceptions;

/**
 * TODO
 */
public class DuplicateDayException extends Exception {
    private static final String EXCEPTION_MESSAGE = "The given day is already present in history.";

    public DuplicateDayException() {}

    public static String getExceptionMessage() {
        return EXCEPTION_MESSAGE;
    }
}
