package seedu.recruit.logic.commands.emailcommand;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.HashSet;
import java.util.Set;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import seedu.recruit.commons.util.EmailUtil;
import seedu.recruit.logic.CommandHistory;
import seedu.recruit.logic.LogicManager;
import seedu.recruit.logic.commands.CommandResult;
import seedu.recruit.model.Model;
import seedu.recruit.model.UserPrefs;

/**
 * This class handles the send sub command for email send phase
 */
public class EmailSendSendCommand extends EmailSendCommand {

    @Override
    public CommandResult execute(Model model, CommandHistory history, UserPrefs userPrefs) {
        EmailUtil emailUtil = model.getEmailUtil();
        updateRecipientsAndContents(emailUtil);

        //Generating recipients
        Set<String> recipientEmails = new HashSet<>();
        generateRecipients(recipientEmails, model, emailUtil);

        //Generate content (bodyText)
        String bodyText = generateContent(emailUtil);

        //Generate subject
        String subject = generateSubject(emailUtil);

        //Sending the email
        try {
            MimeMessage mimeMessage = EmailUtil.createEmail(EmailUtil.DEFAULT_FROM, recipientEmails, subject, bodyText);
            EmailUtil.sendMessage(EmailUtil.serviceInit(), EmailUtil.DEFAULT_FROM, mimeMessage);
        } catch (MessagingException | GeneralSecurityException | IOException e) {
            e.printStackTrace();
            return new CommandResult(EMAIL_FAILURE);
        }

        resetRecipientsAndContents();
        LogicManager.setLogicState("primary");
        return new CommandResult(EMAIL_SUCCESS);
    }
}
