package seedu.address.storage;

import java.util.Objects;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.EventName;
import seedu.address.model.person.Schedule;
import seedu.address.model.person.TheDate;
import seedu.address.model.person.Time;

//@@author: driedmelon
/**
 * JAXB-friendly version of the Schedule.
 */
public class XmlAdaptedSchedule {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Schedule %s field is missing!";

    @XmlElement(required = true)
    private String date;
    @XmlElement(required = true)
    private String startTime;
    @XmlElement(required = true)
    private String endTime;
    @XmlElement(required = true)
    private String eventName;
    @XmlElement(required = true)
    private String schedulePrint;

    /**
     * Constructs an XmlAdaptedSchedule.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedSchedule() {}

    /**
     * Constructs an {@code XmlAdaptedPerson} with the given person details.
     */
    public XmlAdaptedSchedule(String date, String startTime, String endTime, String eventName) {
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.eventName = eventName;
        this.schedulePrint = schedulePrint;
    }

    /**
     * Converts a given Person into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedPerson
     */
    public XmlAdaptedSchedule(Schedule source) {
        date = source.getDate().value;
        startTime = source.getStartTime().value;
        endTime = source.getEndTime().value;
        eventName = source.getEventName().value;
        schedulePrint = source.schedulePrint;
    }

    /**
     * Converts this jaxb-friendly adapted person object into the model's Person object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Schedule toModelType() throws IllegalValueException {
        if (date == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, TheDate.class.getSimpleName()));
        }
        if (!TheDate.isValidDate(date)) {
            throw new IllegalValueException(TheDate.MESSAGE_DATE_CONSTRAINTS);
        }
        final TheDate modelDate = new TheDate(date);

        if (startTime == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Time.class.getSimpleName()));
        }
        if (!Time.isValidTime(startTime)) {
            throw new IllegalValueException(Time.MESSAGE_TIME_CONSTRAINTS);
        }
        final Time modelStartTime = new Time(startTime);

        if (endTime == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Time.class.getSimpleName()));
        }
        if (!Time.isValidTime(endTime)) {
            throw new IllegalValueException(Time.MESSAGE_TIME_CONSTRAINTS);
        }

        if (!Schedule.isValidStartEnd(startTime, endTime)) {
            throw new IllegalValueException(Schedule.MESSAGE_START_END_CONSTRAINTS);
        }

        final Time modelEndTime = new Time(endTime);

        if (eventName == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    EventName.class.getSimpleName()));
        }
        if (!EventName.isValidEventName(eventName)) {
            throw new IllegalValueException(EventName.MESSAGE_EVENT_NAME_CONSTRAINTS);
        }
        final EventName modelEventName = new EventName(eventName);

        return new Schedule(modelDate, modelStartTime, modelEndTime, modelEventName);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedSchedule)) {
            return false;
        }

        XmlAdaptedSchedule otherSchedule = (XmlAdaptedSchedule) other;
        return Objects.equals(date, otherSchedule.date)
                && Objects.equals(startTime, otherSchedule.startTime)
                && Objects.equals(endTime, otherSchedule.endTime)
                && Objects.equals(eventName, otherSchedule.eventName);
    }
}
