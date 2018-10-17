package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * Indicates that there is new information to be rendered.
 */
public class NewInfoMessageEvent extends BaseEvent {

    public final String message;

    public NewInfoMessageEvent(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
