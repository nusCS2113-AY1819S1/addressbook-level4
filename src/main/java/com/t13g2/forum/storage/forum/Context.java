package com.t13g2.forum.storage.forum;

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
}
