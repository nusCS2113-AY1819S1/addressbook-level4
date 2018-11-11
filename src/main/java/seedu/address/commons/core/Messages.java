package seedu.address.commons.core;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_MILESTONEDESCRIPTION =
            "Milestone description cannot be more than 40 characters! \n%1$s";
    public static final String MESSAGE_INVALID_TASK_DISPLAYED_INDEX = "The task index provided is invalid";
    public static final String MESSAGE_COMPLETED_TASK = "The task has completed already...";
    public static final String MESSAGE_TASKS_LISTED_OVERVIEW = "%1$d tasks listed!";
    public static final String MESSAGE_ZERO_HOURS_COMPLETION = "It is impossible to complete it in less than 1 hour ;)";
    public static final String MESSAGE_INVALID_DEADLINE = "The date selected does not exist";
    public static final String MESSAGE_DEADLINE_CONTAINS_ILLEGAL_CHARACTERS = "Input deadline "
            + "contains illegal characters";
}
