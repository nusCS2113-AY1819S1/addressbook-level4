package seedu.recruit.commons.events.logic;

import seedu.recruit.commons.events.BaseEvent;

/** Indicates user has been authenticated*/

public class UserAuthenticatedEvent extends BaseEvent {
    public UserAuthenticatedEvent () {

    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }


}
