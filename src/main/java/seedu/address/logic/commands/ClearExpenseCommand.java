package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.model.ExpenseBook;
import seedu.address.model.Model;


/**
 * Clears the expense book.
 */
public class ClearExpenseCommand extends Command {

    public static final String COMMAND_WORD = "clearExpense";
    public static final String MESSAGE_SUCCESS = "Expense book has been cleared!";


    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.resetData(new ExpenseBook());
        model.commitExpenseBook();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
