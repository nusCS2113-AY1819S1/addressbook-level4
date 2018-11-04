package seedu.address.storage;

import java.util.Objects;
import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.timeidentifiedclass.Reminder;
import seedu.address.model.timeidentifiedclass.exceptions.InvalidTimeFormatException;

/**
 * This class adapts Transaction for JAXB.
 */

public class XmlAdaptedReminder {

    private static final String INCORRECT_REMINDER_TIME_MESSAGE_FORMAT = "Incorrect reminder time of %s found!";
    private static final String MISSING_FIELD_MESSAGE_FORMAT = "Reminders' %s field is missing!";

    @XmlElement(required = true)
    private String reminderTime;
    @XmlElement(required = true)
    private String reminderMessage;

    /**
     * Constructs an {@code XmlAdaptedReminder}.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedReminder() {}

    /**
     * Constructs an {@code XmlAdaptedReminder} with the required reminder details.
     *
     * @param reminderTime
     * @param reminderMessage
     */

    public XmlAdaptedReminder(String reminderTime, String reminderMessage) {
        this.reminderTime = reminderTime;
        this.reminderMessage = reminderMessage;
    }

    /**
     * Converts a given reminder into this class for JAXB use.
     *
     * @param reminder
     */

    public XmlAdaptedReminder(Reminder reminder) {
        reminderTime = reminder.getReminderTime();
        reminderMessage = reminder.getReminderMessage();
    }

    /**
     * This method converts the XmlAdaptedReminder into a Transacation object, and returns it.
     * @return reminder
     * @throws IllegalValueException
     */

    public Reminder toModelType() throws IllegalValueException {

        if (reminderTime == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "reminder time"));
        }

        if (reminderMessage == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "reminder message"));
        }

        Reminder reminder;

        try {
            reminder = new Reminder(reminderTime, reminderMessage);
        } catch (InvalidTimeFormatException e) {
            throw new IllegalValueException(String.format(INCORRECT_REMINDER_TIME_MESSAGE_FORMAT,
                    reminderTime));
        }
        return reminder;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedReminder)) {
            return false;
        }

        XmlAdaptedReminder otherReminder = (XmlAdaptedReminder) other;
        return Objects.equals(reminderMessage, otherReminder.reminderMessage)
                && Objects.equals(reminderTime, otherReminder.reminderTime);
    }

}
