package seedu.recruit.logic.commands.emailcommand;

import static java.util.Objects.requireNonNull;

import seedu.recruit.commons.core.EventsCenter;
import seedu.recruit.commons.events.ui.ShowCandidateBookRequestEvent;
import seedu.recruit.commons.events.ui.ShowCompanyBookRequestEvent;
import seedu.recruit.commons.util.EmailUtil;
import seedu.recruit.logic.CommandHistory;
import seedu.recruit.logic.LogicManager;
import seedu.recruit.logic.commands.CommandResult;
import seedu.recruit.model.Model;

/**
 * This class handles the back sub command for email contents phase
 */
public class EmailContentsBackCommand extends EmailContentsSelectCommand {
    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        EmailUtil emailUtil = model.getEmailUtil();

        if (emailUtil.isAreRecipientsCandidates()) {
            EventsCenter.getInstance().post(new ShowCandidateBookRequestEvent());
        } else {
            EventsCenter.getInstance().post(new ShowCompanyBookRequestEvent());
        }

        LogicManager.setLogicState(EmailRecipientsSelectCommand.COMMAND_LOGIC_STATE);
        return new CommandResult(EmailRecipientsSelectCommand.MESSAGE_USAGE);
    }
}
