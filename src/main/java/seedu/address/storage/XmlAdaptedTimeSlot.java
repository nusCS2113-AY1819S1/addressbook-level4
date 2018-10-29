package seedu.address.storage;

import java.time.DayOfWeek;
import java.time.LocalTime;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.TimeSlot;

/**
 * JAXB-friendly adapted version of the TimeSlot.
 */
public class XmlAdaptedTimeSlot {

    @XmlElement
    private String dayOfWeek;
    @XmlElement
    private String startTime;
    @XmlElement
    private String endTime;
    @XmlElement
    private String label;

    /**
     * Constructs an XmlAdaptedTimeSlot.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedTimeSlot() {}

    /**
     * Constructs a {@code XmlAdaptedTimeSlot} with the given {@code TimeSlot} for JAXB use..
     *
     * @param source future changes to this will not affect the created
     */
    public XmlAdaptedTimeSlot(TimeSlot source) {
        this.label = source.getLabel();
        this.dayOfWeek = source.getDayOfWeek().toString();
        this.startTime = source.getStartTime().toString();
        this.endTime = source.getEndTime().toString();
    }

    /**
     * Converts this jaxb-friendly adapted TimeSlot object into the model's TimeSlot object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public TimeSlot toModelType() throws IllegalValueException {
        //TODO: test and ensure that values are legal.

        DayOfWeek dayOfWeek = DayOfWeek.of(Integer.parseInt(this.dayOfWeek));
        LocalTime startTime = LocalTime.parse(this.startTime);
        LocalTime endTime = LocalTime.parse(this.endTime);
        String label = this.label;

        return new TimeSlot(dayOfWeek, startTime, endTime, label);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedTimeSlot)) {
            return false;
        }

        return dayOfWeek.equals(((XmlAdaptedTimeSlot) other).dayOfWeek)
                && startTime.equals(((XmlAdaptedTimeSlot) other).startTime)
                && endTime.equals(((XmlAdaptedTimeSlot) other).endTime)
                && label.equals(((XmlAdaptedTimeSlot) other).label);
    }
}
