package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;

/**
 * Sell a drink from inventory.
 * // TODO: STUB
 */
public class ViewTransactionsCommand extends Command {

    public static final String COMMAND_WORD = "viewTrans";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": View all transactions recorded in Drink I/O ";

    public static final String MESSAGE_SUCCESS = "Listed all transactions: %s";


    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        //TransactionList transactionList = model.getTransactionList();
        // String.format(MESSAGE_SUCCESS, model.getTransactions.size());
        String result = model.getTransactions();
        return new CommandResult(String.format(MESSAGE_SUCCESS, result));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ViewTransactionsCommand); // instanceof handles nulls;
    }
}
