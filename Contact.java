package seedu.address.model.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Event's contact in the event manager.
 * Guarantees: immutable; is valid as declared in {@link #isValidContact(String)}
 */
public class Contact {

    public static final String MESSAGE_CONTACT_CONSTRAINTS =
            "Contact names should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the contact must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String CONTACT_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String fullContactName;

    /**
     * Constructs a {@code Contact}.
     *
     * @param contact A valid contact.
     */
    public Contact(String contact) {
        requireNonNull(contact);
        checkArgument(isValidContact(contact), MESSAGE_CONTACT_CONSTRAINTS);
        fullContactName = contact;
    }

    /**
     * Returns true if a given string is a valid contact.
     */
    public static boolean isValidContact(String test) {
        return test.matches(CONTACT_VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return fullContactName;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Contact // instanceof handles nulls
                && fullContactName.equals(((Contact) other).fullContactName)); // state check
    }

    @Override
    public int hashCode() {
        return fullContactName.hashCode();
    }

}
