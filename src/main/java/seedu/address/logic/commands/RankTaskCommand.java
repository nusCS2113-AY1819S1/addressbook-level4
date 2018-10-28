package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;

/**
 * Lists all persons in the address book to the user.
 */
public class RankTaskCommand extends Command {

    public static final String COMMAND_WORD = "TDL_rank";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Rank and show tasks in the sequence of deadline date, module or priority.\n"
            + "Parameters: date, module or priority\n"
            + "Example: " + COMMAND_WORD + " date";

    public static final String MESSAGE_SUCCESS_DATE = "Listed all tasks in the rank of deadline date";
    public static final String MESSAGE_SUCCESS_PRIORITY = "Listed all tasks in the rank of priority";
    public static final String MESSAGE_SUCCESS_MODULE = "Listed all tasks in the rank of module code";

    private final String filter;

    /**
     * @param filter to determine the predicate used for updating filtered task list
     */
    public RankTaskCommand(String filter) {
        requireNonNull(filter);

        this.filter = filter;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);

        if (filter.equals("date")) {
            model.rankFilteredTaskDeadline();
            return new CommandResult(MESSAGE_SUCCESS_DATE);
        }
        else if (filter.equals("module")) {
            model.rankFilteredTaskModule();
            return new CommandResult(MESSAGE_SUCCESS_MODULE);
        }
        else {
            model.rankFilteredTaskPriority();
            return new CommandResult(MESSAGE_SUCCESS_PRIORITY);
        }
    }
}
