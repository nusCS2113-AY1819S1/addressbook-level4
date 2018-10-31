//@@Meowzz95
package com.t13g2.forum.model;

import com.t13g2.forum.model.forum.User;

/**
 *  A singleton class to manage login status
 */
public class Context {
    private static Context instance;
    private User currentUser;
    private int currentModuleId = -1;
    private int currentThreadId = -1;
    private int currentCommentId = -1;


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

    /**
     * returns current logged in user id
     *
     * @return user id, if not logged in -1
     */
    public int getCurrentUserId() {
        return currentUser == null ? -1 : currentUser.getId();
    }

    /**
     * checks login status
     * @return true if logged in otherwise false
     */
    public boolean isLoggedIn() {
        return currentUser != null;
    }

    /**
     * checks if current user a admin.
     * @return true if current user is admin otherwise false(including not logged in)
     */
    public boolean isCurrentUserAdmin() {
        return currentUser != null && currentUser.isAdmin();
    }

    /**
     * checks if current user is blocked.
     * @return true if current user is blocked or not logged in otherwise false
     */
    public boolean isCurrentUserBlocked() {
        return currentUser == null || currentUser.isBlock();
    }

    public int getCurrentModuleId() {
        return currentModuleId;
    }

    public void setCurrentModuleId(int currentModuleId) {
        this.currentModuleId = currentModuleId;
    }

    public int getCurrentThreadId() {
        return currentThreadId;
    }

    public void setCurrentThreadId(int currentThreadId) {
        this.currentThreadId = currentThreadId;
    }

    public int getCurrentCommentId() {
        return currentCommentId;
    }

    public void setCurrentCommentId(int currentCommentId) {
        this.currentCommentId = currentCommentId;
    }
}
