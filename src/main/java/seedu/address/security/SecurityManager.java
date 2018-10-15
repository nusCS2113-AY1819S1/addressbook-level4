package seedu.address.security;

import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.events.security.LogoutEvent;
import seedu.address.commons.events.security.SuccessfulLoginEvent;
import seedu.address.commons.events.security.UnsuccessfulLoginEvent;
import seedu.address.model.Model;

/***
 *  Implements a Security authentication that identifies user
 */

public class SecurityManager extends ComponentManager implements Security {
    private boolean isAuthenticated;
    private Model model;
    private String username;
    private String password;
    private User user;

    public SecurityManager(boolean isTest, Model model) {
        this.isAuthenticated = isTest; //Test for now
        this.username = "test";
        this.password = "test";
        this.model = model;
    }

    public boolean getAuthentication() {
        return this.isAuthenticated;
    }

    @Override
    public void login(String username, String password) {
        if (username.equals(this.username) && password.equals(this.password)) {
            this.isAuthenticated = true;
            //Links User to Security Manager
            this.user = new User(username, model);
            //TODO Implement logger
            //System.out.println("Correct Password");
            raise(new SuccessfulLoginEvent());
        } else {
            //TODO Raise an exception to prompt user to type again
            //TODO implement logger
            //System.out.println("Incorrect password");
            raise(new UnsuccessfulLoginEvent());
        }
    }

    @Override
    public void logout() {
        this.isAuthenticated = false;
        //TODO Do I clear the User since its logged out? I can just leave it there to be overwritten
        raise(new LogoutEvent());
    }
}
