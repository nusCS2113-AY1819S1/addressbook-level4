package seedu.address.model.saleshistory.exceptions;

import java.lang.Exception;

public class DuplicateDayException extends Exception {
    private static String EXCEPTION_MESSAGE = "The given day is already present in history.";

    public DuplicateDayException() {}

    public static String getExceptionMessage() {
        return EXCEPTION_MESSAGE;
    }
}
