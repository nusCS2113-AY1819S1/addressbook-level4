//@@Meowzz95
package com.t13g2.forum.model;

import com.t13g2.forum.commons.exceptions.NotLoggedInException;
import com.t13g2.forum.model.forum.User;

/**
 *
 */
public class Context {
    private static Context instance;
    private User currentUser;


    private Context() {

    }

    public static Context getInstance() {
        if (instance == null) {
            instance = new Context();
        }
        return instance;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public int getCurrentUserId() throws NotLoggedInException {
        ensureLoggedIn();
        return currentUser.getId();
    }

    public boolean isLoggedIn() {
        return currentUser != null;
    }

    /**
     * check if current user a admin.
     */
    public boolean isCurrentUserAdmin() throws NotLoggedInException {
        ensureLoggedIn();
        return currentUser.isAdmin();
    }

    /**
     * check if current user is blocked.
     */
    public boolean isCurrentUserBlocked() throws NotLoggedInException {
        ensureLoggedIn();
        return currentUser.isBlock();
    }

    /**
     * ensure that a user is logged in.
     */
    private void ensureLoggedIn() throws NotLoggedInException {
        if (!isLoggedIn()) {
            throw new NotLoggedInException();

        }
    }
}
