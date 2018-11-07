//@@author ian-tjahjono
package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.EventBook;
import seedu.address.model.ReadOnlyEventBook;
import seedu.address.model.event.Event;

/**
 * An Immutable EventBook that is serializable to XML format
 */
@XmlRootElement(name = "eventbook")
public class XmlSerializableEventBook {

    public static final String MESSAGE_DUPLICATE_EVENT = "Event list contains duplicate event(s).";

    @XmlElement
    private List<XmlAdaptedEvent> event;

    /**
     * Creates an empty XmlSerializableEventBook.
     * This empty constructor is required for marshalling.
     */
    public XmlSerializableEventBook() {
        event = new ArrayList<>();
    }

    /**
     * Conversion
     */
    public XmlSerializableEventBook(ReadOnlyEventBook src) {
        this();
        event.addAll(src.getEventList().stream().map(XmlAdaptedEvent::new).collect(Collectors.toList()));
    }

    /**
     * Converts this eventbook into the model's {@code EventBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated
     */
    public EventBook toModelType() throws IllegalValueException {
        EventBook eventBook = new EventBook();
        for (XmlAdaptedEvent p : event) {
            Event event = p.toModelType();
            eventBook.addEvent(event);
        }
        return eventBook;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlSerializableEventBook)) {
            return false;
        }
        return event.equals(((XmlSerializableEventBook) other).event);
    }
}
