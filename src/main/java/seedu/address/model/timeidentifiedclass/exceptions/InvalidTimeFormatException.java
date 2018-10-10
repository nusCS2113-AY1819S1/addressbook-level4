package seedu.address.model.timeidentifiedclass.exceptions;

import java.lang.Exception;

public class InvalidTimeFormatException extends Exception {
    private static String EXCEPTION_MESSAGE = "Invalid date/time format.";

    public InvalidTimeFormatException(){}

    public String getExceptionMessage() {
        return EXCEPTION_MESSAGE;
    }
}
