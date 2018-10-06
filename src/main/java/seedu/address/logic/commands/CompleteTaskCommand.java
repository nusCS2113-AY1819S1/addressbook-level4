package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TITLE;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.task.Task;


/**
 * Completes a task in the Task Book
 */
public class CompleteTaskCommand extends Command {
    public static final String COMMAND_WORD = "complete";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Completes a task in the task book. "
        + "Parameters: "
        + PREFIX_TITLE + "TITLE \n"
        + "Example: " + COMMAND_WORD + " "
        + PREFIX_TITLE + "Do code refactoring";

    public static final String MESSAGE_SUCCESS = "Task completed: %1$s";
    public static final String MESSAGE_NONEXISTENT_TASK = "This task does not exist in the task book";

    private final Task toComplete;

    /**
     * Creates an AddCommand to add the specified {@code Task}
     */
    public CompleteTaskCommand(Task task) {
        requireNonNull(task);
        toComplete = task;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (!model.hasTask(toComplete)) {
            throw new CommandException(MESSAGE_NONEXISTENT_TASK);
        }

        toComplete.completed();
        // model.addTask(toAdd);
        model.commitTaskBook();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toComplete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof AddTaskCommand // instanceof handles nulls
            && toComplete.equals(((CompleteTaskCommand) other).toComplete));
    }
}
