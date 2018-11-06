package seedu.recruit.logic.commands.emailcommand;

import static java.util.Objects.requireNonNull;

import seedu.recruit.logic.CommandHistory;
import seedu.recruit.logic.LogicManager;
import seedu.recruit.logic.commands.CommandResult;
import seedu.recruit.model.Model;
import seedu.recruit.model.UserPrefs;

/**
 * This class handles the back sub command for email send phase
 */
public class EmailSendBackCommand extends EmailSendCommand {
    @Override
    public CommandResult execute(Model model, CommandHistory history, UserPrefs userPrefs) {
        requireNonNull(model);

        LogicManager.setLogicState(EmailContentsCommand.COMMAND_LOGIC_STATE);
        return new CommandResult(EmailContentsCommand.MESSAGE_USAGE);
    }
}
