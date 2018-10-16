package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRODUCT;
// TODO: To add transaction items with quantity.
// import static seedu.address.logic.parser.CliSyntax.PREFIX_QUANTITY;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.timeidentifiedclass.exceptions.InvalidTimeFormatException;
import seedu.address.model.timeidentifiedclass.shopday.exceptions.ClosedShopDayException;
import seedu.address.model.timeidentifiedclass.shopday.exceptions.DuplicateTransactionException;
import seedu.address.model.timeidentifiedclass.transaction.Transaction;

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
        try {
            model.addTransaction(toAdd);
        } catch (InvalidTimeFormatException e) {
            return new CommandResult(e.getExceptionMessage() + ". Upon adding this transaction");
        } catch (ClosedShopDayException e) {
            return new CommandResult(e.getExceptionMessage() + ". Upon adding this transaction");
        } catch (DuplicateTransactionException e) {
            return new CommandResult(e.getLocalizedMessage() + ". Upon adding this transaction");
        }
        model.commitAddressBook();
        return new CommandResult(MESSAGE_SUCCESS + toAdd.getTime());
    }
}
