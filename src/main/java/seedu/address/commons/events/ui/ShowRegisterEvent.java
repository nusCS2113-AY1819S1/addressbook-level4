package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * An event requesting to view the help page.
 */
public class ShowRegisterEvent extends BaseEvent {

    @Override
    public String toString() {
        return "Register Window Shown";
    }

}
