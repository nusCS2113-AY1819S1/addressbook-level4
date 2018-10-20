package seedu.recruit.logic.commands.emailcommand;

import static java.util.Objects.requireNonNull;

import seedu.recruit.commons.util.EmailUtil;
import seedu.recruit.logic.CommandHistory;
import seedu.recruit.logic.LogicManager;
import seedu.recruit.logic.commands.Command;
import seedu.recruit.logic.commands.CommandResult;
import seedu.recruit.model.Model;

/**
 * Starts the 4-step process of Email
 */
public class EmailInitialiseCommand extends Command {
    public static final String COMMAND_WORD = "email";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Emails specified candidates about specified job offers"
            + "or specified companies about specified candidates.";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);

        //Initiailising a fresh instance of EmailUtil
        EmailUtil emailUtil = new EmailUtil(model.getEmailUtil().getEmailSettings());
        model.setEmailUtil(emailUtil);
        LogicManager.setLogicState(EmailSelectRecipientsCommand.COMMAND_LOGIC_STATE);
        return new CommandResult(EmailSelectRecipientsCommand.MESSAGE_USAGE);
    }
}
