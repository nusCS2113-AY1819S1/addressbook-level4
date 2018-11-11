package seedu.recruit.logic.commands.emailcommand;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import seedu.recruit.commons.core.EventsCenter;
import seedu.recruit.commons.events.ui.ShowEmailPreviewEvent;
import seedu.recruit.commons.util.EmailUtil;
import seedu.recruit.logic.CommandHistory;
import seedu.recruit.logic.commands.CommandResult;
import seedu.recruit.model.Model;
import seedu.recruit.model.UserPrefs;
import seedu.recruit.model.company.Company;
import seedu.recruit.model.joboffer.JobOffer;

/**
 * This class handles the preview sub command of the send phase
 */
public class EmailSendPreviewCommand extends EmailSendCommand {

    @Override
    public CommandResult execute(Model model, CommandHistory history, UserPrefs userPrefs) {
        EmailUtil emailUtil = model.getEmailUtil();
        updateRecipientsAndContents(emailUtil);
        StringBuilder emailPreview = new StringBuilder();

        //Generating recipients
        Set<String> recipientEmails = new HashSet<>();
        generateRecipients(recipientEmails, model, emailUtil);

        //Generate subject
        String subject = generateSubject(emailUtil);

        //Generate content (bodyText)
        StringBuilder bodyText = new StringBuilder();
        if (emailUtil.getAreRecipientsCandidates()) {
            bodyText.append(generateContentWhenRecipientsAreCandidates(emailUtil));
            emailPreview.append("To: " + recipientEmails.toString() + "\n");
            emailPreview.append("Subject: " + subject + "\n");
            emailPreview.append("Contents of the email:\n\n" + bodyText);
        } else {
            for (String email : recipientEmails) {
                bodyText.append(generateJobOfferCompanyPairs(model, emailUtil, email));
            }
            emailPreview.append(bodyText);
            emailPreview.append("Subject: " + subject + "\n");
            emailPreview.append("Contents of the email:\n" + generateCandidateContentDetails());
        }

        EventsCenter.getInstance().post(new ShowEmailPreviewEvent(emailPreview.toString()));
        return new CommandResult(EMAIL_SEND_SHOWING_PREVIEW_MESSAGE + MESSAGE_USAGE);
    }

    /**
     * Generates body text of the email.
     * @param emailUtil
     * @return bodytext String
     */
    @SuppressWarnings("Duplicates")
    public String generateJobOfferCompanyPairs(Model model, EmailUtil emailUtil, String email) {
        StringBuilder bodyText = new StringBuilder();
        ArrayList<String> jobNames = new ArrayList<>();
        Company actualCompany = null;
        for (JobOffer jobOfferRecipient : jobOfferRecipients) {
            int index = model.getCompanyIndexFromName(jobOfferRecipient.getCompanyName());
            //Company not found in CompanyBook, prevent null pointer exception
            if (index == -1) {
                continue;
            }
            Company company = model.getCompanyFromIndex(index);
            if (company.getEmail().toString().equals(email)) {
                jobNames.add(jobOfferRecipient.getJob().toString());
                actualCompany = company;
            }
        }

        if (actualCompany != null) {
            bodyText.append("To: ").append(actualCompany.getCompanyName().toString()).append("(")
                    .append(actualCompany.getEmail().toString()).append(")").append(" regarding job offers: ")
                    .append(jobNames.toString()).append('\n');
        }
        return bodyText.toString();
    }
}
