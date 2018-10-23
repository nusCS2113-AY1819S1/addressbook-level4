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
    public static final Prefix PREFIX_MATRIC = new Prefix("i/");

    public static final Prefix PREFIX_GRADEBOOK_ITEM = new Prefix("i/");
    public static final Prefix PREFIX_GRADEBOOK_MAXMARKS = new Prefix("mm/");
    public static final Prefix PREFIX_GRADEBOOK_WEIGHTAGE = new Prefix("w/");

    public static final Prefix PREFIX_NOTE_DATE = new Prefix("d/");




    /* TODO: Reorder the prefixes above in alphabetical order using the foll
    owing template */
    /* Class prefixes */
    public static final Prefix PREFIX_CLASSNAME = new Prefix("c/");
    public static final Prefix PREFIX_MAXENROLLMENT = new Prefix("e/");
    public static final Prefix PREFIX_MODULECODE = new Prefix("m/");
    /* Course prefixes */
    public static final Prefix PREFIX_COURSE_CODE = new Prefix("c/");
    public static final Prefix PREFIX_COURSE_NAME = new Prefix("n/");
    public static final Prefix PREFIX_COURSE_FACULTY = new Prefix("f/");
    /* Gradebook prefixes */
    /* Module prefixes */
    public static final Prefix PREFIX_MODULE_CODE = new Prefix("mc/");
    public static final Prefix PREFIX_MODULE_NAME = new Prefix("mn/");
    /* Note prefixes */
}
