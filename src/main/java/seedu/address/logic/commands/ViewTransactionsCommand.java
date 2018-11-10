package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_TRANSACTIONS;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;

/**
 * Sell a drink from inventory.
 */
public class ViewTransactionsCommand extends Command {

    public static final String COMMAND_WORD = "viewTrans";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": View all transactions recorded in Drink I/O ";

    public static final String MESSAGE_SUCCESS = "Listed all transactions: %s";


    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);

        model.updateFilteredTransactionList(PREDICATE_SHOW_ALL_TRANSACTIONS);

        return new CommandResult(String.format(MESSAGE_SUCCESS, model.getTransactionList()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ViewTransactionsCommand); // instanceof handles nulls;
    }
}
