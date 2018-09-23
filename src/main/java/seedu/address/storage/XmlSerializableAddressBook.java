package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.EventManager;
import seedu.address.model.ReadOnlyEventManager;
import seedu.address.model.event.Event;

/**
 * An Immutable EventManager that is serializable to XML format
 */
@XmlRootElement(name = "addressbook")
public class XmlSerializableAddressBook {

    public static final String MESSAGE_DUPLICATE_PERSON = "Persons list contains duplicate event(s).";

    @XmlElement
    private List<XmlAdaptedPerson> events;

    /**
     * Creates an empty XmlSerializableAddressBook.
     * This empty constructor is required for marshalling.
     */
    public XmlSerializableAddressBook() {
        events = new ArrayList<>();
    }

    /**
     * Conversion
     */
    public XmlSerializableAddressBook(ReadOnlyEventManager src) {
        this();
        events.addAll(src.getEventList().stream().map(XmlAdaptedPerson::new).collect(Collectors.toList()));
    }

    /**
     * Converts this addressbook into the model's {@code EventManager} object.
     *
     * @throws IllegalValueException if there were any data constraints violated or duplicates in the
     * {@code XmlAdaptedPerson}.
     */
    public EventManager toModelType() throws IllegalValueException {
        EventManager eventManager = new EventManager();
        for (XmlAdaptedPerson p : events) {
            Event event = p.toModelType();
            if (eventManager.hasEvent(event)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }
            eventManager.addEvent(event);
        }
        return eventManager;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlSerializableAddressBook)) {
            return false;
        }
        return events.equals(((XmlSerializableAddressBook) other).events);
    }
}
