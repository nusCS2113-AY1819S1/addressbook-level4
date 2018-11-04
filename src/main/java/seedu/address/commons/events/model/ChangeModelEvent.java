package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;

/**
 * Indicate a different model will be used for new user
 */
public class ChangeModelEvent extends BaseEvent {
    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
