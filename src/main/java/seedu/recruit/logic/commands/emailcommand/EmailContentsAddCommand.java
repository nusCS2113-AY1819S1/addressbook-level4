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

/**
 * This class handles the add sub command for email contents phase
 */
public class EmailContentsAddCommand extends EmailContentsCommand {

    private ArrayList<Candidate> duplicateCandidates = new ArrayList<>();
    private ArrayList<JobOffer> duplicateJobOffers = new ArrayList<>();
    private ArrayList<Candidate> addedCandidates = new ArrayList<>();
    private ArrayList<JobOffer> addedJobOffers = new ArrayList<>();

    @Override
    public CommandResult execute(Model model, CommandHistory history, UserPrefs userPrefs) {
        requireNonNull(model);
        EmailUtil emailUtil = model.getEmailUtil();

        //check if objects being added are the same as the initial added objects
        if (emailUtil.isAreRecipientsCandidates()) {
            addJobOffers(model, emailUtil);
        } else {
            addCandidates(model, emailUtil);
        }

        //Generate duplicate string (if any)
        StringBuilder duplicates = new StringBuilder(
                "Unable to add the following because it already has been added before:\n");
        boolean hasDuplicates = generateDuplicate(emailUtil, duplicates);

        //Generate contents string
        StringBuilder contents = new StringBuilder("Contents added:\n");
        generateContents(emailUtil, contents);

        //Check if both recipients string and duplicate string is empty
        if (duplicates.toString().equals("Unable to add the following because it already has been added before:\n")
            && contents.toString().equals("Recipients added:\n")) {
            return new CommandResult("ERROR: Nothing was selected!\n" + MESSAGE_USAGE);
        }

        //Generate output string
        StringBuilder output = new StringBuilder();
        generateOutput(hasDuplicates, output, duplicates, contents);
        return new CommandResult(output.toString());
    }

    /**
     * generates output string
     * @param hasDuplicates
     * @param output
     * @param duplicates
     * @param contents
     */
    private void generateOutput(boolean hasDuplicates,
                                StringBuilder output,
                                StringBuilder duplicates,
                                StringBuilder contents) {
        //if there are duplicates, add the duplicate string in
        if (hasDuplicates) {
            output.append(duplicates);
        }

        //only include recipients string if it's not empty
        if (!contents.toString().equals("Contents added:\n")) {
            output.append(contents);
        }
        output.append(EmailRecipientsCommand.MESSAGE_USAGE);
    }

    /**
     * generates recipients string
     * @param emailUtil
     * @param contents
     */
    @SuppressWarnings("Duplicates")
    private void generateContents(EmailUtil emailUtil, StringBuilder contents) {
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
        return hasDuplicates;
    }

    /**
     * adds candidates into the candidates arrays
     * @param model
     * @param emailUtil
     */
    private void addCandidates(Model model, EmailUtil emailUtil) {
        ObservableList<Candidate> contents = model.getFilteredCandidateList();
        for (Candidate content : contents) {
            //if added successfully into linkedhashset, means it was not there.
            //if not added successfully then object already exists.
            if (!emailUtil.addCandidate(content)) {
                duplicateCandidates.add(content);
            } else {
                addedCandidates.add(content);
            }
        }
    }

    /**
     * adds job offers into the job offers arrays
     * @param model
     * @param emailUtil
     */
    private void addJobOffers(Model model, EmailUtil emailUtil) {
        ObservableList<JobOffer> contents = model.getFilteredCompanyJobList();
        for (JobOffer content : contents) {
            //if added successfully into linkedhashset, means it was not there.
            //if not added successfully then object already exists.
            if (!emailUtil.addJobOffer(content)) {
                duplicateJobOffers.add(content);
            } else {
                addedJobOffers.add(content);
            }
        }
    }
}
