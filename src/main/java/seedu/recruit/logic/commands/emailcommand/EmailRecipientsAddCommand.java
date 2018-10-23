package seedu.recruit.logic.commands.emailcommand;

import static java.util.Objects.requireNonNull;

import javafx.collections.ObservableList;
import seedu.recruit.commons.util.EmailUtil;
import seedu.recruit.logic.CommandHistory;
import seedu.recruit.logic.commands.CommandResult;
import seedu.recruit.model.Model;
import seedu.recruit.model.candidate.Candidate;
import seedu.recruit.model.joboffer.JobOffer;
import seedu.recruit.ui.MainWindow;

/**
 * This class handles the add sub command for email recipients phase
 */
public class EmailRecipientsAddCommand extends EmailRecipientsSelectCommand {
    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        EmailUtil emailUtil = model.getEmailUtil();

        //Add recipients only if commandWord is add.
        if (MainWindow.getDisplayedBook().equals("candidateBook")) {
            ObservableList<Candidate> recipients = model.getFilteredCandidateList();
            for (Candidate recipient : recipients) {
                emailUtil.addCandidate(recipient);
            }
            emailUtil.setAreRecipientsCandidates(true);
        } else {
            ObservableList<JobOffer> recipients = model.getFilteredCompanyJobList();
            for (JobOffer recipient : recipients) {
                emailUtil.addJobOffer(recipient);
            }
            emailUtil.setAreRecipientsCandidates(false);
        }

        String output = "Recipients added:\n";
        if (emailUtil.isAreRecipientsCandidates()) {
            output += model.getFilteredCandidateNames();
        } else {
            output += model.getFilteredRecipientJobOfferNames();
        }
        output += EmailRecipientsSelectCommand.MESSAGE_USAGE;
        return new CommandResult(output);
    }
}
