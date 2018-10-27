package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.TimeTable;

/**
 * JAXB-friendly adapted version of the TimeSlot.
 */
public class XmlAdaptedTimeTable {

    @XmlElement
    private List<XmlAdaptedTimeSlot> timeSlot = new ArrayList<>();

    /**
     * Constructs an XmlAdaptedTimeTable.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedTimeTable() {}

    /**
     * Converts a given TimeTable into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created
     */
    public XmlAdaptedTimeTable(TimeTable source) {
        timeSlot = (source.getTimeSlots().stream().map(XmlAdaptedTimeSlot::new).collect(Collectors.toList()));
    }

    /**
     * Converts this jaxb-friendly adapted TimeTable object into the model's TimeTable object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public TimeTable toModelType() throws IllegalValueException {
        //TODO: test and ensure that values are legal.
        /* if (!Tag.isValidTagName(tagName)) {
            throw new IllegalValueException(Tag.MESSAGE_CONSTRAINTS);
        }*/
        TimeTable timeTable = new TimeTable();
        for (XmlAdaptedTimeSlot timeSlot: this.timeSlot) {
            timeTable.addTimeSlot(timeSlot.toModelType());
        }
        return timeTable;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedTimeTable)) {
            return false;
        }

        return timeSlot.equals(((XmlAdaptedTimeTable) other).timeSlot);
    }
}
