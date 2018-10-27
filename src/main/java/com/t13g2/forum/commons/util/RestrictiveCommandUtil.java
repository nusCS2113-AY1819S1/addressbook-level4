package com.t13g2.forum.commons.util;

import com.t13g2.forum.logic.commands.exceptions.CommandException;
import com.t13g2.forum.model.Context;

/**
 * A Utility class to restrict commands
 */
public class RestrictiveCommandUtil {

    public static final String MESSAGE_NOT_LOGIN = "Sorry! You have to login to proceed.";
    public static final String MESSAGE_BLOCKED_USER = "You have been blocked for creating new thread and comment!";
    public static final String MESSAGE_NOT_THREAD_OWNER = "Sorry! You are not the owner of this thread.";
    public static final String MESSAGE_NOT_COMMENT_OWNER = "Sorry! You are not the owner of this comment.";

    /**
     * check for login status
     * @throws CommandException
     */
    public static boolean checkLogin() throws CommandException {
        if (!Context.getInstance().isLoggedIn()) {
            return false;
        }
        return true;
    }
}
