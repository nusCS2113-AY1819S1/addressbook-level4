package seedu.address.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_TASK = new Prefix("t/");
    public static final Prefix PREFIX_MODULE = new Prefix("m/");
    public static final Prefix PREFIX_DATE = new Prefix("d/");
    public static final Prefix PREFIX_PRIORITY = new Prefix("p/");
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_PHONE = new Prefix("p/");
    public static final Prefix PREFIX_EMAIL = new Prefix("e/");
    public static final Prefix PREFIX_ADDRESS = new Prefix("a/");
    public static final Prefix PREFIX_TAG = new Prefix("t/");

    /* Expenditure prefix definition */
    public static final Prefix PREFIX_DESCRIPTION = new Prefix("e/");
    public static final Prefix PREFIX_MONEY = new Prefix("m/");
    public static final Prefix PREFIX_CATEGORY = new Prefix("c/");
<<<<<<< HEAD
    public static final Prefix PREFIX_START = new Prefix("s/");
    public static final Prefix PREFIX_END = new Prefix("n/");
=======
    public static final Prefix PREFIX_PERIOD = new Prefix("numofdays/");
>>>>>>> 0ed200d76e739d353a5980aed52fa6a600cd442f

}
