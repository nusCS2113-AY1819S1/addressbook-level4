package seedu.address.storage;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.TimeSlots;

/**
 * JAXB-friendly adapted version of the Tag.
 */
public class XmlAdaptedTimeSlots {

    @XmlValue
    private String timeSlot;

    /**
     * Constructs an XmlAdaptedTag.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedTimeSlots() {}

    /**
     * Constructs a {@code XmlAdaptedTag} with the given {@code tagName}.
     */
    public XmlAdaptedTimeSlots(String timeSlot) {
        this.timeSlot = timeSlot;
    }

    /**
     * Converts a given Tag into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created
     */
    public XmlAdaptedTimeSlots(TimeSlots source) { timeSlot = source.toString(); }

    /**
     * Converts this jaxb-friendly adapted tag object into the model's Tag object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public TimeSlots toModelType() {
        return new TimeSlots(timeSlot);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedTimeSlots)) {
            return false;
        }

        return timeSlot.equals(((XmlAdaptedTimeSlots) other).timeSlot);
    }
}
