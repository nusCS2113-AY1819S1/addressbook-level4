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

/**
 * This class handles the preview sub command of the send phase
 */
public class EmailSendPreviewCommand extends EmailSendCommand {

    public static final String SHOWING_PREVIEW_MESSAGE = "Opened preview.\n" + MESSAGE_USAGE;

    @Override
    @SuppressWarnings("Duplicates")
    public CommandResult execute(Model model, CommandHistory history) {
        EmailUtil emailUtil = model.getEmailUtil();
        String result;
        ArrayList<?> recipients;
        ArrayList<?> contents;

        //Setting recipients and contents based on AreRecipientsCandidates boolean
        if (emailUtil.isAreRecipientsCandidates()) {
            recipients = new ArrayList<>(emailUtil.getCandidates());
            contents = new ArrayList<>(emailUtil.getJobOffers());
        } else {
            recipients = new ArrayList<>(emailUtil.getJobOffers());
            contents = new ArrayList<>(emailUtil.getCandidates());
        }

        //Generating recipients
        Set<String> recipientEmails = new HashSet<>();
        generateRecipients(recipientEmails, model, emailUtil, recipients, contents);

        //Generate content (bodyText)
        String bodyText = generateContent(model, emailUtil, recipients, contents);

        //Generate subject
        String subject = generateSubject(emailUtil);

        StringBuilder emailPreview = new StringBuilder();
        emailPreview.append("To: " + recipientEmails.toString() + "\n");
        emailPreview.append("Subject: " + subject + "\n");
        emailPreview.append("Contents of the email:\n\n" + bodyText);

        EventsCenter.getInstance().post(new ShowEmailPreviewEvent(emailPreview.toString()));
        return new CommandResult(SHOWING_PREVIEW_MESSAGE);
    }
}
