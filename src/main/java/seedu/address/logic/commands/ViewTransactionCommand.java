package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.timeidentifiedclass.exceptions.InvalidTimeFormatException;

/**
 * This command allows us to view the details of a transaction made at a specified time
 */
public class ViewTransactionCommand extends Command {
    public static final String COMMAND_WORD = "viewtransaction";

    private final String transactionTime;

    public ViewTransactionCommand(String transactionTime) {
        this.transactionTime = transactionTime;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        try {
            return new CommandResult(model.getTransactionAsString(transactionTime));
        } catch (InvalidTimeFormatException e) {
            throw new CommandException("Invalid transaction time entered");
        }
    }
}
