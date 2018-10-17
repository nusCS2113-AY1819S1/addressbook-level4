package seedu.address.storage;

import javax.xml.bind.annotation.XmlValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.attendee.Attendee;

public class XmlAdaptedAttendee {

    @XmlValue
    private String attendeeName;

    /**
     * Constructs an XmlAdaptedAttendee.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedAttendee() {}

    /**
     * Constructs a {@code XmlAdaptedAttendee} with the given {@code attendeeName}.
     */
    public XmlAdaptedAttendee(String tagName) {
        this.attendeeName = attendeeName;
    }

    /**
     * Converts a given Attendee into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created
     */
    public XmlAdaptedAttendee(Attendee source) {
        attendeeName = source.attendeeName;
    }

    /**
     * Converts this jaxb-friendly adapted tag object into the model's Attendee object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted event
     */
    public Attendee toModelType() throws IllegalValueException {
        if (!Attendee.isValidAttendeeName(attendeeName)) {
            throw new IllegalValueException(Attendee.MESSAGE_ATTENDEE_CONSTRAINTS);
        }
        return new Attendee(attendeeName);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedAttendee)) {
            return false;
        }

        return attendeeName.equals(((XmlAdaptedAttendee) other).attendeeName);
    }
}
