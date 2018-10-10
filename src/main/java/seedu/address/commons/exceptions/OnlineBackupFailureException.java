package seedu.address.commons.exceptions;

/**
 * Signals that some online backup failed for some reason.
 */
public class OnlineBackupFailureException extends Exception {
    /**
     * @param message should contain relevant information on failure reason(s)
     */
    public OnlineBackupFailureException(String message) {
        super(message);
    }

    /**
     * @param message should contain relevant information on failure reason(s)
     * @param cause of the main exception
     */
    public OnlineBackupFailureException(String message, Throwable cause) {
        super(message, cause);
    }
}
