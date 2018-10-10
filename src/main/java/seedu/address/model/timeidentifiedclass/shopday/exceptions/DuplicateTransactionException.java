package seedu.address.model.timeidentifiedclass.shopday.exceptions;

import java.lang.Exception;

public class DuplicateTransactionException extends Exception {
    private static String EXCEPTION_MESSAGE = "This day has already recorded another transaction at the given time.";

    public DuplicateTransactionException() {}

    public static String getExceptionMessage() {
        return EXCEPTION_MESSAGE;
    }
}
