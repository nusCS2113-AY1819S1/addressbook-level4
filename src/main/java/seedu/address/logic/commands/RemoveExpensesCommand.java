package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.expenses.Expenses;

/**
 * Remove an expenses amount of the employee from the Expenses List.
 */
public class RemoveExpensesCommand extends Command {
    public static final String COMMAND_WORD = "deleteExpenses";
    public static final String COMMAND_ALIAS = "de";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Remove the expenses identified by the index number used in the displayed person list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_REMOVE_EXPENSES_SUCCESS = "Remove Expenses: %1$s";

    private final Index targetIndex;

    public RemoveExpensesCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Expenses> lastShownList = model.getFilteredExpensesList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EXPENSES_DISPLAYED_INDEX);
        }

        Expenses expensesToRemove = lastShownList.get(targetIndex.getZeroBased());
        model.deleteExpenses(expensesToRemove);
        model.commitExpensesList();
        return new CommandResult(String.format(MESSAGE_REMOVE_EXPENSES_SUCCESS, expensesToRemove));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RemoveExpensesCommand // instanceof handles nulls
                && targetIndex.equals(((RemoveExpensesCommand) other).targetIndex)); // state check
    }
}


