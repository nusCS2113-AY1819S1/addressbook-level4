package seedu.divelog.model.dive.exceptions;

/**
 * Thrown if malformatted time is detected
 */
public class InvalidTimeException extends Exception {
    public InvalidTimeException(String message) {
        super(message);
    }
    public InvalidTimeException(String message, Throwable cause) {
        super(message, cause);
    }
}

