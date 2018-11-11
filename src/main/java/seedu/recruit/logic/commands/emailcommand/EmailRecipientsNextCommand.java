package seedu.recruit.logic.commands.emailcommand;

import static java.util.Objects.requireNonNull;

import seedu.recruit.commons.core.EventsCenter;
import seedu.recruit.commons.events.logic.ChangeLogicStateEvent;
import seedu.recruit.commons.events.ui.ShowCandidateBookRequestEvent;
import seedu.recruit.commons.events.ui.ShowCompanyBookRequestEvent;
import seedu.recruit.commons.util.EmailUtil;
import seedu.recruit.logic.CommandHistory;
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
        if (isEmpty(emailUtil)) {
            return new CommandResult(NEXT_RECIPIENTS_ERROR_NO_RECIPIENTS + EmailRecipientsCommand.MESSAGE_USAGE);
        } else {
            changeBook(emailUtil);
            EventsCenter.getInstance().post(new ChangeLogicStateEvent(EmailContentsCommand.COMMAND_LOGIC_STATE));
            return new CommandResult(EmailContentsCommand.MESSAGE_USAGE);
        }
    }

    /**
     * checks if recipients array is empty
     * @param emailUtil
     * @return boolean value to see if recipients array is empty
     */
    private boolean isEmpty(EmailUtil emailUtil) {
        if (emailUtil.getAreRecipientsCandidates() && emailUtil.getCandidates().size() == 0) {
            return true;
        }
        if (!emailUtil.getAreRecipientsCandidates() && emailUtil.getJobOffers().size() == 0) {
            return true;
        }
        return false;
    }

    /**
     * changes the current book when proceeding to the next step
     * @param emailUtil
     */
    private void changeBook(EmailUtil emailUtil) {
        if (emailUtil.getAreRecipientsCandidates()) {
            EventsCenter.getInstance().post(new ShowCompanyBookRequestEvent());
        } else {
            EventsCenter.getInstance().post(new ShowCandidateBookRequestEvent());
        }
    }
}
