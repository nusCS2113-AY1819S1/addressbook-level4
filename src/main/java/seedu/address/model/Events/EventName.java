package seedu.address.model.Events;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Present the eventName in the event.
 */
public class EventName {
    public static final String NAME_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";
    public static final String MESSAGE_EVENTNAME_CONSTRAINTS =
            "Name should only contain alphanumeric characters and spaces, and it should not be blank";
    public final String ThisName;
    public EventName(String name) {
        requireNonNull(name);
        checkArgument(checkValid(name), MESSAGE_EVENTNAME_CONSTRAINTS);
        ThisName = name;
    }
    public static boolean checkValid(String name) {
        return name.matches(NAME_VALIDATION_REGEX);
    }

    public String toString() {
        return ThisName;
    }

    /**
     * To check whether two eventName equals.
     */
    public boolean equals(EventName other) {
        return other == this
                || ThisName.equals(other.ThisName);
    }
}
