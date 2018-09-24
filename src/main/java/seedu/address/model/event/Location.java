package seedu.address.model.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

public class Location {
    public static final String MESSAGE_LOCATION_CONSTRAINTS =
            "Event location should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String LOCATION_VALIDATION_REGEX = "[^\\s].*";

    public final String value;

    /**
     * Constructs an {@code Address}.
     *
     * @param location A valid address.
     */
    public Location(String location) {
        requireNonNull(location);
        checkArgument(isValidDescription(location), MESSAGE_LOCATION_CONSTRAINTS);
        value = location;
    }

    /**
     * Returns true if a given string is a valid description.
     * Will implement this at a later date
     */
    public static boolean isValidDescription(String test) {
        return true;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Location// instanceof handles nulls
                && value.equals(((Location) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }


}
