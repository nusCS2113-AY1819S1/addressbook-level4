package seedu.address.security;

import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.events.security.LogoutEvent;
import seedu.address.commons.events.security.SuccessfulLoginEvent;
import seedu.address.commons.events.security.UnsuccessfulLoginEvent;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;

/***
 *  Implements a Security authentication that identifies user
 */

public class SecurityManager extends ComponentManager implements Security {
    private boolean isAuthenticated;
    private Model model;
    private String username;
    private String password;
    private Logic logic;

    public SecurityManager(boolean isTest, Model model, Logic logic) {
        this.isAuthenticated = isTest; //Test for now
        this.username = "test";
        this.password = "test";
        this.model = model;
        this.logic = logic;
    }

    public boolean getAuthentication() {
        return this.isAuthenticated;
    }

    @Override
    public void login(String username, String password) {
        if (username.equals(this.username) && password.equals(this.password)) {
            this.isAuthenticated = true;
            //Instantiates User in Model manager
            model.matchUserToPerson(username);
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

    @Override
    public RegisterFlag register(String username, String password, String email, String phone, String address) {
        try {
            logic.execute("add n/" + username + " e/" + email + " p/" + phone + " a/" + address);
            this.isAuthenticated = true;
            model.matchUserToPerson(username);
            return RegisterFlag.SUCCESS;
        } catch (CommandException e) {
            return RegisterFlag.USER_ALREADY_EXISTS;
        } catch (ParseException e) {
            return RegisterFlag.INCOMPLETE_FIELD;
        }
        //TODO Use password to create a database tgt with username
    }
}
