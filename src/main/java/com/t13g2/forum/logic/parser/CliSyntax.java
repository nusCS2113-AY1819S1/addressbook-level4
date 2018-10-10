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

    /* Prefix for announcement */
    public static final Prefix PREFIX_ANNOUNCE_TITLE = new Prefix("at/");
    public static final Prefix PREFIX_ANNOUNCE_CONTENT = new Prefix("ac/");

    /* */
    public static final Prefix PREFIX_USER_NAME = new Prefix("uname/");
}
