package seedu.address.commons.events.logic;

import seedu.address.commons.events.BaseEvent;

/** Event that carries username and password for SecurityManager*/
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
