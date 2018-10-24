package seedu.address.model.timeidentifiedclass.shopday;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import seedu.address.model.timeidentifiedclass.TimeIdentifiedClass;
import seedu.address.model.timeidentifiedclass.exceptions.InvalidTimeFormatException;

/**
 * This represents a reminder that is set for a given day and time.
 */
public class Reminder extends TimeIdentifiedClass {

    public static final String REMINDER_TIME_CONSTRAINTS = "Reminder times must be in dd/MM/yyyy HH:mm:ss format.";

    private String time;
    private String reminderMessage;

    /**
     * Constructor to set a reminder with time and reminder message.
     * @param time
     * @param reminderMessage
     * @throws InvalidTimeFormatException
     */
    public Reminder(String time, String reminderMessage) throws InvalidTimeFormatException {
        requireAllNonNull(time, reminderMessage);
        time = time.trim();
        // Reminders are set with in date and time format.
        if (!super.isValidDateAndTime(time)) {
            throw new InvalidTimeFormatException();
        }
        this.time = time;
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
        requireNonNull(time);
        time = time.trim();
        if (!super.isValidDateAndTime(time)) {
            throw new InvalidTimeFormatException();
        }
        this.time = time;
    }

    public void changeMessage(String newMessage) {
        this.reminderMessage = newMessage;
    }

}
