package seedu.address.commons.events.logic;

import seedu.address.commons.events.BaseEvent;

/** Logins event that contains username and password for Security Manager*/
public class LoginEvent extends BaseEvent {

    private String username;
    private String password;

    public LoginEvent(String username, String password) {
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
        return "Login Event";
    }
}
