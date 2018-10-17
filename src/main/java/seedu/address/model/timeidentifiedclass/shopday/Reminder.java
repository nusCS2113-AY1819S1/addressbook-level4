package seedu.address.model.timeidentifiedclass.shopday;

import seedu.address.model.timeidentifiedclass.TimeIdentifiedClass;
import seedu.address.model.timeidentifiedclass.exceptions.InvalidTimeFormatException;
import seedu.address.model.timeidentifiedclass.transaction.Transaction;

/**
 * This represents a reminder that is set for a given day and time.
 */
public class Reminder extends TimeIdentifiedClass {

    public static final String REMINDER_TIME_CONSTRAINTS = "Reminder times must be in yyyy/MM/dd HH:mm:ss format.";

    private String time;
    private String reminderMessage;

    /**
     * Constructor to set a reminder with time and reminder message.
     * @param time
     * @param reminderMessage
     * @throws InvalidTimeFormatException
     */

    public Reminder(String time, String reminderMessage) throws InvalidTimeFormatException {

        // Reminders are set with the same time format as Transactions.
        if (!Transaction.isValidTransactionTime(time)) {
            throw new InvalidTimeFormatException();
        }
        this.time = time.trim();
        this.reminderMessage = reminderMessage.trim();
    }

    public String getMessage() {
        return reminderMessage;
    }

    public String getTime() {
        return time;
    }

    /**
     * The following method allows us to change the time for a reminder.
     * @param time
     * @throws InvalidTimeFormatException
     */

    public void changeTime(String time) throws InvalidTimeFormatException {
        if (!Transaction.isValidTransactionTime(time)) {
            throw new InvalidTimeFormatException();
        }
        this.time = time;
    }

    public void changeMessage(String newMessage) {
        this.reminderMessage = newMessage;
    }

}
