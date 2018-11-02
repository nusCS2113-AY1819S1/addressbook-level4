package seedu.recruit.logic.commands;

import seedu.recruit.commons.core.EventsCenter;
import seedu.recruit.commons.events.ui.ShowLastViewedBookRequestEvent;
import seedu.recruit.logic.CommandHistory;
import seedu.recruit.logic.LogicManager;
import seedu.recruit.logic.commands.exceptions.CommandException;
import seedu.recruit.model.Model;

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
        if (ShortlistCandidateInitializationCommand.isShortlisting()) {
            ShortlistCandidateInitializationCommand.isDoneShortlisting();
            this.cancelledCommand = ShortlistCandidateInitializationCommand.COMMAND_WORD;
            EventsCenter.getInstance().post(new ShowLastViewedBookRequestEvent());
        }
        if (DeleteShortlistedCandidateInitializationCommand.isDeleting()) {
            DeleteShortlistedCandidateInitializationCommand.isDoneDeleting();
            this.cancelledCommand = DeleteShortlistedCandidateInitializationCommand.COMMAND_WORD;
        }
        LogicManager.setLogicState("primary");
        return new CommandResult(String.format(MESSAGE_SUCCESS, cancelledCommand));
    }
}
