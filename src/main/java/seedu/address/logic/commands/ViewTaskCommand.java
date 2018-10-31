package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_COMPLETED_TASKS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_TASKS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_UNCOMPLETED_TASKS;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;

/**
 * Lists all persons in the address book to the user.
 */
public class ViewTaskCommand extends Command {

    public static final String COMMAND_WORD = "TDL_view";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Shows a list of uncompleted/completed tasks in the displayed to-do list.\n"
            + "Parameters: completed or uncompleted\n"
            + "Example: " + COMMAND_WORD + " completed";

    public static final String MESSAGE_SUCCESS_ALL = "Listed all tasks";
    public static final String MESSAGE_SUCCESS_COMPLETED = "Listed all completed tasks";
    public static final String MESSAGE_SUCCESS_UNCOMPLETED = "Listed all uncompleted tasks";

    private final String filter;

    /**
     * @param filter to determine the predicate used for updating filtered task list
     */
    public ViewTaskCommand(String filter) {
        requireNonNull(filter);

        this.filter = filter;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);

        model.commitTodoList();

        if (filter.equals("uncompleted")) {
            model.updateFilteredTaskList(PREDICATE_SHOW_ALL_UNCOMPLETED_TASKS);
            return new CommandResult(MESSAGE_SUCCESS_UNCOMPLETED);
        } else if (filter.equals("completed")) {
            model.updateFilteredTaskList(PREDICATE_SHOW_ALL_COMPLETED_TASKS);
            return new CommandResult(MESSAGE_SUCCESS_COMPLETED);
        } else {
            model.updateFilteredTaskList(PREDICATE_SHOW_ALL_TASKS);
            return new CommandResult(MESSAGE_SUCCESS_ALL);
        }
    }
}
