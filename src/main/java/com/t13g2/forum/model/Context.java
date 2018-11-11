//@@author Meowzz95
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


    /**
     * Creates a new Context instance.
     */
    private Context() {

    }

    public static Context getInstance() {
        if (instance == null) {
            instance = new Context();
        }
        return instance;
    }

    /**
     * Returns current logged in user
     *
     * @return current logged in user
     */
    public User getCurrentUser() {
        return currentUser;
    }

    /**
     * Sets current logged in user
     *
     * @param currentUser
     */
    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    /**
     * Returns current logged in user id
     *
     * @return user id, if not logged in -1
     */
    public int getCurrentUserId() {
        return currentUser == null ? -1 : currentUser.getId();
    }

    /**
     * Checks login status
     * @return true if logged in otherwise false
     */
    public boolean isLoggedIn() {
        return currentUser != null;
    }

    /**
     * Checks if current user a admin.
     * @return true if current user is admin otherwise false(including not logged in)
     */
    public boolean isCurrentUserAdmin() {
        return currentUser != null && currentUser.isAdmin();
    }

    /**
     * Checks if current user is blocked.
     * @return true if current user is blocked or not logged in otherwise false
     */
    public boolean isCurrentUserBlocked() {
        return currentUser == null || currentUser.isBlock();
    }

    /**
     * Gets the module id that current user is browsing
     * @return module id
     */
    public int getCurrentModuleId() {
        return currentModuleId;
    }

    /**
     * Sets the module id that current user is browsing
     * @param currentModuleId
     */
    public void setCurrentModuleId(int currentModuleId) {
        this.currentModuleId = currentModuleId;
    }

    /**
     * Gets the thread id that current user is browsing
     * @return thread id
     */
    public int getCurrentThreadId() {
        return currentThreadId;
    }

    /**
     * Sets the thread id that current user is browsing
     * @param currentThreadId
     */
    public void setCurrentThreadId(int currentThreadId) {
        this.currentThreadId = currentThreadId;
    }

    /**
     * Gets the comment id that current user is browsing
     * @return comment id
     */
    public int getCurrentCommentId() {
        return currentCommentId;
    }

    /**
     * Sets the comment id that current user is browsing
     * @param currentCommentId
     */
    public void setCurrentCommentId(int currentCommentId) {
        this.currentCommentId = currentCommentId;
    }
}
