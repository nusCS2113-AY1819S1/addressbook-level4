package seedu.recruit.logic.commands.emailcommand;

import static java.util.Objects.requireNonNull;

import javafx.collections.ObservableList;
import seedu.recruit.commons.util.EmailUtil;
import seedu.recruit.logic.CommandHistory;
import seedu.recruit.logic.commands.CommandResult;
import seedu.recruit.model.Model;
import seedu.recruit.model.candidate.Candidate;
import seedu.recruit.model.joboffer.JobOffer;

/**
 * This class handles the add sub command for email contents phase
 */
public class EmailContentsAddCommand extends EmailContentsSelectCommand {
    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        EmailUtil emailUtil = model.getEmailUtil();

        if (emailUtil.isAreRecipientsCandidates()) {
            ObservableList<JobOffer> contents = model.getFilteredCompanyJobList();
            for (JobOffer content : contents) {
                emailUtil.addJobOffer(content);
            }
        } else {
            ObservableList<Candidate> contents = model.getFilteredCandidateList();
            for (Candidate content : contents) {
                emailUtil.addCandidate(content);
            }
        }

        String output = "Content added:\n";
        if (emailUtil.isAreRecipientsCandidates()) {
            output += model.getFilteredContentJobOfferNames();
        } else {
            output += model.getFilteredCandidateNames();
        }
        output += EmailRecipientsSelectCommand.MESSAGE_USAGE;
        return new CommandResult(output);
    }
}
