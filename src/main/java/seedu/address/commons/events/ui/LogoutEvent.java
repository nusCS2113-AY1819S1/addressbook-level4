package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * Indicates a logout event
 */
public class LogoutEvent extends BaseEvent {


    @Override
    public String toString () {
        return getClass().getSimpleName();
    }
}
