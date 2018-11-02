package seedu.address.model.Events;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

public class EventName {
    public final String ThisName;
    public static final String NAME_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";
    public static final String MESSAGE_EVENTNAME_CONSTRAINTS =
            "Names should only contain alphanumeric characters and spaces, and it should not be blank";
    public EventName(String name){
        requireNonNull(name);
        checkArgument(CheckValid(name), MESSAGE_EVENTNAME_CONSTRAINTS);
        ThisName= name;
    }
    public static boolean CheckValid(String name){
        return name.matches(NAME_VALIDATION_REGEX);
    }

    public String toString() {
        return ThisName;
    }
    public boolean equals(EventName other) {
        return other == this
                || ThisName.equals(other.ThisName);
    }
}
