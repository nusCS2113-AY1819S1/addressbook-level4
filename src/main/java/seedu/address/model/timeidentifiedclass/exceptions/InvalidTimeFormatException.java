package seedu.address.model.timeidentifiedclass.exceptions;

/**
 *
 */
public class InvalidTimeFormatException extends Exception {
    public static final String EXCEPTION_MESSAGE = "Invalid date/time format.";

    public InvalidTimeFormatException(){}

    public String getExceptionMessage() {
        return EXCEPTION_MESSAGE;
    }
}
