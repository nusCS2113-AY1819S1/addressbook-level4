package com.t13g2.forum.commons.core;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX = "The person index provided is invalid";
    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW = "%1$d persons listed!";

    public static final String MESSAGE_NOT_LOGIN = "Sorry! You have to login to proceed.";
    public static final String MESSAGE_BLOCKED_USER =
            "You have been blocked for creating and updating thread and comment!";
    public static final String MESSAGE_NOT_THREAD_OWNER = "Sorry! You are not the owner of this thread.";
    public static final String MESSAGE_NOT_COMMENT_OWNER = "Sorry! You are not the owner of this comment.";

    public static final String MESSAGE_INVALID_MODULE_CODE = "Module Code does not exist in the forum book!";
    public static final String MESSAGE_INVALID_THREAD_ID = "Thread ID does not exist in the forum book!";
    public static final String MESSAGE_INVALID_COMMENT_ID = "Comment ID does not exist in the forum book!";

    public static final String MESSAGE_INVALID_THREAD =
            "Sorry! The entity you requested is not under the current scope."
            + "\nPlease go to the corresponding thread list.";
    public static final String MESSAGE_INVALID_COMMENT =
            "Sorry! The entity you requested is not under the current scope."
            + "\nPlease go to rhe corresponding comment list.";
}
