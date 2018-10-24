package seedu.recruit.logic.commands.emailcommand;

import static java.util.Objects.requireNonNull;

import seedu.recruit.commons.util.EmailUtil;
import seedu.recruit.logic.CommandHistory;
import seedu.recruit.logic.LogicManager;
import seedu.recruit.logic.commands.CommandResult;
import seedu.recruit.model.Model;
import seedu.recruit.ui.MainWindow;

/**
 * This class handles the back sub command for email contents phase
 */
public class EmailContentsBackCommand extends EmailContentsSelectCommand {
    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        EmailUtil emailUtil = model.getEmailUtil();

        if (emailUtil.isAreRecipientsCandidates()) {
            MainWindow.switchToCandidateBook();
        } else {
            MainWindow.switchToCompanyBook();
        }

        LogicManager.setLogicState(EmailRecipientsSelectCommand.COMMAND_LOGIC_STATE);
        return new CommandResult(EmailRecipientsSelectCommand.MESSAGE_USAGE);
    }
}
