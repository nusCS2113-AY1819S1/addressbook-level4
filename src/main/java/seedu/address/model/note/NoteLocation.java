package seedu.address.model.note;

import static java.util.Objects.requireNonNull;

/**
 * Represents a location in a note.
 */
public class NoteLocation {

    public static final int LOCATION_MAX_CHAR_COUNT = 80;

    public static final String MESSAGE_LOCATION_EXCEED_MAX_CHAR_COUNT =
            "Location is too long! Please keep the character count to "
            + Integer.toString(LOCATION_MAX_CHAR_COUNT) + " and below.";

    private final String location;

    public NoteLocation(String location) {
        requireNonNull(location);
        this.location = location;
    }

    public static boolean isValidLocation(String location) {
        return (location.length() <= LOCATION_MAX_CHAR_COUNT);
    }

    @Override
    public String toString() {
        return this.location;
    }
}
