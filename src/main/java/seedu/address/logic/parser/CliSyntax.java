package seedu.address.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_PHONE = new Prefix("s/");  /* serie number*/
    public static final Prefix PREFIX_EMAIL = new Prefix("d/");     /* serie number*/
    public static final Prefix PREFIX_ADDRESS = new Prefix("i/");   /*product info*/
    public static final Prefix PREFIX_TAG = new Prefix("t/");

}
