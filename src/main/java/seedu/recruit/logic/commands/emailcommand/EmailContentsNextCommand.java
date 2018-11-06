package seedu.recruit.logic.commands.emailcommand;

import static java.util.Objects.requireNonNull;

import seedu.recruit.commons.util.EmailUtil;
import seedu.recruit.logic.CommandHistory;
import seedu.recruit.logic.LogicManager;
import seedu.recruit.logic.commands.CommandResult;
import seedu.recruit.model.Model;

/**
 * This class handles the next sub command for email contents phase
 */
public class EmailContentsNextCommand extends EmailContentsCommand {
    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        EmailUtil emailUtil = model.getEmailUtil();

        //Check if content array is empty, if it is, do not allow to move on to next stage
        boolean isEmpty = false;


        if (emailUtil.isAreRecipientsCandidates() && emailUtil.getJobOffers().size() == 0) {
            isEmpty = true;
        }

        if (!emailUtil.isAreRecipientsCandidates() && emailUtil.getCandidates().size() == 0) {
            isEmpty = true;
        }

        if (isEmpty) {
            return new CommandResult(NEXT_CONTENTS_ERROR_NO_CONTENTS + EmailContentsCommand.MESSAGE_USAGE);
        } else {
            LogicManager.setLogicState(EmailSendCommand.COMMAND_LOGIC_STATE);

            return new CommandResult(EmailSendCommand.MESSAGE_USAGE);
        }
    }
}
