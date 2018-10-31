package seedu.address.commons.events.security;

import seedu.address.commons.events.BaseEvent;

/***
 * Event for logout
 */
public class LogoutEvent extends BaseEvent {
    @Override
    public String toString() {
        return "User Logged Out";
    }
}
