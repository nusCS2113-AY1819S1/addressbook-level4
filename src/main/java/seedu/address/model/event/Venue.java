package seedu.address.model.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents an Event's venue in the event manager.
 * Guarantees: immutable; is valid as declared in {@link #isValidVenue(String)}
 */
public class Venue {

    public static final String MESSAGE_VENUE_CONSTRAINTS =
            "Venues can take any values, and it should not be blank";

    /*
     * The first character of the venue must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VENUE_VALIDATION_REGEX = "[^\\s].*";

    public final String value;

    /**
     * Constructs an {@code Venue}.
     *
     * @param venue A valid venue.
     */
    public Venue(String venue) {
        requireNonNull(venue);
        checkArgument(isValidVenue(venue), MESSAGE_VENUE_CONSTRAINTS);
        value = venue;
    }

    /**
     * Returns true if a given string is a valid location.
     */
    public static boolean isValidVenue(String test) {
        return test.matches(VENUE_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Venue // instanceof handles nulls
                && value.equals(((Venue) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
