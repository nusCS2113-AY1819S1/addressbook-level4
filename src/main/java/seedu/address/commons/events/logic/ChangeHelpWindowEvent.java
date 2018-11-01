package seedu.address.commons.events.logic;

import seedu.address.commons.events.BaseEvent;

/**
 * Change in logic for logic related component
 */
public class ChangeHelpWindowEvent extends BaseEvent {


    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
