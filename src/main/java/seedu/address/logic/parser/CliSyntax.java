package seedu.address.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions for Person */
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_PHONE = new Prefix("p/");
    public static final Prefix PREFIX_EMAIL = new Prefix("e/");
    public static final Prefix PREFIX_ADDRESS = new Prefix("a/");
    public static final Prefix PREFIX_TAG = new Prefix("t/");
    public static final Prefix PREFIX_MEDICAL_REPORT = new Prefix("mr/");
    public static final Prefix PREFIX_MEDICAL_REPORT_INFO = new Prefix("mr/");
    public static final Prefix PREFIX_TITLE = new Prefix("t/");
    public static final Prefix PREFIX_DATE = new Prefix("d/");

    /* Prefix definitions for MedHistory */
    public static final Prefix PREFIX_HISTORY = new Prefix("hs/");
    public static final Prefix PREFIX_BLOODTYPE = new Prefix("b/");

    /* Prefix definitions for Additional Information */
    public static final Prefix PREFIX_ADD_INFO_NRIC = new Prefix("i/");
    public static final Prefix PREFIX_ADD_INFO_DOB = new Prefix("d/");
    public static final Prefix PREFIX_ADD_INFO_HEIGHT = new Prefix("h/");
    public static final Prefix PREFIX_ADD_INFO_WEIGHT = new Prefix("w/");
    public static final Prefix PREFIX_ADD_INFO_GENDER = new Prefix("g/");
    public static final Prefix PREFIX_ADD_INFO_OCCUPATION = new Prefix("/o");
    public static final Prefix PREFIX_ADD_INFO_MARITAL = new Prefix("/m");
    public static final Prefix PREFIX_ADD_INFO_FAMILY = new Prefix("/f");

    /* Prefix definitions for Appt */
    public static final Prefix PREFIX_APPT_START = new Prefix("s/");
    public static final Prefix PREFIX_APPT_END = new Prefix("e/");
    public static final Prefix PREFIX_APPT_VENUE = new Prefix("v/");
    public static final Prefix PREFIX_APPT_INFO = new Prefix("i/");
    public static final Prefix PREFIX_APPT_DRNAME = new Prefix("d/");

}
