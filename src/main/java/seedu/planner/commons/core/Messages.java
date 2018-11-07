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

    public static final String MESSAGE_INVALID_DATE_REQUIRED =
            "Please enter exact TWO Dates, Start_Date and End_Date.\n";

    public static final String MESSAGE_INVALID_STARTDATE_ENDDATE =
            "Please enter the Start_Date smaller than or equal to the End_Date.\n";

    public static final String MESSAGE_UNREALISTIC_DIRECTORY =
            "Please choose existing directory/file path.\n";

    public static final String MESSAGE_INVALID_ENTRY_EXCEL_FILE =
            "The cells for Name, Date, Money Received/Spent, Tags should be in correct order."
                    + " The Cell should only be String or Numeric type."
                    + " The first row of your table should come with 4 columns, namely, "
                    + "NAME, DATE,  MONEY SPENT/RECEIVED and TAGS (case in-sensitive).\n";

    public static final String MESSAGE_RECORD_ADDED_SUCCESSFULLY =
            "All records from the %1$s are read"
                    + " and only non-existing records are added to the current Financial Planner.\n";

    public static final String MESSAGE_EXCEL_FILE_WRITTEN_SUCCESSFULLY =
            "The Excel file named %1$s has been written successfully in path: %2$s.\n";

    public static final String MESSAGE_EXPORT_COMMAND_ERRORS = "There is error, "
            + "it can either be no record found to export or the Excel file you want to write is opened.";

    public static final String MESSAGE_ARCHIVE_COMMAND_ERRORS = "There is error, "
            + "it can either be no record found to archive or the Excel file you want to write is opened.";

    public static final String MESSAGE_IMPORT_COMMAND_ERRORS = "There is error, "
            + "it can either be no record found to import or the Excel file you want to read is opened.";

    public static final String MESSAGE_ARCHIVE_SUCCESSFULLY =
            " The records in the Excel file will be no longer in the current Financial Planner.\n";

    public static final String MESSAGE_LIMITS_DO_NOT_EXIST =
            "There is no limit for that period of date";

    public static final String MESSAGE_FILE_OPENED =
            "The file is being opened, please close it before performing Export/Archive/Import command\n";
}
