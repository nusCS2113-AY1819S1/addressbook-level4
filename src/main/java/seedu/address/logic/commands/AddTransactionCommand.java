package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRODUCT;
// TODO: To add transaction items with quantity.
// import static seedu.address.logic.parser.CliSyntax.PREFIX_QUANTITY;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.timeidentifiedclass.Transaction;
import seedu.address.model.timeidentifiedclass.exceptions.DuplicateTransactionException;
import seedu.address.model.timeidentifiedclass.exceptions.InvalidTimeFormatException;


/**
 * Adds a transaction to the address book.
 */
public class AddTransactionCommand extends Command {

    public static final String COMMAND_WORD = "transaction";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a transaction. "
            + "Parameters: "
            + PREFIX_PRODUCT + "PRODUCT NAME"
            + PREFIX_PRODUCT + "PRODUCT NAME"
            + "... \n"
            + "Example: "
            + PREFIX_PRODUCT + "Apples "
            + PREFIX_PRODUCT + "Milk "
            + PREFIX_PRODUCT + "Milk";


    private static final String MESSAGE_SUCCESS = "Transaction successfully recorded for time ";
    private final Transaction toAdd;

    public AddTransactionCommand(Transaction transaction) {
        requireNonNull(transaction);
        toAdd = transaction;
    }
    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {

        TreeMap<String, Integer> transactionRecord = toAdd.getTransactionRecord();
        List<String> listWithProductsToAdd = new ArrayList<String>(transactionRecord.keySet());

        for (String x: listWithProductsToAdd) {
            if (model.hasProductName(x)) {
                try {
                    model.addTransaction(toAdd);
                } catch (InvalidTimeFormatException e) {
                    return new CommandResult(e.getExceptionMessage() + ". Upon adding this transaction");
                } catch (DuplicateTransactionException e) {
                    return new CommandResult(e.getLocalizedMessage() + ". Upon adding this transaction");
                }
                model.commitProductDatabase();
                return new CommandResult(MESSAGE_SUCCESS + toAdd.getTransactionTime());
            } else {
                throw new CommandException("The product does not exist");
            }
        }
        throw new CommandException("The product does not exist");
    }
}
