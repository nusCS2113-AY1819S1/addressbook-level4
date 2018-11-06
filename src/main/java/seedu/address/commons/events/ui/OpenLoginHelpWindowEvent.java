package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * Indicate open of help window
 */
public class OpenLoginHelpWindowEvent extends BaseEvent {
    @Override
    public String toString () {
        return getClass().getSimpleName();
    }

}
