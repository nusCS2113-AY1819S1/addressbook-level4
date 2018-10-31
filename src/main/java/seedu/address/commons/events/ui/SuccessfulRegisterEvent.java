package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * An event requesting to view the help page.
 */
public class SuccessfulRegisterEvent extends BaseEvent {

    @Override
    public String toString() {
        return "Successfully Registered New User";
    }

}
