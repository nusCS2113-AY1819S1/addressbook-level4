package seedu.recruit.logic.commands.EmailCommand;

import javafx.collections.ObservableList;
import seedu.recruit.commons.util.EmailUtil;
import seedu.recruit.logic.CommandHistory;
import seedu.recruit.logic.LogicManager;
import seedu.recruit.logic.commands.Command;
import seedu.recruit.logic.commands.CommandResult;
import seedu.recruit.model.Model;
import seedu.recruit.model.candidate.Candidate;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;

/**
 * Finally, send the email.
 */
public class EmailSendCommand extends Command {
    public static final String COMMAND_WORD = "send";
    public static final String MESSAGE_USAGE = "Sending...";
    public static final String COMMAND_LOGIC_STATE = "EmailSend";
    public static final String EMAIL_SUCCESS = "Successfully sent the email!";
    public static final String EMAIL_FAILURE = "Failed to send the email!";

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws IOException, GeneralSecurityException {
        EmailUtil emailUtil = model.getEmailUtil();
        String result;
        ObservableList<?> recipients = emailUtil.getRecipients();
        ObservableList<?> contents = emailUtil.getContents();

        for(Object content : contents) {
            System.out.println(content.toString());
        }

        ArrayList<String> recipientEmails = new ArrayList<>();
        if(emailUtil.isAreRecipientsCandidates()) {
            for (Object recipient : recipients) {
                Candidate candidate = (Candidate)recipient;
                recipientEmails.add(candidate.getEmail().toString());
            }
        } else {
            //next time
        }

        for(String recipientEmail:recipientEmails) {
            System.out.println(recipientEmail);
        }

        //Sending the email
        try {
            MimeMessage mimeMessage = EmailUtil.createEmail(EmailUtil.DEFAULT_FROM, recipientEmails, "hello", "testing");
            EmailUtil.sendMessage(EmailUtil.serviceInit(), EmailUtil.DEFAULT_FROM, mimeMessage);
            result = EMAIL_SUCCESS;
        } catch (MessagingException | GeneralSecurityException e) {
            e.printStackTrace();
            result = EMAIL_FAILURE;
        }
        LogicManager.setLogicState("primary");
        return new CommandResult(result);
    }
}
