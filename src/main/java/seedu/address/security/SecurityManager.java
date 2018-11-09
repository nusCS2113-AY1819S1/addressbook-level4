package seedu.address.security;

import java.util.ArrayList;

import com.google.common.eventbus.Subscribe;

import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.events.logic.LoginEvent;
import seedu.address.commons.events.logic.RegisterSuccessEvent;
import seedu.address.commons.events.security.GetAuthenticationEvent;
import seedu.address.commons.events.security.GetAuthenticationReplyEvent;
import seedu.address.commons.events.security.LogoutEvent;
import seedu.address.commons.events.security.SuccessfulLoginEvent;
import seedu.address.commons.events.ui.NewResultAvailableEvent;
import seedu.address.commons.events.ui.SuccessfulRegisterEvent;
import seedu.address.logic.Logic;
import seedu.address.model.User;

/***
 *  Implements a Security Module that handles authentication
 */

public class SecurityManager extends ComponentManager implements Security {
    private boolean isAuthenticated;
    private Logic logic;
    private AppUsers appUsers;
    private ArrayList<AccountCredential> userlist;
    private boolean incorrectPassWord = false;

    public SecurityManager(boolean isTest, Logic logic, AppUsers appUsers) {
        this.isAuthenticated = isTest; //Test for now
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
                    raise(new NewResultAvailableEvent("Incorrect Password"));
                }
            }
        }
        //When username is not in the list
        if (!incorrectPassWord && !this.isAuthenticated) {
            //THROW USER NOT FOUND
            raise(new NewResultAvailableEvent("Username not found"));
        }
        incorrectPassWord = false;
    }

    @Override
    public void logout() {
        this.isAuthenticated = false;
        //Clears instance of User in model to prevent anyone accessing previously logged in user
        logic.clearUser();
    }

    @Override
    public RegisterFlag register(String username, String password) {
        this.isAuthenticated = true;
        logic.matchUserToPerson(username);
        userlist = appUsers.getAccountCredentials();
        userlist.add(new AccountCredential(username, password));
        appUsers.updateAccountCredentials(userlist);
        return RegisterFlag.SUCCESS;
    }

    @Override
    public User getUser() {
        return logic.getUser();
    }

    @Subscribe
    public void handleCommandLogoutEvent(LogoutEvent logout) {
        logout();
    }

    @Subscribe
    public void handleLoginAttemptEvent(LoginEvent e) {
        login(e.getUsername(), e.getPassword());
    }

    @Subscribe
    public void handleRegisterSuccessEvent(RegisterSuccessEvent e) {
        switch (register(e.getUsername(), e.getPassword())) {
        case SUCCESS:
            raise(new SuccessfulRegisterEvent());
            break;
        default:
            break;

        }
    }

    @Subscribe
    public void handleGetAuthenticationEvent(GetAuthenticationEvent e) {
        raise(new GetAuthenticationReplyEvent(isAuthenticated));
    }
}
