package seedu.address.commons.core;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX = "The person index provided is invalid";
    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW = "%1$d persons listed!";

    //messages thrown by {@code ParserUtil#parseImportExportFileName}
    public static final String MESSAGE_PATH_TOO_LONG = "Destination file path is too long, consider making your "
            + "FILENAME shorter, or moving your application to a shorter directory, such as C:\\files\\";
    public static final String MESSAGE_INVALID_PATH = "The path of the file was invalid.";
}
