package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.expenses.ExpensesList;

/**
 * Clears the expenses list.
 */
public class ClearExpensesCommand extends Command {

    public static final String COMMAND_WORD = "clearExpenses";
    public static final String COMMAND_ALIAS = "ce";
    public static final String MESSAGE_SUCCESS = "Expenses list has been cleared!";
    public static final String MESSAGE_FAILURE_CLEARED = "Expenses list is empty!";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Clears the Expenses List\n"
            + "Example: " + COMMAND_WORD;

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        model.updateFilteredExpensesList(model.PREDICATE_SHOW_ALL_EXPENSES);
        if (model.getFilteredExpensesList().size() > 0) {
            model.resetDataExpenses(new ExpensesList());
            model.commitExpensesList();
        } else {
            throw new CommandException(MESSAGE_FAILURE_CLEARED);
        }

        return new CommandResult(MESSAGE_SUCCESS);
    }
}
