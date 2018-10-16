package seedu.recruit.logic.commands.emailcommand;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import javafx.collections.ObservableList;

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
    public static final String MESSAGE_USAGE = "Type \"send\" to send the message!";
    public static final String COMMAND_LOGIC_STATE = "EmailSend";
    public static final String EMAIL_SUCCESS = "Successfully sent the email!";
    public static final String EMAIL_FAILURE = "Failed to send the email!";

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws IOException, GeneralSecurityException {
        EmailUtil emailUtil = model.getEmailUtil();
        String result;
        ObservableList<?> recipients = emailUtil.getRecipients();
        ObservableList<?> contents = emailUtil.getContents();

        for (Object content : contents) {
            System.out.println(content.toString());
        }

        System.out.println("-----------------------------");

        for (Object recipient : recipients) {
            System.out.println(recipient.toString());
        }

        Set<String> recipientEmails = new HashSet<>();
        if (emailUtil.isAreRecipientsCandidates()) {
            for (Object recipient : recipients) {
                Candidate candidate = (Candidate) recipient;
                recipientEmails.add(candidate.getEmail().toString());
            }
        } else {
            for (Object recipient : recipients) {
                JobOffer jobOffer = (JobOffer) recipient;

                int index = model.getCompanyIndexFromName(jobOffer.getCompanyName());
                //Company not found in CompanyBook
                if(index == -1) {
                    continue;
                }
                
                Company company = model.getCompanyFromIndex(index);
                recipientEmails.add(company.getEmail().toString());
            }
        }

        System.out.println(recipientEmails.toString());

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
