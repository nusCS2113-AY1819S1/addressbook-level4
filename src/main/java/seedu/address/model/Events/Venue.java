package seedu.address.model.Events;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

public class Venue {
    public final String ThisVenue;
    public static final String VENUE_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";
    public static final String MESSAGE_VENUE_CONSTRAINT =
            "Venue should only contain alphanumeric characters and spaces, and it should not be blank";
    public Venue(String venue){
        requireNonNull(venue);
        checkArgument(CheckValid(venue), MESSAGE_VENUE_CONSTRAINT);
        ThisVenue= venue;
    }


    public static boolean CheckValid(String name){
        return name.matches(VENUE_VALIDATION_REGEX);
    }

    public String toString() {
        return ThisVenue;
    }

}
