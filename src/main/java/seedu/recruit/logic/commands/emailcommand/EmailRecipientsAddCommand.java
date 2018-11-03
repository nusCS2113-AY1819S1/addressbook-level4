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
import seedu.recruit.ui.MainWindow;

/**
 * This class handles the add sub command for email recipients phase
 */
public class EmailRecipientsAddCommand extends EmailRecipientsSelectCommand {

    @Override
    @SuppressWarnings("Duplicates")
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        EmailUtil emailUtil = model.getEmailUtil();
        ArrayList<Candidate> duplicateCandidates = new ArrayList<>();
        ArrayList<JobOffer> duplicateJobOffers = new ArrayList<>();
        ArrayList<Candidate> addedCandidates = new ArrayList<>();
        ArrayList<JobOffer> addedJobOffers = new ArrayList<>();

        //Check if there are already recipients added
        if (!emailUtil.isHasRecipientsAdded()) {
            if (MainWindow.getDisplayedBook().equals("candidateBook")) {
                ObservableList<Candidate> recipients = model.getFilteredCandidateList();
                for (Candidate recipient : recipients) {
                    emailUtil.addCandidate(recipient);
                    addedCandidates.add(recipient);
                }
                emailUtil.setAreRecipientsCandidates(true);
            } else {
                ObservableList<JobOffer> recipients = model.getFilteredCompanyJobList();
                for (JobOffer recipient : recipients) {
                    emailUtil.addJobOffer(recipient);
                    addedJobOffers.add(recipient);
                }
                emailUtil.setAreRecipientsCandidates(false);
            }
            emailUtil.setHasRecipientsAdded(true);
        //else check if objects being added are the same as the initial added objects
        } else {
            if (emailUtil.isAreRecipientsCandidates()) {
                if (MainWindow.getDisplayedBook().equals("candidateBook")) {
                    ObservableList<Candidate> recipients = model.getFilteredCandidateList();
                    for (Candidate recipient : recipients) {
                        //if added successfully into linkedhashset, means it was not there.
                        //if not added successfully then object already exists.
                        if (!emailUtil.addCandidate(recipient)) {
                            duplicateCandidates.add(recipient);
                        } else {
                            addedCandidates.add(recipient);
                        }
                    }
                } else {
                    return new CommandResult("ERROR: You can only add candidates!");
                }
            } else {
                if (MainWindow.getDisplayedBook().equals("companyBook")) {
                    ObservableList<JobOffer> recipients = model.getFilteredCompanyJobList();
                    for (JobOffer recipient : recipients) {
                        //if added successfully into linkedhashset, means it was not there.
                        //if not added successfully then object already exists.
                        if (!emailUtil.addJobOffer(recipient)) {
                            duplicateJobOffers.add(recipient);
                        } else {
                            addedJobOffers.add(recipient);
                        }
                    }
                } else {
                    return new CommandResult("ERROR: You can only add job offers!\n" + MESSAGE_USAGE);
                }
            }
        }

        //Generate duplicate string (if any)
        boolean hasDuplicates = false;
        StringBuilder duplicates = new StringBuilder(
                "Unable to add the following because it already has been added before:\n");
        if (duplicateCandidates.size() != 0 || duplicateJobOffers.size() != 0) {
            if (emailUtil.isAreRecipientsCandidates()) {
                for (Candidate duplicateCandidate : duplicateCandidates) {
                    duplicates.append(duplicateCandidate.getName().toString());
                    duplicates.append("\n");
                }
            } else {
                for (JobOffer duplicateJobOffer : duplicateJobOffers) {
                    duplicates.append(emailUtil.getRecipientJobOfferName(duplicateJobOffer));
                    duplicates.append("\n");
                }
            }
            hasDuplicates = true;
        }

        //Generate added recipients string
        StringBuilder recipients = new StringBuilder("Recipients added:\n");
        if (emailUtil.isAreRecipientsCandidates()) {
            for (Candidate addedCandidate : addedCandidates) {
                recipients.append(addedCandidate.getName().toString());
                recipients.append("\n");
            }
        } else {
            for (JobOffer addedJobOffer : addedJobOffers) {
                recipients.append(emailUtil.getRecipientJobOfferName(addedJobOffer));
                recipients.append("\n");
            }
        }

        //Generate output string
        StringBuilder output = new StringBuilder();

        if (hasDuplicates) {
            output.append(duplicates);
        }

        if (!recipients.toString().equals("Recipients added:\n")) {
            output.append(recipients);
        }

        output.append(EmailRecipientsSelectCommand.MESSAGE_USAGE);
        return new CommandResult(output.toString());
    }
}
