//@@author XiaoYunhan
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;

/**
 * Lists all persons in the address book to the user.
 */
public class SortTaskCommand extends Command {

    public static final String COMMAND_WORD = "TDL_sort";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Sort tasks in the order of module, date, priority, default or reverse order.\n"
            + "Parameters: default, date, module, priority or reverse\n"
            + "Example: " + COMMAND_WORD + " date";

    public static final String MESSAGE_SUCCESS_DATE = "Sorted all tasks based on due date";
    public static final String MESSAGE_SUCCESS_PRIORITY = "Sorted all tasks based on priority level";
    public static final String MESSAGE_SUCCESS_MODULE = "Sorted all tasks based on module code";
    public static final String MESSAGE_SUCCESS_DEFAULT = "Sorted all tasks based on default naming order";
    public static final String MESSAGE_SUCCESS_REVERSE = "Task list reversed";

    private final String filter;

    /**
     * @param filter to determine the predicate used for updating filtered task list
     */
    public SortTaskCommand(String filter) {
        requireNonNull(filter);

        this.filter = filter;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);

        if ("date".equals(filter)) {
            model.rankFilteredTaskDeadline();
            return new CommandResult(MESSAGE_SUCCESS_DATE);
        } else if ("module".equals(filter)) {
            model.rankFilteredTaskModule();
            return new CommandResult(MESSAGE_SUCCESS_MODULE);
        } else if ("default".equals(filter)) {
            model.rankTaskDefault();
            return new CommandResult(MESSAGE_SUCCESS_DEFAULT);
        } else if ("reverse".equals(filter)) {
            model.reverseTodoList();
            return new CommandResult(MESSAGE_SUCCESS_REVERSE);
        } else {
            model.rankFilteredTaskPriority();
            return new CommandResult(MESSAGE_SUCCESS_PRIORITY);
        }
    }
}
