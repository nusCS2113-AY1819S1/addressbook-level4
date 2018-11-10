package seedu.recruit.logic.commands.emailcommand;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;

import javafx.collections.ObservableList;

import seedu.recruit.commons.util.EmailUtil;
import seedu.recruit.logic.CommandHistory;
import seedu.recruit.logic.commands.CommandResult;
import seedu.recruit.model.Model;
import seedu.recruit.model.UserPrefs;
import seedu.recruit.model.candidate.Candidate;
import seedu.recruit.model.joboffer.JobOffer;
import seedu.recruit.ui.MainWindow;

/**
 * This class handles the add sub command for email recipients phase
 */
public class EmailRecipientsAddCommand extends EmailRecipientsCommand {

    private ArrayList<Candidate> duplicateCandidates = new ArrayList<>();
    private ArrayList<JobOffer> duplicateJobOffers = new ArrayList<>();
    private ArrayList<Candidate> addedCandidates = new ArrayList<>();
    private ArrayList<JobOffer> addedJobOffers = new ArrayList<>();

    @Override
    public CommandResult execute(Model model, CommandHistory history, UserPrefs userPrefs) {
        requireNonNull(model);
        EmailUtil emailUtil = model.getEmailUtil();

        //Check if there are already recipients added
        if (!emailUtil.isHasRecipientsAdded()) {
            if (MainWindow.getDisplayedBook().equals("candidateBook")) {
                if (addCandidates(model, emailUtil)) {
                    emailUtil.setAreRecipientsCandidates(true);
                }
            } else {
                if (addJobOffers(model, emailUtil)) {
                    emailUtil.setAreRecipientsCandidates(false);
                }
            }
            emailUtil.setHasRecipientsAdded(true);
        //else check if objects being added are the same as the initial added objects
        } else {
            if (emailUtil.isAreRecipientsCandidates()) {
                if (MainWindow.getDisplayedBook().equals("candidateBook")) {
                    addCandidates(model, emailUtil);
                } else {
                    return new CommandResult(ADD_RECIPIENTS_ERROR_ONLY_CANDIDATES + MESSAGE_USAGE);
                }
            } else {
                if (MainWindow.getDisplayedBook().equals("companyBook")) {
                    addJobOffers(model, emailUtil);
                } else {
                    return new CommandResult(ADD_RECIPIENTS_ERROR_ONLY_JOB_OFFERS + MESSAGE_USAGE);
                }
            }
        }

        //Generate duplicate string (if any)
        StringBuilder duplicates = new StringBuilder(ADD_RECIPIENTS_DUPLICATE_MESSAGE);
        boolean hasDuplicates = generateDuplicate(emailUtil, duplicates);

        //Generate added recipients string
        StringBuilder recipients = new StringBuilder(ADD_RECIPIENTS_RECIPIENTS_ADDED);
        generateRecipients(emailUtil, recipients);

        //Check if both recipients string and duplicate string is empty
        if (duplicates.toString().equals(ADD_RECIPIENTS_DUPLICATE_MESSAGE)
            && recipients.toString().equals(ADD_RECIPIENTS_RECIPIENTS_ADDED)) {
            return new CommandResult(ADD_RECIPIENTS_NOTHING_SELECTED + MESSAGE_USAGE);
        }

        //Generate output string
        StringBuilder output = new StringBuilder();
        generateOutput(hasDuplicates, output, duplicates, recipients);
        return new CommandResult(output.toString());
    }

    /**
     * generates output string
     * @param hasDuplicates
     * @param output
     * @param duplicates
     * @param recipients
     */
    private void generateOutput(boolean hasDuplicates,
                                StringBuilder output,
                                StringBuilder duplicates,
                                StringBuilder recipients) {
        //if there are duplicates, add the duplicate string in
        if (hasDuplicates) {
            output.append(duplicates);
        }
        //only include recipients string if it's not empty
        if (!recipients.toString().equals("Recipients added:\n")) {
            output.append(recipients);
        }
        output.append(EmailRecipientsCommand.MESSAGE_USAGE);
    }

    /**
     * generates recipients string
     * @param emailUtil
     * @param recipients
     */
    @SuppressWarnings("Duplicates")
    private void generateRecipients(EmailUtil emailUtil, StringBuilder recipients) {
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
    }

    /**
     * generates duplicate string
     * @param emailUtil
     * @param duplicates
     * @return boolean value if there is duplicates
     */
    @SuppressWarnings("Duplicates")
    private boolean generateDuplicate(EmailUtil emailUtil, StringBuilder duplicates) {
        boolean hasDuplicates = false;
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
        return hasDuplicates;
    }

    /**
     * adds candidates into the candidates arrays
     * @param model
     * @param emailUtil
     * @return boolean if there was anything present getFilteredCandidateList()
     */
    private boolean addCandidates(Model model, EmailUtil emailUtil) {
        ObservableList<Candidate> recipients = model.getFilteredCandidateList();
        if (recipients.size() == 0) {
            return false;
        }
        for (Candidate recipient : recipients) {
            //if added successfully into linkedhashset, means it was not there.
            //if not added successfully then object already exists.
            if (!emailUtil.addCandidate(recipient)) {
                duplicateCandidates.add(recipient);
            } else {
                addedCandidates.add(recipient);
            }
        }
        return true;
    }

    /**
     * adds job offers into the job offers arrays
     * @param model
     * @param emailUtil
     * @return boolean if there was anything present getFilteredCandidateList()
     */
    private boolean addJobOffers(Model model, EmailUtil emailUtil) {
        ObservableList<JobOffer> recipients = model.getFilteredCompanyJobList();
        if (recipients.size() == 0) {
            return false;
        }
        for (JobOffer recipient : recipients) {
            //if added successfully into linkedhashset, means it was not there.
            //if not added successfully then object already exists.
            if (!emailUtil.addJobOffer(recipient)) {
                duplicateJobOffers.add(recipient);
            } else {
                addedJobOffers.add(recipient);
            }
        }
        return true;
    }
}
