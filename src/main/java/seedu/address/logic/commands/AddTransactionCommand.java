package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

public class AddTransactionCommand extends Command {

    private static final String COMMAND_WORD = "transaction";
    private static final String MESSAGE_SUCCESS = "Transaction successfully recorded.";

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        return null;
    }
}
