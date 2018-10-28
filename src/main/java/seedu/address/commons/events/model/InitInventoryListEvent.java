package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;

/**
 * indicate that user has login and start to init the main app
 */
public class InitInventoryListEvent extends BaseEvent {

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

}
