package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.timeidentifiedclass.transaction.Transaction;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRODUCT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUANTITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_TRANSACTION;

import static java.util.Objects.requireNonNull;

public class AddTransactionCommand extends Command {

    private static final String COMMAND_WORD = "transaction";
    private static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a transaction. "
            + "Parameters: "
            + PREFIX_PRODUCT + "PRODUCT NAME"
            + PREFIX_QUANTITY + "PRODUCT QUANTITY"
            + "..."
            + PREFIX_END_TRANSACTION
            + "\n Example: "
            + PREFIX_PRODUCT + "Apples"
            + PREFIX_QUANTITY + "3"
            + PREFIX_PRODUCT + "Milk"
            + PREFIX_QUANTITY + "2"
            + PREFIX_END_TRANSACTION;

    private static final String MESSAGE_SUCCESS = "Transaction successfully recorded for time ";
    private final Transaction toAdd;

    public AddTransactionCommand(Transaction transaction) {
        requireNonNull(transaction);
        toAdd = transaction;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        model.addTransaction(toAdd);
        model.commitAddressBook();
        return new CommandResult(MESSAGE_SUCCESS + toAdd.getTime());
    }
}
