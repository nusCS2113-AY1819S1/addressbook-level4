package seedu.recruit.logic.commands.emailcommand;

import java.util.HashSet;
import java.util.Set;

import seedu.recruit.commons.core.EventsCenter;
import seedu.recruit.commons.events.ui.ShowEmailPreviewEvent;
import seedu.recruit.commons.util.EmailUtil;
import seedu.recruit.logic.CommandHistory;
import seedu.recruit.logic.commands.CommandResult;
import seedu.recruit.model.Model;
import seedu.recruit.model.UserPrefs;

/**
 * This class handles the preview sub command of the send phase
 */
public class EmailSendPreviewCommand extends EmailSendCommand {

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

        StringBuilder emailPreview = new StringBuilder();
        emailPreview.append("To: " + recipientEmails.toString() + "\n");
        emailPreview.append("Subject: " + subject + "\n");
        emailPreview.append("Contents of the email:\n\n" + bodyText);

        EventsCenter.getInstance().post(new ShowEmailPreviewEvent(emailPreview.toString()));
        return new CommandResult(EMAIL_SEND_SHOWING_PREVIEW_MESSAGE + MESSAGE_USAGE);
    }
}
