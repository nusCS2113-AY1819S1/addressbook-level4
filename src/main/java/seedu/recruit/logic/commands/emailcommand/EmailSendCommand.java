package seedu.recruit.logic.commands.emailcommand;

import static seedu.recruit.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Set;

import seedu.recruit.commons.util.EmailUtil;
import seedu.recruit.logic.CommandHistory;
import seedu.recruit.logic.commands.Command;
import seedu.recruit.logic.commands.CommandResult;
import seedu.recruit.logic.parser.exceptions.ParseException;
import seedu.recruit.model.Model;
import seedu.recruit.model.UserPrefs;
import seedu.recruit.model.candidate.Candidate;
import seedu.recruit.model.company.Company;
import seedu.recruit.model.joboffer.JobOffer;

/**
 * 4th step of the email command: send the email.
 */
public class EmailSendCommand extends Command {
    public static final String COMMAND_WORD = "send";
    public static final String MESSAGE_USAGE = "Type \"send\" to send the message\n"
            + "Type \"preview\" to preview the email.\n"
            + "Type \"back\" to go back to select contents command.\n"
            + "Type \"cancel\" to cancel the email command.";
    public static final String COMMAND_LOGIC_STATE = "EmailSend";
    public static final String EMAIL_SUCCESS = "Successfully sent the email!";
    public static final String EMAIL_FAILURE = "Failed to send the email!";

    private static ArrayList<Candidate> candidateRecipients;
    private static ArrayList<JobOffer> jobOfferRecipients;
    private static ArrayList<Candidate> candidateContents;
    private static ArrayList<JobOffer> jobOfferContents;

    @Override
    public CommandResult execute(Model model, CommandHistory history, UserPrefs userPrefs)
            throws ParseException, IOException, GeneralSecurityException {
        throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
    }

    /**
     * Updates the recipients and contents array list in this class from emailUtil
     * @param emailUtil
     */
    protected void updateRecipientsAndContents(EmailUtil emailUtil) {
        if (emailUtil.isAreRecipientsCandidates()) {
            candidateRecipients = new ArrayList<>(emailUtil.getCandidates());
            jobOfferContents = new ArrayList<>(emailUtil.getJobOffers());
        } else {
            jobOfferRecipients = new ArrayList<>(emailUtil.getJobOffers());
            candidateContents = new ArrayList<>(emailUtil.getCandidates());
        }
    }

    /**
     * Resets the array lists in this class, to be used when email has been sent or cancelled
     */
    public static void resetRecipientsAndContents() {
        candidateRecipients = new ArrayList<>();
        jobOfferRecipients = new ArrayList<>();
        candidateContents = new ArrayList<>();
        jobOfferContents = new ArrayList<>();
    }

    /**
     * Generates a hashset of emails
     * @param recipientEmails hashset of emails
     * @param model
     * @param emailUtil
     */
    public void generateRecipients(Set<String> recipientEmails, Model model, EmailUtil emailUtil) {
        if (emailUtil.isAreRecipientsCandidates()) {
            //recipients are candidates
            for (Candidate candidateRecipient : candidateRecipients) {
                recipientEmails.add(candidateRecipient.getEmail().toString());
            }
        } else {
            //recipients are companies
            for (JobOffer jobOfferRecipient : jobOfferRecipients) {
                int index = model.getCompanyIndexFromName(jobOfferRecipient.getCompanyName());
                //Company not found in CompanyBook
                if (index == -1) {
                    continue;
                }

                Company company = model.getCompanyFromIndex(index);
                recipientEmails.add(company.getEmail().toString());
            }
        }
    }

    /**
     * Generates bodytext of the email.
     * @param emailUtil
     * @return bodytext String
     */
    public String generateContent(EmailUtil emailUtil) {
        String bodyText;
        if (emailUtil.isAreRecipientsCandidates()) {
            bodyText = emailUtil.getEmailSettings().getBodyTextCandidateAsRecipient();
            //contents are companies
            for (JobOffer jobOfferContent : jobOfferContents) {
                bodyText = bodyText + '\n' + "Company: " + jobOfferContent.getCompanyName().toString();
                bodyText = bodyText + '\n' + "Job: " + jobOfferContent.getJob().toString();
                bodyText = bodyText + '\n' + "Salary offered: " + jobOfferContent.getSalary().toString();
                bodyText += '\n';
            }
        } else {
            ArrayList<String> jobNames = new ArrayList<>();
            for (JobOffer jobOfferRecipient : jobOfferRecipients) {
                jobNames.add(jobOfferRecipient.getJob().toString());
            }

            bodyText = emailUtil.getEmailSettings().getBodyTextCompanyAsRecipient()
                    + jobNames.toString() + '\n';
            //contents are candidates
            for (Candidate candidateContent : candidateContents) {
                bodyText = bodyText + '\n' + "Name: " + candidateContent.getName().toString();
                bodyText = bodyText + '\n' + "Age: " + candidateContent.getAge().toString();
                bodyText = bodyText + '\n' + "Education: " + candidateContent.getEducation().toString();
                bodyText = bodyText + '\n' + "Email: " + candidateContent.getEmail().toString();
                bodyText = bodyText + '\n' + "Phone Number: " + candidateContent.getPhone().toString();
                bodyText += '\n';
            }
        }
        return bodyText;
    }

    /**
     * Function to generate a subject for email
     * @param emailUtil
     * @return String subject
     */
    public String generateSubject(EmailUtil emailUtil) {
        String subject;
        if (emailUtil.isAreRecipientsCandidates()) {
            subject = emailUtil.getEmailSettings().getSubjectCandidateAsRecipient();
        } else {
            subject = emailUtil.getEmailSettings().getSubjectCompanyAsRecipient();
        }
        return subject;
    }
}
