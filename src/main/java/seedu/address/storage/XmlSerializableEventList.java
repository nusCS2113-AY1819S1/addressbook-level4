package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.EventList;
import seedu.address.model.ReadOnlyEventList;
import seedu.address.model.event.Event;

//@@author: IcedCoffeeBoy

/**
 * An Immutable EventList that is serializable to XML format
 */
@XmlRootElement(name = "eventlist")
public class XmlSerializableEventList {

    public static final String MESSAGE_DUPLICATE_EVENT = "Event list contains duplicate event(s).";

    @XmlElement
    private List<XmlAdaptedEvent> events;

    /**
     * Creates an empty XmlSerializableAddressBook.
     * This empty constructor is required for marshalling.
     */
    public XmlSerializableEventList() {
        events = new ArrayList<>();
    }

    /**
     * Conversion
     */
    public XmlSerializableEventList(ReadOnlyEventList src) {
        this();
        events.addAll(src.getEventList().stream().map(XmlAdaptedEvent::new).collect(Collectors.toList()));
    }

    /**
     * Converts this XML event list into the model's {@code EventList} object.
     *
     * @throws IllegalValueException if there were any data constraints violated or duplicates in the
     * {@code XmlAdaptedPerson}.
     */
    public EventList toModelType() throws IllegalValueException {
        EventList eventList = new EventList();
        for (XmlAdaptedEvent e : events) {
            Event event = e.toModelType();
            if (eventList.hasEvent(event)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_EVENT);
            }
            eventList.addEvent(event);
        }
        return eventList;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlSerializableEventList)) {
            return false;
        }
        return events.equals(((XmlSerializableEventList) other).events);
    }
}
