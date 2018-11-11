package seedu.recruit.logic.commands;

import seedu.recruit.commons.core.EventsCenter;
import seedu.recruit.commons.events.logic.ChangeLogicStateEvent;
import seedu.recruit.commons.events.ui.ShowLastViewedBookRequestEvent;
import seedu.recruit.logic.CommandHistory;

import seedu.recruit.logic.commands.exceptions.CommandException;
import seedu.recruit.model.Model;
import seedu.recruit.model.UserPrefs;

/**
 * Cancels any intermediate commands
 */

public class CancelCommand extends Command {
    public static final String COMMAND_WORD = "cancel";

    public static final String MESSAGE_SUCCESS = "Cancelled/Stopped: %1$s\n";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Cancels any intermediate commands.\n";

    private String cancelledCommand;

    public CancelCommand(String cancelledCommand) {
        this.cancelledCommand = cancelledCommand;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history, UserPrefs userPrefs) throws CommandException {
        if (ShortlistCandidateInitializationCommand.isShortlisting()) {
            ShortlistCandidateInitializationCommand.isDoneShortlisting();
            this.cancelledCommand = ShortlistCandidateInitializationCommand.COMMAND_WORD;
            EventsCenter.getInstance().post(new ShowLastViewedBookRequestEvent());
        }
        if (DeleteShortlistedCandidateInitializationCommand.isDeleting()) {
            DeleteShortlistedCandidateInitializationCommand.isDoneDeleting();
            this.cancelledCommand = DeleteShortlistedCandidateInitializationCommand.COMMAND_WORD;
        }
        EventsCenter.getInstance().post(new ChangeLogicStateEvent("primary"));

        return new CommandResult(String.format(MESSAGE_SUCCESS, cancelledCommand));
    }
}
