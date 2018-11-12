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
    public static final Prefix PREFIX_SKILL = new Prefix("s/");
    public static final Prefix PREFIX_SKILLLEVEL = new Prefix("l/");
    public static final Prefix PREFIX_CLUB_NAME = new Prefix("c/");
    public static final Prefix PREFIX_EXPECTED_TURNOUT = new Prefix("t/");
    public static final Prefix PREFIX_NUMBER_OF_EVENTS = new Prefix("e/");
    public static final Prefix PREFIX_TOTAL_BUDGET = new Prefix("b/");
    public static final Prefix PREFIX_SORT = new Prefix("st/");
}
