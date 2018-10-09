package seedu.address.logic.commands.EmailCommand;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.LogicManager;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.parser.RecruitBookParser;
import seedu.address.model.Model;

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
        RecruitBookParser.setEmailCommand(new EmailCommand());
        LogicManager.setLogicState(EmailSelectRecipientsCommand.COMMAND_LOGIC_STATE);
        return new CommandResult(EmailSelectRecipientsCommand.MESSAGE_USAGE);
    }
}
