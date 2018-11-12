package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.JumpToListExpensesRequestEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.expenses.Expenses;

/**
 * Selects a expenses identified using it's displayed index from the expenses list.
 */
public class SelectExpensesCommand extends Command {

    public static final String COMMAND_WORD = "selectExpenses";
    public static final String COMMAND_ALIAS = "se";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Selects the expenses identified by the index number used in the displayed expenses list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SELECT_EXPENSES_SUCCESS = "Selected Expenses: %1$s";

    private final Index targetIndex;

    public SelectExpensesCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        List<Expenses> filteredExpensesList = model.getFilteredExpensesList();

        if (targetIndex.getZeroBased() >= filteredExpensesList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EXPENSES_DISPLAYED_INDEX);
        }

        EventsCenter.getInstance().post(new JumpToListExpensesRequestEvent(targetIndex));
        return new CommandResult(String.format(MESSAGE_SELECT_EXPENSES_SUCCESS, targetIndex.getOneBased()));

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SelectExpensesCommand // instanceof handles nulls
                && targetIndex.equals(((SelectExpensesCommand) other).targetIndex)); // state check
    }
}
