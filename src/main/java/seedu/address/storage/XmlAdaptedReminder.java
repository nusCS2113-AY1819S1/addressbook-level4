package seedu.address.storage;

import java.util.Objects;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.todo.Title;
//import seedu.address.model.person.Date;
import seedu.address.model.person.Time;
//import seedu.address.model.reminder.Title;
import seedu.address.model.reminder.Date;
//import seedu.address.model.reminder.Time;
import seedu.address.model.reminder.Agenda;
import seedu.address.model.reminder.Reminder;

//@@author: junweiljw
/**
 * JAXB-friendly version of the Reminder.
 */
public class XmlAdaptedReminder {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Reminder's %s field is missing!";

    @XmlElement(required = true)
    private String title;
    @XmlElement(required = true)
    private String date;
    @XmlElement(required = true)
    private String time;
    @XmlElement(required = true)
    private String agenda;

    /**
     * Constructs an XmlAdaptedReminder.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedReminder() {}

    /**
     * Constructs an {@code XmlAdaptedReminder} with the given reminder details.
     */
    public XmlAdaptedReminder(String title, String date, String time, String agenda) {
        this.title = title;
        this.date = date;
        this.time = time;
        this.agenda = agenda;
    }

    /**
     * Converts a given reminder
     * into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedReminder
     */
    public XmlAdaptedReminder(Reminder source) {
        title = source.getTitle().value;
        date = source.getDate().value;
        time = source.getTime().value;
        agenda = source.getAgenda().value;
    }

    /**
     * Converts this jaxb-friendly adapted reminder object into the model's Reminder object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted reminder
     */
    public Reminder toModelType() throws IllegalValueException {
        if (title == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Title.class.getSimpleName()));
        }
        if (!Title.isValidTitle(title)) {
            throw new IllegalValueException(Title.MESSAGE_TITLE_CONSTRAINTS);
        }
        final Title modelTitle = new Title(title);

        if (date == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Date.class.getSimpleName()));
        }
        if (!Date.isValidDate(date)) {
            throw new IllegalValueException(Date.MESSAGE_DATE_CONSTRAINTS);
        }
        final Date modelDate = new Date(date);

        if (time == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Time.class.getSimpleName()));
        }
        if (!Time.isValidTime(time)) {
            throw new IllegalValueException(Time.MESSAGE_TIME_CONSTRAINTS);
        }
        final Time modelTime = new Time(time);

        if (agenda == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Agenda.class.getSimpleName()));
        }
        if (!Agenda.isValidAgenda(agenda)) {
            throw new IllegalValueException(Agenda.MESSAGE_AGENDA_CONSTRAINTS);
        }
        final Agenda modelAgenda = new Agenda(agenda);

        return new Reminder(modelTitle, modelDate, modelTime, modelAgenda);
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
        return Objects.equals(title, otherReminder.title)
                && Objects.equals(date, otherReminder.date)
                && Objects.equals(time, otherReminder.time)
                && Objects.equals(agenda, otherReminder.agenda);
    }
}
