package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.CompleteTaskCommandParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.task.Task;

//@@author chelseyong
/**
 * Completes a task in the Task Book
 */
public class CompleteTaskCommand extends Command implements CommandParser {
    public static final String COMMAND_WORD = "complete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Completes the task identified by the index number used in the displayed task list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SUCCESS = "Task completed: %1$s";
    public static final String MESSAGE_NONEXISTENT_TASK = "This task does not exist in the task book";

    private final Index targetIndex;
    public CompleteTaskCommand() {
        // Null so that it can be initialized in LogicManager
        // Check in JUnit test
        targetIndex = null;
    }
    /**
     * Creates an CompleteTaskCommand to add the specified {@code Task}
     */
    public CompleteTaskCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Task> lastShownList = model.getFilteredTaskList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        Task taskToComplete = lastShownList.get(targetIndex.getZeroBased());
        if (taskToComplete.isCompleted()) {
            throw new CommandException(Messages.MESSAGE_COMPLETED_TASK);
        }
        model.completeTask(taskToComplete);
        model.commitTaskBook();
        return new CommandResult(String.format(MESSAGE_SUCCESS, taskToComplete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof CompleteTaskCommand // instanceof handles nulls
            && targetIndex.equals(((CompleteTaskCommand) other).targetIndex));
    }

    @Override
    public Command parse(String arguments) throws ParseException {
        return new CompleteTaskCommandParser().parse(arguments);
    }

    @Override
    public String getCommandWord() {
        return COMMAND_WORD;
    }
}
