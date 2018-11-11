package seedu.address.commons.events.security;

import seedu.address.commons.events.BaseEvent;

/** Indicates the AddressBook in the model has changed*/
public class SendsAuthenticationStateEvent extends BaseEvent {

    private boolean isAuthenticated;

    public SendsAuthenticationStateEvent(boolean isAuthenticated) {
        this.isAuthenticated = isAuthenticated;
    }

    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    @Override
    public String toString() {
        return "Authentication state replied";
    }
}
