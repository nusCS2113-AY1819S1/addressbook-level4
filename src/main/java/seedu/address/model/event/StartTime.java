package seedu.address.model.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Event's start time in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidTime(String)}
 */
public class StartTime extends Time {

    /**
     * Constructs a {@code StartTime}.
     *
     * @param time A valid time.
     */
    public StartTime(String time) {
        super(time);
    }

}
