package seedu.recruit.logic.commands.emailcommand;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import seedu.recruit.commons.util.EmailUtil;
import seedu.recruit.logic.CommandHistory;
import seedu.recruit.logic.LogicManager;
import seedu.recruit.logic.commands.Command;
import seedu.recruit.logic.commands.CommandResult;
import seedu.recruit.model.Model;
import seedu.recruit.model.candidate.Candidate;
import seedu.recruit.model.company.Company;
import seedu.recruit.model.joboffer.JobOffer;

/**
 * Finally, send the email.
 */
public class EmailSendCommand extends Command {
    public static final String COMMAND_WORD = "send";
    public static final String MESSAGE_USAGE = "Type \"send\" to send the message\n";
    public static final String COMMAND_LOGIC_STATE = "EmailSend";
    private static final String EMAIL_SUCCESS = "Successfully sent the email!";
    private static final String EMAIL_FAILURE = "Failed to send the email!";

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws IOException, GeneralSecurityException {
        EmailUtil emailUtil = model.getEmailUtil();
        String result;
        ArrayList<?> recipients;
        ArrayList<?> contents;

        //Setting recipients and contents based on AreRecipientsCandidates boolean
        if (emailUtil.isAreRecipientsCandidates()) {
            recipients = emailUtil.getCandidates();
            contents = emailUtil.getJobOffers();
        } else {
            recipients = emailUtil.getJobOffers();
            contents = emailUtil.getCandidates();
        }

        // for testing purposes
        for (Object content : contents) {
            System.out.println(content.toString());
        }

        System.out.println("-----------------------------");

        for (Object recipient : recipients) {
            System.out.println(recipient.toString());
        }

        //Generating recipients
        Set<String> recipientEmails = new HashSet<>();
        generateRecipients(recipientEmails, model, emailUtil, recipients, contents);

        //Generate content (bodyText)
        String bodyText = generateContent(model, emailUtil, recipients, contents);

        //Generate subject
        String subject = generateSubject(emailUtil);

        //Sending the email
        try {
            MimeMessage mimeMessage = EmailUtil.createEmail(EmailUtil.DEFAULT_FROM, recipientEmails, subject, bodyText);
            EmailUtil.sendMessage(EmailUtil.serviceInit(), EmailUtil.DEFAULT_FROM, mimeMessage);
            result = EMAIL_SUCCESS;
        } catch (MessagingException | GeneralSecurityException e) {
            e.printStackTrace();
            result = EMAIL_FAILURE;
        }
        LogicManager.setLogicState("primary");
        return new CommandResult(result);
    }

    /**
     * Generates a hashset of emails
     * @param recipientEmails hashset of emails
     * @param model
     * @param emailUtil
     * @param recipients
     * @param contents
     */
    private void generateRecipients(Set<String> recipientEmails, Model model, EmailUtil emailUtil,
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
    private String generateContent(Model model, EmailUtil emailUtil,
                                   ArrayList<?> recipients, ArrayList<?> contents) {
        String bodyText;
        if (emailUtil.isAreRecipientsCandidates()) {
            bodyText = "Hello candidates! I think you will be interested in these job offer(s)\n";
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

            bodyText = "Hello Sirs/Madams,\nI think you will be interested in these candidates for your job offer: "
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
    private String generateSubject(EmailUtil emailUtil) {
        String subject;
        if (emailUtil.isAreRecipientsCandidates()) {
            subject = "Hot new job offers that you will love!";
        } else {
            subject = "New candidates found for your company!";
        }
        return subject;
    }
}
