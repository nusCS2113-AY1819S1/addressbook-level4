package seedu.recruit.logic.commands.EmailCommand;

import static java.util.Objects.requireNonNull;

import seedu.recruit.logic.CommandHistory;
import seedu.recruit.logic.LogicManager;
import seedu.recruit.logic.commands.CommandResult;
import seedu.recruit.logic.parser.RecruitBookParser;
import seedu.recruit.model.Model;

/**
 * Starts the 3-step process of Email
 */
public class EmailInitialiseCommand {
    public static final String COMMAND_WORD = "email";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Emails specified candidates about specified job offers"
            + "or specified companies about specified candidates.";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        RecruitBookParser.emailCommand = new EmailCommand());
        LogicManager.setLogicState(EmailSelectRecipientsCommand.COMMAND_LOGIC_STATE);
        return new CommandResult(EmailSelectRecipientsCommand.MESSAGE_USAGE);
    }
}
