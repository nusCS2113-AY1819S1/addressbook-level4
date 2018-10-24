package seedu.address.logic.parser;

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

    public static final Prefix PREFIX_BODY = new Prefix("b/");
    public static final Prefix PREFIX_START = new Prefix("s/");
    public static final Prefix PREFIX_END = new Prefix("e/");
    public static final Prefix PREFIX_PRIORITY = new Prefix("p/");

    public static final Prefix PREFIX_CONTACT = new Prefix("c/");
    public static final Prefix PREFIX_TIME = new Prefix("t/");

    //@@author ChenSongJian
    public static final Prefix PREFIX_EXPENSE_CATEGORY = new Prefix("c/");
    public static final Prefix PREFIX_EXPENSE_DATE = new Prefix("d/");
    public static final Prefix PREFIX_EXPENSE_VALUE = new Prefix("v/");
    //@@author
}
