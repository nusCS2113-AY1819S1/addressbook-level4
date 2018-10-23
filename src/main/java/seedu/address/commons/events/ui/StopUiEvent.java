package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * Indicate a stop of UI for the main app
 */
public class StopUiEvent extends BaseEvent {

    @Override
    public String toString () {
        return getClass().getSimpleName();
    }
}
