package seedu.address.model.timeidentifiedclass.exceptions;

/**
 * Exception for duplicate reminders within the same day
 */

public class DuplicateReminderException extends Exception {

    private static final String EXCEPTION_MESSAGE = "This reminder has already been set.";

    public DuplicateReminderException() { }

    public String getExceptionMessage() {
        return EXCEPTION_MESSAGE;
    }
}
