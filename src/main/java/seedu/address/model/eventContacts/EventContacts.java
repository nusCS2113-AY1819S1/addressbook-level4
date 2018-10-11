package seedu.address.model.eventContacts;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents an event contact in the student planner.
 * Guarantees: immutable;
 */
public class EventContacts {

    public static final String MESSAGE_EVENT_CONTACTS_CONSTRAINTS = "Event Contacts should contain only "
            + "letters and spaces";
    public static final String CONTACT_VALIDATION_REGEX = "[a-z|A-Z|\\s]+";

    public final String eventContactName;

    /**
     * Constructs a {@code Event Contact}.
     *
     * @param eventContactName A valid event contact.
     */
    public EventContacts(String eventContactName) {
        requireNonNull(eventContactName);
        checkArgument(isValidEventContactName(eventContactName), MESSAGE_EVENT_CONTACTS_CONSTRAINTS);
        this.eventContactName = eventContactName;
    }

    /**
     * Returns true if a given string is a valid contact.
     */
    public static boolean isValidEventContactName(String test) {
        return test.matches(CONTACT_VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EventContacts // instanceof handles nulls
                && eventContactName.equals(((EventContacts) other).eventContactName)); // state check
    }

    @Override
    public int hashCode() {
        return eventContactName.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return '[' + eventContactName + ']';
    }

}
