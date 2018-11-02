package seedu.address.model.timeidentifiedclass;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import seedu.address.model.timeidentifiedclass.exceptions.InvalidTimeFormatException;

/**
 * This represents a reminder that is set for a given day and time.
 */
public class Reminder extends TimeIdentifiedClass {

    public static final String REMINDER_TIME_CONSTRAINTS = "Reminder times must be in yyyy/MM/dd HH:mm:ss format.";

    private String time;
    private String reminderMessage;
    private boolean hasBeenShownByThread;

    /**
     * Constructor to set a reminder with time and reminder message.
     * @param time
     * @param reminderMessage
     * @throws InvalidTimeFormatException
     */
    public Reminder(String time, String reminderMessage) throws InvalidTimeFormatException {
        requireAllNonNull(time, reminderMessage);
        time = time.trim();

        if (!isValidReminderTime(time)) {
            throw new InvalidTimeFormatException();
        }
        this.time = time;
        this.reminderMessage = reminderMessage.trim();
        this.hasBeenShownByThread = false;
    }

    /**
     * No parameter constructor for tests
     */
    protected Reminder() {}

    public String getReminderMessage() {
        return reminderMessage;
    }

    public String getReminderTime() {
        return time;
    }

    /**
     * The following method allows us to change the time for a reminder. It also resets the
     * {@code hasBeenShownByThread} after changing the time.
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
        this.hasBeenShownByThread = false;
    }

    public void changeMessage(String newMessage) {
        this.reminderMessage = newMessage;
    }

    public boolean hasBeenShownByThread() {
        return hasBeenShownByThread;
    }

    public void declareAsShownByThread() {
        hasBeenShownByThread = true;
    }

    /**
     * The given method checks if the {@code reminderTim} is in the required day and time format.
     * @param reminderTime
     */
    public static boolean isValidReminderTime(String reminderTime) {
        return isValidDateAndTime(reminderTime);
    }
}
