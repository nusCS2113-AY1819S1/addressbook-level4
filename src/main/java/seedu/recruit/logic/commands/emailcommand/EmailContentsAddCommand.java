package seedu.recruit.logic.commands.emailcommand;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;

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
public class EmailContentsAddCommand extends EmailContentsCommand {

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        EmailUtil emailUtil = model.getEmailUtil();
        ArrayList<Candidate> duplicateCandidates = new ArrayList<>();
        ArrayList<JobOffer> duplicateJobOffers = new ArrayList<>();
        ArrayList<Candidate> addedCandidates = new ArrayList<>();
        ArrayList<JobOffer> addedJobOffers = new ArrayList<>();

        //check if objects being added are the same as the initial added objects
        if (emailUtil.isAreRecipientsCandidates()) {
            ObservableList<JobOffer> contents = model.getFilteredCompanyJobList();
            for (JobOffer content : contents) {
                if (!emailUtil.addJobOffer(content)) {
                    duplicateJobOffers.add(content);
                } else {
                    addedJobOffers.add(content);
                }
            }
        } else {
            ObservableList<Candidate> contents = model.getFilteredCandidateList();
            for (Candidate content : contents) {
                if (!emailUtil.addCandidate(content)) {
                    duplicateCandidates.add(content);
                } else {
                    addedCandidates.add(content);
                }
            }
        }

        //Generate duplicate string (if any)
        boolean hasDuplicates = false;
        StringBuilder duplicates = new StringBuilder(
                "Unable to add the following because it already has been added before:\n");
        if (duplicateCandidates.size() != 0 || duplicateJobOffers.size() != 0) {
            if (!emailUtil.isAreRecipientsCandidates()) {
                for (Candidate duplicateCandidate : duplicateCandidates) {
                    duplicates.append(duplicateCandidate.getName().toString());
                    duplicates.append("\n");
                }
            } else {
                for (JobOffer duplicateJobOffer : duplicateJobOffers) {
                    duplicates.append(emailUtil.getContentJobOfferName(duplicateJobOffer));
                    duplicates.append("\n");
                }
            }
            hasDuplicates = true;
        }

        //Generate recipients string
        StringBuilder contents = new StringBuilder("Contents added:\n");
        if (emailUtil.isAreRecipientsCandidates()) {
            for (JobOffer addedJobOffer : addedJobOffers) {
                contents.append(emailUtil.getContentJobOfferName(addedJobOffer));
                contents.append("\n");
            }
        } else {
            for (Candidate addedCandidate : addedCandidates) {
                contents.append(addedCandidate.getName().toString());
                contents.append("\n");
            }
        }

        //Generate output string
        StringBuilder output = new StringBuilder();

        //if there are duplicates, add the duplicate string in
        if (hasDuplicates) {
            output.append(duplicates);
        }

        //only include recipients string if it's not empty
        if (!contents.toString().equals("Contents added:\n")) {
            output.append(contents);
        }

        output.append(EmailRecipientsCommand.MESSAGE_USAGE);
        return new CommandResult(output.toString());
    }
}
