package seedu.address.logic.parser;

/**
 * Contains DrinkCommand Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_NAME = new Prefix("n/");


    /* Common prefix*/
    public static final Prefix PREFIX_DRINK_NAME = new Prefix ("n/");


    /* Manger prefix */
    public static final Prefix PREFIX_DRINK_DEFAULT_SELLING_PRICE = new Prefix("p/");
    public static final Prefix PREFIX_DRINK_COST_PRICE = new Prefix ("cp/");
    public static final Prefix PREFIX_DRINK_TAG = new Prefix ("t/");

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
