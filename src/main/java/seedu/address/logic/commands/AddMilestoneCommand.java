package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MILESTONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RANK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TITLE;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.task.Milestone;

/**
 * Adds a milestone to a task in the taskbook
 */
public class AddMilestoneCommand extends Command {
    public static final String COMMAND_WORD = "add_milestone";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds milestone(s) to selected task. "
            + "Parameters: "
            + PREFIX_TITLE + "TITLE "
            + PREFIX_MILESTONE + "MILESTONE "
            + PREFIX_RANK + "RANK \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_TITLE + "Complete CS2113T Week 8 LO "
            + PREFIX_MILESTONE + "Complete Sections 8.1 to 8.5 "
            + PREFIX_RANK + "1";

    //print observable list v1.3

    //prompt user to select task v1.3

    //create Task object and assign user's choice to it v1.3
    //Task selectedTask = ;

    //assign milestone to user's choice v1.3

    private final Milestone toAdd;

    /**
     * Creates a AddMilestoneCommand to add the specified {@code Milestone}
     */
    public AddMilestoneCommand(Milestone milestone) {
        requireNonNull(milestone);
        toAdd = milestone;
    }

    public static final String MESSAGE_SUCCESS = "New milestone added";
    public static final String MESSAGE_TASK_NOT_FOUND = "This task does not exist in the task book";

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandExceptionException {
        requireNonNull(model);

        //update Model & ModelManager once interaction between Task & Milestone confirmed

        }


    }

}
