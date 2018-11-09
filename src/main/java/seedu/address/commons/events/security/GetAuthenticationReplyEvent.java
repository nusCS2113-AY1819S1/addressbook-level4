package seedu.address.commons.events.security;

import seedu.address.commons.events.BaseEvent;

/** Indicates the AddressBook in the model has changed*/
public class GetAuthenticationReplyEvent extends BaseEvent {

    private boolean isAuthenticated;

    public GetAuthenticationReplyEvent(boolean isAuthenticated) {
        this.isAuthenticated = isAuthenticated;
    }

    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    @Override
    public String toString() {
        return "Register Success";
    }
}
