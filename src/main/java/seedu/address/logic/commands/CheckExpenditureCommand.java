package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_EXPENDITURES;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.expenditureinfo.Expenditure;
import seedu.address.model.task.Task;

import java.util.List;

/**
 * Lists all persons in the address book to the user.
 */
public class CheckExpenditureCommand extends Command {

    public static final String COMMAND_WORD = "ET_check";
    public static final String COMMAND_ALIAS = "c";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Check expenditures in a specific period\n"
            + "Parameters: Date1 ( must be a positive number)"
            + " Date2 (must larger than previous number)\n"
            + "Examples:" + COMMAND_WORD + "27-09-2018"
            + "09-10-2018";

    public static final String MESSAGE_SUCCESS = "Show expenditures in this period";
    private final Index targetIndex;

    /**
     * @param index of the task in the filtered task list to edit
     */
    public CheckExpenditureCommand(Index index) {
        requireNonNull(index);

        this.targetIndex = index;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        requireNonNull(model);
        List<Expenditure> lastShownList = model.getFilteredExpenditureList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        Task taskToEdit = lastShownList.get(targetIndex.getZeroBased());
        Task editedTask = taskToEdit;
        editedTask.setAsUncompleted();

        if (!taskToEdit.isSameTask(editedTask) && model.hasTask(editedTask)) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        }

        model.updateTask(taskToEdit, editedTask);
        model.updateFilteredTaskList(PREDICATE_SHOW_ALL_TASKS);
        model.commitTodoList();
        return new CommandResult(String.format(MESSAGE_COMPLETE_TASK_SUCCESS, editedTask));
    }
}
