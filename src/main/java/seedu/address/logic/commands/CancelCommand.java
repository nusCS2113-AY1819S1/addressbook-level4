package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.LogicManager;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Cancels any intermediate commands
 */

public class CancelCommand extends Command {
    public static final String COMMAND_WORD = "cancel";

    public static final String MESSAGE_SUCCESS = "Cancelled command: %1$s";


    private final String cancelledCommand;

    public CancelCommand(String cancelledCommand) {
        this.cancelledCommand = cancelledCommand;
    };
    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        LogicManager.setLogicState("primary");
        return new CommandResult(String.format(MESSAGE_SUCCESS, cancelledCommand));
    }

}
