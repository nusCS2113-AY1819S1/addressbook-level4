package seedu.address.security;

import java.util.ArrayList;

import com.google.common.eventbus.Subscribe;

import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.events.security.LogoutEvent;
import seedu.address.commons.events.security.SuccessfulLoginEvent;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.User;

/***
 *  Implements a Security Module that handles authentication
 */

public class SecurityManager extends ComponentManager implements Security {
    private boolean isAuthenticated;
    private String username;
    private String password;
    private Logic logic;
    private AppUsers appUsers;
    private ArrayList<AccountCredential> userlist;
    private boolean incorrectPassWord = false;

    public SecurityManager(boolean isTest, Logic logic, AppUsers appUsers) {
        this.isAuthenticated = isTest; //Test for now
        this.username = "test";
        this.password = "test";
        this.logic = logic;
        this.appUsers = appUsers;

    }

    public boolean getAuthentication() {
        return this.isAuthenticated;
    }

    @Override
    public void login(String username, String password) {
        userlist = appUsers.getAccountCredentials();
        for (AccountCredential acc : userlist) {
            if (username.equals(acc.getUserName())) {
                if (acc.passwordIsValid(password)) {
                    this.isAuthenticated = true;
                    logic.matchUserToPerson(username);
                    raise(new SuccessfulLoginEvent());
                } else {
                    //THROW INCORRECT PASSWORD EXCEPTION
                    incorrectPassWord = true;
                }
            }
            //When username is not in the list
            if (!incorrectPassWord && !this.isAuthenticated) {
                //THROW USER NOT FOUND

            }
        }
    }

    @Override
    public void logout() {
        this.isAuthenticated = false;
        //Clears instance of User in model to prevent anyone accessing previously logged in user
        logic.clearUser();
    }

    @Override
    public RegisterFlag register(String username, String password, String email, String phone, String address) {
        try {
            logic.execute("add n/" + username + " e/" + email + " p/" + phone + " a/" + address);
            this.isAuthenticated = true;
            logic.matchUserToPerson(username);
            userlist = appUsers.getAccountCredentials();
            userlist.add(new AccountCredential(username, password));
            appUsers.updateAccountCredentials(userlist);
            return RegisterFlag.SUCCESS;
        } catch (CommandException e) {
            return RegisterFlag.USER_ALREADY_EXISTS;
        } catch (ParseException e) {
            return RegisterFlag.INCOMPLETE_FIELD;
        }
        //TODO Use password to create a database tgt with username
    }

    @Override
    public User getUser() {
        return logic.getUser();
    }

    @Subscribe
    public void handleCommandLogoutEvent(LogoutEvent logout) {
        logout();
    }
}
