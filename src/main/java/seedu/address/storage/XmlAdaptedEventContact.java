//@@author ian-tjahjono
package seedu.address.storage;

import javax.xml.bind.annotation.XmlValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.eventContacts.EventContacts;

/**
 * JAXB-friendly adapted version of the EventContacts.
 */
public class XmlAdaptedEventContact {

    @XmlValue
    private String eventContact;

    /**
     * Constructs an XmlAdaptedEventContact.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedEventContact() {}

    /**
     * Constructs a {@code XmlAdaptedEventContact} with the given {@code eventContact}.
     */
    public XmlAdaptedEventContact(String eventContact) {
        this.eventContact = eventContact;
    }

    /**
     * Converts a given Event Contact into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created
     */
    public XmlAdaptedEventContact(EventContacts source) {
        eventContact = source.eventContactName;
    }

    /**
     * Converts this jaxb-friendly adapted eventContact object into the model's eventContact object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted event
     */
    public EventContacts toModelType() throws IllegalValueException {
        if (!EventContacts.isValidEventContactName(eventContact)) {
            throw new IllegalValueException(EventContacts.MESSAGE_EVENT_CONTACTS_CONSTRAINTS);
        }
        return new EventContacts(eventContact);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedEventContact)) {
            return false;
        }

        return eventContact.equals(((XmlAdaptedEventContact) other).eventContact);
    }
}
