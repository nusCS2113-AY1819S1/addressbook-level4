package com.t13g2.forum.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_PHONE = new Prefix("p/");
    public static final Prefix PREFIX_EMAIL = new Prefix("e/");
    public static final Prefix PREFIX_ADDRESS = new Prefix("a/");
    public static final Prefix PREFIX_TAG = new Prefix("t/");
    public static final Prefix PREFIX_COMMENT = new Prefix("c/");
    public static final Prefix PREFIX_INDEX = new Prefix("i/");
    public static final Prefix PREFIX_MODULE = new Prefix("m/");
    public static final Prefix PREFIX_THREAD = new Prefix("th/");
    /* Prefix for Announcement */
    public static final Prefix PREFIX_ANNOUNCE_TITLE = new Prefix("aTitle/");
    public static final Prefix PREFIX_ANNOUNCE_CONTENT = new Prefix("aContent/");
    /* Prefix for Thread */
    public static final Prefix PREFIX_THREAD_ID = new Prefix("tId/");
    public static final Prefix PREFIX_THREAD_TITLE = new Prefix("tTitle/");
    /* Prefix for User */
    public static final Prefix PREFIX_USER_ID = new Prefix("uID/");
    public static final Prefix PREFIX_USER_NAME = new Prefix("uName/");
    public static final Prefix PREFIX_USER_PASSWORD = new Prefix("uPass/");
    public static final Prefix PREFIX_USER_EMAIL = new Prefix("uEmail/");
    public static final Prefix PREFIX_USER_PHONE = new Prefix("uPhone/");
    public static final Prefix PREFIX_USER_IS_ADMIN = new Prefix("uAdmin/");
    /* Prefix for Module */
    public static final Prefix PREFIX_MODULE_TITLE = new Prefix("mTitle/");
    public static final Prefix PREFIX_MODULE_CODE = new Prefix("mCode/");
    /* Prefix for Comment */
    public static final Prefix PREFIX_COMMENT_CONTENT = new Prefix("cContent/");

}
