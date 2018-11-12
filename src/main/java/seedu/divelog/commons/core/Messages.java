package seedu.divelog.commons.core;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_DIVE_DISPLAYED_INDEX = "The index provided is invalid!\n"
            + "It should be based on the numbers you see in the side panel.";
    public static final String MESSAGE_DIVE_LISTED_OVERVIEW = "%1$d dives listed!";
    public static final String MESSAGE_INVALID_TIME_FORMAT = "Invalid time format! (Not 24Hr format) ";
    public static final String MESSAGE_INVALID_DATE_FORMAT = "Invalid date format! (Not DDMMYYYY) ";
    public static final String MESSAGE_INVALID_DATE_LIMITS = "Start and End date or time or Safety Stop Time"
            + " are not in chronological order!\n"
            + "Start Date should be earlier than End Date!\n"
            + "Safety Stop should be between Start and End time";
    public static final String MESSAGE_INVALID_TIMEZONE_FORMAT = "Invalid timezone format! (UTC format)";
    public static final String MESSAGE_ERROR_LIMIT_EXCEED = "Dive is too deep and too long!!";
    public static final String MESSAGE_ERROR_DIVES_OVERLAP =
            "The dive overlaps with another dive. Not updating the divelog.";
    public static final String MESSAGE_MAX_DEPTH_EXCEEDED = "The deepest you can go is 42m - don't risk your life!";
}
