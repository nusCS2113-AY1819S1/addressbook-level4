package seedu.recruit.logic.commands.emailcommand;

import static java.util.Objects.requireNonNull;

import javafx.collections.ObservableList;
import seedu.recruit.commons.util.EmailUtil;
import seedu.recruit.logic.CommandHistory;
import seedu.recruit.logic.commands.CommandResult;
import seedu.recruit.model.Model;
import seedu.recruit.model.candidate.Candidate;
import seedu.recruit.model.joboffer.JobOffer;

import java.util.ArrayList;

/**
 * This class handles the add sub command for email contents phase
 */
public class EmailContentsAddCommand extends EmailContentsSelectCommand {
    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        EmailUtil emailUtil = model.getEmailUtil();
        ArrayList<Candidate> duplicateCandidates = new ArrayList<>();
        ArrayList<JobOffer> duplicateJobOffers = new ArrayList<>();

        //check if objects being added are the same as the initial added objects
        if (emailUtil.isAreRecipientsCandidates()) {
            ObservableList<JobOffer> contents = model.getFilteredCompanyJobList();
            for (JobOffer content : contents) {
                if(!emailUtil.addJobOffer(content)) {
                    duplicateJobOffers.add(content);
                }
            }
        } else {
            ObservableList<Candidate> contents = model.getFilteredCandidateList();
            for (Candidate content : contents) {
                if(!emailUtil.addCandidate(content)) {
                    duplicateCandidates.add(content);
                }
            }
        }

        //Generate duplicate string (if any)
        boolean hasDuplicates = false;
        String duplicates = "Unable to add the following because it already has been added before:\n";
        if (duplicateCandidates.size() != 0 || duplicateJobOffers.size() != 0) {
            if (!emailUtil.isAreRecipientsCandidates()) {
                for(Candidate duplicateCandidate : duplicateCandidates) {
                    duplicates += duplicateCandidate.getName().toString();
                    duplicates += "\n";
                }
            } else {
                for(JobOffer duplicateJobOffer : duplicateJobOffers) {
                    duplicates += duplicateJobOffer.getJob().toString();
                    duplicates += "\n";
                }
            }
            hasDuplicates = true;
        }

        //Generate recipients string
        String contents = "Contents added:\n";
        if(hasDuplicates) {
            if (!emailUtil.isAreRecipientsCandidates()) {
                contents += model.getFilteredCandidateNames(duplicateCandidates);
            } else {
                contents += model.getFilteredContentJobOfferNames(duplicateJobOffers);
            }
        } else {
            if (!emailUtil.isAreRecipientsCandidates()) {
                contents += model.getFilteredCandidateNames();
            } else {
                contents += model.getFilteredContentJobOfferNames();
            }
        }

        //Generate output string
        String output = "";

        if(hasDuplicates) {
            output += duplicates;
        }

        if(!contents.equals("Contents added:\n")) {
            output += contents;
        }

        output += EmailRecipientsSelectCommand.MESSAGE_USAGE;
        return new CommandResult(output);
    }
}
