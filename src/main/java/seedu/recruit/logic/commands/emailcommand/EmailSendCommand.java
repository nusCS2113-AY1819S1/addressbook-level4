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
import seedu.recruit.model.candidate.Candidate;
import seedu.recruit.model.company.Company;
import seedu.recruit.model.joboffer.JobOffer;

/**
 * 4th step of the email command: send the email.
 */
public class EmailSendCommand extends Command {
    public static final String COMMAND_WORD = "send";
    public static final String MESSAGE_USAGE = "Type \"send\" to send the message\n"
            + "Type \"back\" to go back to select contents command.\n"
            + "Type \"cancel\" to cancel the email command.";
    public static final String COMMAND_LOGIC_STATE = "EmailSend";
    public static final String EMAIL_SUCCESS = "Successfully sent the email!";
    public static final String EMAIL_FAILURE = "Failed to send the email!";

    @Override
    public CommandResult execute(Model model, CommandHistory history)
            throws ParseException, IOException, GeneralSecurityException {
        throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
    }

    /**
     * Generates a hashset of emails
     * @param recipientEmails hashset of emails
     * @param model
     * @param emailUtil
     * @param recipients
     * @param contents
     */
    public void generateRecipients(Set<String> recipientEmails, Model model, EmailUtil emailUtil,
                                    ArrayList<?> recipients, ArrayList<?> contents) {
        if (emailUtil.isAreRecipientsCandidates()) {
            //recipients are candidates
            for (Object recipient : recipients) {
                Candidate candidate = (Candidate) recipient;
                recipientEmails.add(candidate.getEmail().toString());
            }
        } else {
            //recipients are companies
            for (Object recipient : recipients) {
                JobOffer jobOffer = (JobOffer) recipient;

                int index = model.getCompanyIndexFromName(jobOffer.getCompanyName());
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
     * @param model
     * @param emailUtil
     * @param recipients
     * @param contents
     * @return bodytext String
     */
    public String generateContent(Model model, EmailUtil emailUtil,
                                   ArrayList<?> recipients, ArrayList<?> contents) {
        String bodyText;
        if (emailUtil.isAreRecipientsCandidates()) {
            bodyText = emailUtil.getEmailSettings().getBodyTextCandidateAsRecipient();
            //contents are companies
            for (Object content : contents) {
                JobOffer jobOffer = (JobOffer) content;
                bodyText = bodyText + '\n' + "Company: " + jobOffer.getCompanyName().toString();
                bodyText = bodyText + '\n' + "Job: " + jobOffer.getJob().toString();
                bodyText = bodyText + '\n' + "Salary offered: " + jobOffer.getSalary().toString();
                bodyText += '\n';
            }
        } else {
            ArrayList<String> jobNames = new ArrayList<>();
            for (Object recipient : recipients) {
                JobOffer jobOffer = (JobOffer) recipient;
                jobNames.add(jobOffer.getJob().toString());
            }

            bodyText = emailUtil.getEmailSettings().getBodyTextCompanyAsRecipient()
                    + jobNames.toString() + '\n';
            //contents are candidates
            for (Object content : contents) {
                Candidate candidate = (Candidate) content;
                bodyText = bodyText + '\n' + "Name: " + candidate.getName().toString();
                bodyText = bodyText + '\n' + "Age: " + candidate.getAge().toString();
                bodyText = bodyText + '\n' + "Education: " + candidate.getEducation().toString();
                bodyText = bodyText + '\n' + "Email: " + candidate.getEmail().toString();
                bodyText = bodyText + '\n' + "Phone Number: " + candidate.getPhone().toString();
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
