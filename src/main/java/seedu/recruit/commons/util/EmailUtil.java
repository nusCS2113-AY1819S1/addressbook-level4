package seedu.recruit.commons.util;

import java.util.LinkedHashSet;

import seedu.recruit.commons.core.EmailSettings;
import seedu.recruit.model.candidate.Candidate;
import seedu.recruit.model.joboffer.JobOffer;

/**
 * This class contains recipients and contents used by the email command
 */
public class EmailUtil {

    public static final String EMAIL_ADD_COMMAND = "add";
    public static final String EMAIL_NEXT_COMMAND = "next";
    public static final String EMAIL_BACK_COMMAND = "back";
    public static final String EMAIL_SEND_COMMAND = "send";
    public static final String EMAIL_PREVIEW_COMMAND = "preview";

    /**
     * Variables for Email Command
     */
    private static EmailSettings emailSettings;
    private LinkedHashSet<Candidate> candidates;
    private LinkedHashSet<JobOffer> jobOffers;
    private boolean hasRecipientsAdded;
    private boolean areRecipientsCandidates;

    /**
     * Constructor
     */
    public EmailUtil() {
        candidates = new LinkedHashSet<>();
        jobOffers = new LinkedHashSet<>();
        hasRecipientsAdded = false;
    }

    /**
     * Getters and Setters
     */
    public boolean getHasRecipientsAdded() {
        return hasRecipientsAdded;
    }

    public void setHasRecipientsAdded(boolean hasRecipientsAdded) {
        this.hasRecipientsAdded = hasRecipientsAdded;
    }

    public static void setEmailSettings(EmailSettings emailSettings) {
        EmailUtil.emailSettings = emailSettings;
    }

    public LinkedHashSet<Candidate> getCandidates() {
        return candidates;
    }

    public void setCandidates(LinkedHashSet<Candidate> candidates) {
        this.candidates = candidates;
    }

    public LinkedHashSet<JobOffer> getJobOffers() {
        return jobOffers;
    }

    public void setJobOffers(LinkedHashSet<JobOffer> jobOffers) {
        this.jobOffers = jobOffers;
    }

    public boolean getAreRecipientsCandidates() {
        return areRecipientsCandidates;
    }

    public void setAreRecipientsCandidates(boolean areRecipientsCandidates) {
        this.areRecipientsCandidates = areRecipientsCandidates;
    }

    public EmailSettings getEmailSettings() {
        return emailSettings;
    }

    /**
     * Adds candidate to candidates ArrayList
     * @param candidate
     * @return boolean value whether value was added into linkedhashset
     */
    public boolean addCandidate(Candidate candidate) {
        return candidates.add(candidate);
    }

    /**
     * Adds jobOffer to jobOffers ArrayList
     * @param jobOffer
     * @return boolean value whether value was added into linkedhashset
     */
    public boolean addJobOffer(JobOffer jobOffer) {
        return jobOffers.add(jobOffer);
    }

    /**
     * returns a string for job offers as recipients.
     * @param jobOffer
     * @return String of company name regarding: job offer
     */
    public String getRecipientJobOfferName(JobOffer jobOffer) {
        StringBuilder output = new StringBuilder();
        output.append(jobOffer.getCompanyName().toString());
        output.append(" regarding job offer: ");
        output.append(jobOffer.getJob().toString());
        return output.toString();
    }

    /**
     * returns a string for job offers as contents
     * @param jobOffer
     * @return String of job offer at company
     */
    public String getContentJobOfferName(JobOffer jobOffer) {
        StringBuilder output = new StringBuilder();
        output.append(jobOffer.getJob().toString());
        output.append(" at ");
        output.append(jobOffer.getCompanyName().toString());
        return output.toString();
    }
}
