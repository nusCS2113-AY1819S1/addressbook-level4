package seedu.address.commons.events.logic;

import seedu.address.commons.events.BaseEvent;

/** Indicates the AddressBook in the model has changed*/
public class RegisterSuccessEvent extends BaseEvent {

    private String username;
    private String password;

    public RegisterSuccessEvent(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "Register Success";
    }
}
