package seedu.address.model.event;

/**
 * Represents a Event's end time in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidTime(String)}
 */
public class EndTime extends Time {

    public static final String MESSAGE_INVALID_END_TIME =
            "End time should not be earlier than start time.";

    /**
     * Constructs a {@code Name}.
     *
     * @param time A valid time.
     */
    public EndTime(String time) {
        super(time);
    }


}
