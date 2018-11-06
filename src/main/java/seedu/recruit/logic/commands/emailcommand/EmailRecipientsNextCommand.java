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
import seedu.recruit.model.UserPrefs;

/**
 * This class handles the next sub command for email recipients phase
 */
public class EmailRecipientsNextCommand extends EmailRecipientsCommand {
    @Override
    public CommandResult execute(Model model, CommandHistory history, UserPrefs userPrefs) {
        requireNonNull(model);
        EmailUtil emailUtil = model.getEmailUtil();

        //Check if content array is empty, if it is, do not allow to move on to next stage
        boolean isEmpty = false;

        //if recipient are candidates and candidate arraylist is empty
        if (emailUtil.isAreRecipientsCandidates() && emailUtil.getCandidates().size() == 0) {
            isEmpty = true;
        }
        //if companies are candidates and job offers arraylist is empty
        if (!emailUtil.isAreRecipientsCandidates() && emailUtil.getJobOffers().size() == 0) {
            isEmpty = true;
        }

        if (isEmpty) {
            return new CommandResult("ERROR: There are no recipients selected!\n"
                    + EmailRecipientsCommand.MESSAGE_USAGE);
        } else {
            if (emailUtil.isAreRecipientsCandidates()) {
                EventsCenter.getInstance().post(new ShowCompanyBookRequestEvent());
            } else {
                EventsCenter.getInstance().post(new ShowCandidateBookRequestEvent());
            }

            LogicManager.setLogicState(EmailContentsCommand.COMMAND_LOGIC_STATE);
            return new CommandResult(EmailContentsCommand.MESSAGE_USAGE);
        }
    }
}
