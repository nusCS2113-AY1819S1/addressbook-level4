package seedu.divelog.logic.parser;
/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_DATE_START = new Prefix("ds/");
    public static final Prefix PREFIX_TIME_START = new Prefix("ts/");
    public static final Prefix PREFIX_DATE_END = new Prefix("de/");
    public static final Prefix PREFIX_TIME_END = new Prefix("te/");
    public static final Prefix PREFIX_SAFETY_STOP = new Prefix("ss/");
    public static final Prefix PREFIX_DEPTH = new Prefix("d/");
    public static final Prefix PREFIX_PRESSURE_GROUP_START = new Prefix("pg/");
    public static final Prefix PREFIX_PRESSURE_GROUP_END = new Prefix("pge/");
    public static final Prefix PREFIX_LOCATION = new Prefix("l/");
    public static final Prefix PREFIX_TIMEZONE = new Prefix("tz/");
}
