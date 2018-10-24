package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EXPENSE_CATEGORY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EXPENSE_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EXPENSE_VALUE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.expense.Expense;

/**
 * Adds an expense to the expenses list
 */
public class AddExpenseCommand extends Command {
    public static final String COMMAND_WORD = "addExpense";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an expense to the expenses list. \n"
            + "Parameters: "
            + PREFIX_EXPENSE_CATEGORY + "EXPENSE_CATEGORY "
            + PREFIX_EXPENSE_VALUE + "EXPENSE_VALUE "
            + PREFIX_EXPENSE_DATE + "EXPENSE_DATE "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_EXPENSE_CATEGORY + "food "
            + PREFIX_EXPENSE_VALUE + "11.11 "
            + PREFIX_EXPENSE_DATE + "11/11/2011 "
            + PREFIX_TAG + "taobao";

    public static final String MESSAGE_SUCCESS = "New expense added";

    private final Expense toAdd;

    /**
     * Creates an AddExpenseCommand to add the specified {@code Expense}
     */
    public AddExpenseCommand(Expense expense) {
        requireNonNull(expense);
        toAdd = expense;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        model.addExpense(toAdd);
        model.commitExpenseBook();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

}
