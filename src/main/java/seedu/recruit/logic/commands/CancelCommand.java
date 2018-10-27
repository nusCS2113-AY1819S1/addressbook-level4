package seedu.recruit.logic.commands;

import seedu.recruit.logic.CommandHistory;
import seedu.recruit.logic.LogicManager;
import seedu.recruit.logic.commands.exceptions.CommandException;
import seedu.recruit.model.Model;
import seedu.recruit.ui.MainWindow;

/**
 * Cancels any intermediate commands
 */

public class CancelCommand extends Command {
    public static final String COMMAND_WORD = "cancel";

    public static final String MESSAGE_SUCCESS = "Cancelled command: %1$s";

    private String cancelledCommand;

    public CancelCommand(String cancelledCommand) {
        this.cancelledCommand = cancelledCommand;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        if (ShortlistCandidateInitializationCommand.isProcessing()) {
            ShortlistCandidateInitializationCommand.isDoneProcessing();
            this.cancelledCommand = ShortlistCandidateInitializationCommand.COMMAND_WORD;
            MainWindow.switchToLastViewedBook();
        }
        LogicManager.setLogicState("primary");
        return new CommandResult(String.format(MESSAGE_SUCCESS, cancelledCommand));
    }
}
