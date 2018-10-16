package seedu.recruit.logic.commands.emailcommand;

import static java.util.Objects.requireNonNull;

import seedu.recruit.commons.util.EmailUtil;
import seedu.recruit.logic.CommandHistory;
import seedu.recruit.logic.LogicManager;
import seedu.recruit.logic.commands.Command;
import seedu.recruit.logic.commands.CommandResult;
import seedu.recruit.model.Model;
import seedu.recruit.ui.MainWindow;


/**
 * 2nd step of the Email command
 */
public class EmailSelectRecipientsCommand extends Command {
    public static final String COMMAND_WORD = "next";
    public static final String MESSAGE_USAGE = "Find the recipients that you are going to email\n "
            + "Type \"next\" when you have done so to move on to the next step.";
    public static final String COMMAND_LOGIC_STATE = "EmailSelectRecipients";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        EmailUtil emailUtil = model.getEmailUtil();

        if (MainWindow.getDisplayedBook().equals("candidateBook")) {
            System.out.println("Candidate Book!");
            emailUtil.setRecipients(model.getFilteredCandidateList());
            emailUtil.setAreRecipientsCandidates(true);
        } else {
            System.out.println("Company Book!");
            emailUtil.setRecipients(model.getFilteredCompanyJobList());
            emailUtil.setAreRecipientsCandidates(false);
        }

        model.setEmailUtil(emailUtil);
        LogicManager.setLogicState(EmailSelectContentsCommand.COMMAND_LOGIC_STATE);
        return new CommandResult(EmailSelectContentsCommand.MESSAGE_USAGE);
    }
}
