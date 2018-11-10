package seedu.recruit.logic.commands.emailcommand;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import com.google.api.services.gmail.Gmail;

import seedu.recruit.commons.core.EventsCenter;
import seedu.recruit.commons.events.logic.ChangeLogicStateEvent;
import seedu.recruit.commons.util.EmailUtil;
import seedu.recruit.logic.CommandHistory;
import seedu.recruit.logic.commands.CommandResult;
import seedu.recruit.model.Model;
import seedu.recruit.model.UserPrefs;
import seedu.recruit.model.company.Company;
import seedu.recruit.model.joboffer.JobOffer;

/**
 * This class handles the send sub command for email send phase
 */
public class EmailSendSendCommand extends EmailSendCommand {

    @Override
    public CommandResult execute(Model model, CommandHistory history, UserPrefs userPrefs) {
        EmailUtil emailUtil = model.getEmailUtil();
        updateRecipientsAndContents(emailUtil);

        Gmail service;
        try {
            service = serviceInit();
        } catch (IOException | GeneralSecurityException e) {
            e.printStackTrace();
            return new CommandResult(EMAIL_FAILURE);
        }

        //Generating recipients
        Set<String> recipientEmails = new HashSet<>();
        generateRecipients(recipientEmails, model, emailUtil);

        //Generate subject
        String subject = generateSubject(emailUtil);

        //Generate content (bodyText)
        StringBuilder bodyText = new StringBuilder();
        if (emailUtil.isAreRecipientsCandidates()) {
            bodyText.append(generateContentWhenRecipientsAreCandidates(emailUtil));
            //Sending the email
            try {
                MimeMessage mimeMessage = createEmail(DEFAULT_FROM, recipientEmails, subject, bodyText.toString());
                sendMessage(service, DEFAULT_FROM, mimeMessage);
            } catch (MessagingException | IOException e) {
                e.printStackTrace();
                return new CommandResult(EMAIL_FAILURE);
            }
        } else {
            // For each email, find the job offers under it and send to that company
            for (String email : recipientEmails) {
                bodyText = new StringBuilder();
                bodyText.append(generateContentWhenRecipientsAreCompanies(model, emailUtil, email));
                try {
                    MimeMessage mimeMessage = createEmail(DEFAULT_FROM, email, subject, bodyText.toString());
                    sendMessage(service, DEFAULT_FROM, mimeMessage);
                } catch (MessagingException | IOException e) {
                    e.printStackTrace();
                    return new CommandResult(EMAIL_FAILURE);
                }
            }
        }
        resetRecipientsAndContents();
        EventsCenter.getInstance().post(new ChangeLogicStateEvent("primary"));
        return new CommandResult(EMAIL_SUCCESS);

    /**
     * Generate a body text where job offers are paired with its company.
     * @param model
     * @param emailUtil
     * @param email
     * @return
     */
    private String generateContentWhenRecipientsAreCompanies(Model model, EmailUtil emailUtil, String email) {
        ArrayList<String> jobNames = new ArrayList<>();
        StringBuilder bodyText = new StringBuilder();
        for (JobOffer jobOfferRecipient : jobOfferRecipients) {
            int index = model.getCompanyIndexFromName(jobOfferRecipient.getCompanyName());
            //Company not found in CompanyBook, prevent null pointer exception
            if (index == -1) {
                continue;
            }
            Company company = model.getCompanyFromIndex(index);
            if (company.getEmail().toString().equals(email)) {
                jobNames.add(jobOfferRecipient.getJob().toString());
            }
        }
        bodyText.append(emailUtil.getEmailSettings().getBodyTextCompanyAsRecipient())
                .append(": ").append(jobNames.toString()).append('\n');
        bodyText.append(generateCandidateContentDetails());
        return bodyText.toString();
    }
}
