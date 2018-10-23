package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

public class LogoutEvent extends BaseEvent {


    @Override
    public String toString () {
        return getClass().getSimpleName();
    }
}
