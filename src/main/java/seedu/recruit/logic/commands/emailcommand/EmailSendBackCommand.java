package seedu.recruit.logic.commands.emailcommand;

import static java.util.Objects.requireNonNull;

import seedu.recruit.logic.CommandHistory;
import seedu.recruit.logic.LogicManager;
import seedu.recruit.logic.commands.CommandResult;
import seedu.recruit.model.Model;

/**
 * This class handles the back sub command for email send phase
 */
public class EmailSendBackCommand extends EmailSendCommand {
    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);

        LogicManager.setLogicState(EmailContentsSelectCommand.COMMAND_LOGIC_STATE);
        return new CommandResult(EmailContentsSelectCommand.MESSAGE_USAGE);
    }
}
