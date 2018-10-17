package seedu.address.logic.commands;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.task.Task;

import java.util.List;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_TASKS;
import static seedu.address.storage.XmlSerializableTodoList.MESSAGE_DUPLICATE_TASK;

/**
 * Edits the {@code isComplete} field of an existing task in the to-do list.
 */
public class CompleteTaskCommand extends Command {

    public static final String COMMAND_WORD = "TDL_complete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Marks the task identified by the index number used in the displayed to-do list as completed.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_COMPLETE_TASK_SUCCESS = "Completed Task: %1$s";

    private final Index targetIndex;

    /**
     * @param index of the task in the filtered task list to edit
     */
    public CompleteTaskCommand(Index index) {
        requireNonNull(index);

        this.targetIndex = index;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Task> lastShownList = model.getFilteredTaskList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        Task taskToEdit = lastShownList.get(targetIndex.getZeroBased());
        Task editedTask = taskToEdit;
        editedTask.setAsCompleted();

        if (!taskToEdit.isSameTask(editedTask) && model.hasTask(editedTask)) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        }

        model.updateTask(taskToEdit, editedTask);
        model.updateFilteredTaskList(PREDICATE_SHOW_ALL_TASKS);
        model.commitTodoList();
        return new CommandResult(String.format(MESSAGE_COMPLETE_TASK_SUCCESS, editedTask));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CompleteTaskCommand)) {
            return false;
        }

        // state check
        CompleteTaskCommand c = (CompleteTaskCommand) other;
        return targetIndex.equals(c.targetIndex);
    }
}
