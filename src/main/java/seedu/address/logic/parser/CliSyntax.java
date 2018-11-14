package seedu.address.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_DIST_NAME = new Prefix("dn/");
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_DIST_PHONE = new Prefix("dp/");
    public static final Prefix PREFIX_SERIAL_NR = new Prefix("s/");
    public static final Prefix PREFIX_DISTRIBUTOR = new Prefix("d/");
    public static final Prefix PREFIX_DIST_PROD = new Prefix("pr/");
    public static final Prefix PREFIX_PRODUCT_INFO = new Prefix("i/");
    public static final Prefix PREFIX_REMAINING_ITEMS = new Prefix("inv/");
    public static final Prefix PREFIX_TAG = new Prefix("t/");
    public static final Prefix PREFIX_PRODUCT = new Prefix("pr/");
    public static final Prefix PREFIX_USERNAME = new Prefix("u/");
    public static final Prefix PREFIX_PASSWORD = new Prefix("p/");
    public static final Prefix PREFIX_NEW_PASSWORD = new Prefix("newp/");
    public static final Prefix PREFIX_TIME = new Prefix("time/");
    public static final Prefix PREFIX_REMINDER_MESSAGE = new Prefix("message/");
    // TODO: To add transaction items with quantity
    // public static final Prefix PREFIX_QUANTITY = new Prefix("q/");
}
