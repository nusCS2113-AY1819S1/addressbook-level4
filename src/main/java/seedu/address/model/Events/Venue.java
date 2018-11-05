package seedu.address.model.Events;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Present the Venue of the event.
 */
public class Venue {
    public static final String VENUE_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";
    public static final String MESSAGE_VENUE_CONSTRAINT =
            "Venue should only contain alphanumeric characters and spaces, and it should not be blank";
    public final String ThisVenue;
    public Venue(String venue) {
        requireNonNull(venue);
        checkArgument(checkValid(venue), MESSAGE_VENUE_CONSTRAINT);
        ThisVenue = venue;
    }


    public static boolean checkValid(String name) {
        return name.matches(VENUE_VALIDATION_REGEX);
    }

    public String toString() {
        return ThisVenue;
    }

}
