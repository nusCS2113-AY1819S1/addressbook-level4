package seedu.planner.commons.core;

/**
 * Container for user visible messages.
 */
public class Messages {
    public static final String MESSAGE_UNKNOWN_COMMAND =
            "Unknown command.\n";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT =
            "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_RECORD_DISPLAYED_INDEX =
            "The record index provided is invalid.\n";
    public static final String MESSAGE_NONEXISTENT_RECORD_DISPLAYED_DATE =
            "The record date provided is non-existent.\n";
    public static final String MESSAGE_RECORDS_LISTED_OVERVIEW =
            "%1$d records listed!\n";
    public static final String MESSAGE_SUMMARY_MONEYFLOW_OVERVIEW =
            "Total money spent/received: %1$.2f.\n";
    public static final String MESSAGE_INVALID_DATE_REQUIRED =
            "Please enter exact TWO Dates, Start_Date and End_Date.\n";
    public static final String MESSAGE_INVALID_STARTDATE_ENDDATE =
            "Please enter the Start_Date smaller than or equal to the End_Date.\n";
    public static final String MESSAGE_EXCEL_FILE_WRITTEN_SUCCESSFULLY_NAME_PATH =
            "The Excel file named %1$s has been written successfully in path: %2$s.\n";
    public static final String MESSAGE_EXCEL_FILE_WRITTEN_SUCCESSFULLY =
            "The Excel file has been written successfully.\n";
    public static final String MESSAGE_UNREALISTIC_DIRECTORY =
            "Please choose existing directory.\n";
    public static final String MESSAGE_SET_DIRECTORY_SUCCESSFULLY =
            "The Directory path has been set to %1$s\n";
}
