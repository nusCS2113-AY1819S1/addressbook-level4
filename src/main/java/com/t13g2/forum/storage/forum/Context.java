package com.t13g2.forum.storage.forum;

import com.t13g2.forum.model.forum.User;

public class Context {
    private static Context instance;

    private Context() {

    }

    public static Context getInstance() {
        if (instance == null) {
            instance = new Context();
        }
        return instance;
    }

    private User currentUser;

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }
}
