package seedu.recruit.logic.commands.emailcommand;

import static java.util.Objects.requireNonNull;

import seedu.recruit.commons.core.EventsCenter;
import seedu.recruit.commons.events.logic.ChangeLogicStateEvent;
import seedu.recruit.commons.util.EmailUtil;
import seedu.recruit.logic.CommandHistory;

import seedu.recruit.logic.commands.Command;
import seedu.recruit.logic.commands.CommandResult;
import seedu.recruit.model.Model;
import seedu.recruit.model.UserPrefs;

/**
 * Starts the 4-step process of Email
 */
public class EmailInitialiseCommand extends Command {
    public static final String COMMAND_WORD = "email";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Emails specified candidates about specified job offers"
            + "or specified companies about specified candidates.";

    @Override
    public CommandResult execute(Model model, CommandHistory history, UserPrefs userPrefs) {
        requireNonNull(model);

        //Initiailising a fresh instance of EmailUtil
        EmailUtil emailUtil = new EmailUtil();
        model.setEmailUtil(emailUtil);
        EventsCenter.getInstance().post(new ChangeLogicStateEvent(EmailRecipientsCommand.COMMAND_LOGIC_STATE));

        return new CommandResult(EmailRecipientsCommand.MESSAGE_USAGE);
    }
}
