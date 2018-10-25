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

    /* Common prefix*/
    public static final Prefix PREFIX_DRINK_ITEM = new Prefix ("n/");
    /* Manger prefix */
    public static final Prefix PREFIX_DEFAULT_SELLING_PRICE = new Prefix("p/");
    /* Stock taker prefix*/
    public static final Prefix PREFIX_DATE = new Prefix("d/");
    public static final Prefix PREFIX_QUANTITY = new Prefix("q/");
    public static final Prefix PREFIX_PRICE = new Prefix("p/");

    /* Prefix definition for change password and create account*/

    public static final Prefix PREFIX_USERNAME = new Prefix("u/");
    public static final Prefix PREFIX_PASSWORD = new Prefix("p/");
    public static final Prefix PREFIX_AUTHENTICATION_LEVEL = new Prefix("a/");
    public static final Prefix PREFIX_OLD_PASSWORD = new Prefix("o/");
    public static final Prefix PREFIX_NEW_PASSWORD = new Prefix("n/");

}
