package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_MILESTONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ORDER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TITLE;

/**
 * Adds a milestone to a task in the taskbook
 */
public class AddMilestoneCommand extends Command {
    public static final String COMMAND_WORD = "add_milestone";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds milestone(s) to selected task. "
            + "Parameters: "
            + PREFIX_TITLE + "TITLE "
            + PREFIX_MILESTONE + "MILESTONE "
            + PREFIX_ORDER + "ORDER \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_TITLE + "Complete CS2113T Week 8 LO "
            + PREFIX_MILESTONE + "Complete Sections 8.1 to 8.5 "
            + PREFIX_ORDER + "1";

    public static final String MESSAGE_SUCCESS = "New milestone added";
    public static final String MESSAGE_TASK_NOT_FOUND = "This task does not exist in the task book";


}
