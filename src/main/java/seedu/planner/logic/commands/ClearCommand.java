package seedu.planner.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.planner.logic.CommandHistory;
import seedu.planner.model.FinancialPlanner;
import seedu.planner.model.Model;

/**
 * Clears the financial planner.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Financial planner has been cleared!";


    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.resetData(new FinancialPlanner());
        model.commitFinancialPlanner();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
