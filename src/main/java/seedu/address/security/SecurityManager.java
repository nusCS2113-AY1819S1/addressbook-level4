package seedu.address.security;

import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.events.security.SuccessfulLoginEvent;
import seedu.address.commons.events.security.UnsuccessfulLoginEvent;

/***
 *  Implements a Security authentication that identifies user
 */

public class SecurityManager extends ComponentManager implements Security {
    private boolean isAuthenticated;
    private String username;
    private String password;
    //Person userPerson;


    public SecurityManager(boolean isTest) {
        this.isAuthenticated = isTest; //Test for now
        this.username = "test";
        this.password = "test";
    }

    public boolean getAuthentication() {
        return this.isAuthenticated;
    }

    @Override
    public void login(String username, String password) {
        if (username.equals(this.username) && password.equals(this.password)) {
            this.isAuthenticated = true;
            //TODO Implement Person class that this is linked to:
            //userPerson = user1;
            System.out.println("Correct Password");
            raise(new SuccessfulLoginEvent());
        } else {
            //TODO Raise an exception to prompt user to type again
            System.out.println("Incorrect password");
            raise(new UnsuccessfulLoginEvent());
        }
    }
}
