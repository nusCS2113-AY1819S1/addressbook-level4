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
            + ": Reverse or Sort tasks in the order of module, date, priority or default.\n"
            + "Parameters: default, date, module or priority\n"
            + "Example: " + COMMAND_WORD + " date";

    public static final String MESSAGE_SUCCESS_DATE = "Listed all tasks in the rank of deadline date";
    public static final String MESSAGE_SUCCESS_PRIORITY = "Listed all tasks in the rank of priority";
    public static final String MESSAGE_SUCCESS_MODULE = "Listed all tasks in the rank of module code";
    public static final String MESSAGE_SUCCESS_DEFAULT = "Listed all tasks in the default rank";
    public static final String MESSAGE_SUCCESS_REVERSE = "Reversed all tasks";

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
