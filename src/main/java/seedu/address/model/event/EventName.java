package seedu.address.model.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Event's name in the address book.
 * Guarantees: immutable;
 */
public class EventName {

    public static final String MESSAGE_EVENT_CONSTRAINTS =
            "Event should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the event name must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String NAME_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String fullName;

    /**
     * Constructs a {@code Name}.
     *
     * @param name A valid name.
     */
    public EventName(String name) {
        requireNonNull(name);
        checkArgument(isValidName(name), MESSAGE_EVENT_CONSTRAINTS);
        fullName = name;
    }

    /**
     * Returns true if a given string is a valid event name.
     */
    public static boolean isValidName(String test) {
        return test.matches(NAME_VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return fullName;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EventName // instanceof handles nulls
                && fullName.equals(((EventName) other).fullName)); // state check
    }

    @Override
    public int hashCode() {
        return fullName.hashCode();
    }

}
