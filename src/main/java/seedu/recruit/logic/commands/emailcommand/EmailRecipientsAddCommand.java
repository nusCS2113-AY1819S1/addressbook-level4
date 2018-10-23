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

        //Check if there are already recipients added
        if (!emailUtil.isHasRecipientsAdded()) {
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
            emailUtil.setHasRecipientsAdded(true);
        } else {
            //displayed book is candidates and recipients are candidates.
            //send error if displayedbook is company.
            if (emailUtil.isAreRecipientsCandidates()) {
                if (MainWindow.getDisplayedBook().equals("candidateBook")) {
                    ObservableList<Candidate> recipients = model.getFilteredCandidateList();
                    for (Candidate recipient : recipients) {
                        emailUtil.addCandidate(recipient);
                    }
                } else {
                    return new CommandResult("ERROR: You can only add candidates!");
                }
            } else {
                if (MainWindow.getDisplayedBook().equals("companyBook")) {
                    ObservableList<JobOffer> recipients = model.getFilteredCompanyJobList();
                    for (JobOffer recipient : recipients) {
                        emailUtil.addJobOffer(recipient);
                    }
                } else {
                    return new CommandResult("ERROR: You can only add job offers!");
                }
            }
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
