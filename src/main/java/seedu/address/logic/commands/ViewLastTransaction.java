package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.timeidentifiedclass.transaction.Transaction;

/**
 * Javadoc
 */
public class ViewLastTransaction extends Command {

    public static final String COMMAND_WORD = "latest";
    public static final String MESSAGE_USAGE = ": Shows the details of the latest transaction.";
    public static final String NO_LATEST_TRANSACTION = "No transaction made yet.";

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        Transaction latestTransaction = model.getLastTransaction();
        if (latestTransaction == null) {
            return new CommandResult(NO_LATEST_TRANSACTION);
        }
        return new CommandResult(latestTransaction.getTransactionRecordAsString());
    }
}
