package seedu.address.commons.events.security;

import seedu.address.commons.events.BaseEvent;

/***
 * Indicates a unsuccessful login attempt
 */
public class UnsuccessfulLoginEvent extends BaseEvent {

    @Override
    public String toString() {
        return "Login Failure";
    }
}
