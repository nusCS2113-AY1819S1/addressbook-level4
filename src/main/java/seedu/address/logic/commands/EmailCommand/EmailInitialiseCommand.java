package seedu.address.logic.commands.EmailCommand;

import java.io.IOException;
import java.security.GeneralSecurityException;

import javafx.collections.ObservableList;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import seedu.address.commons.core.Email;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.RecruitBookEntries;

/**
 * Email Command (Work in progress)
 */
public class EmailInitialiseCommand extends Command {

    public static final String COMMAND_WORD = "email";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": In Progress";
    private static final String EMAIL_SUCCESS = "Email(s) sent successfully!";
    private static final String EMAIL_FAILURE = "Email(s) not successfully sent!";

    private static ObservableList<?extends RecruitBookEntries> recipients;
    private static ObservableList<?extends RecruitBookEntries> contents;

    public ObservableList<?extends RecruitBookEntries> getRecipients() {
        return recipients;
    }

    public ObservableList<?extends RecruitBookEntries> getContents() {
        return contents;
    }

    public void setRecipients(Model model, ObservableList<?extends RecruitBookEntries> recipients) {
        this.recipients = new ObservableList<?extends RecruitBookEntries>(recipients);
    }

    public void setContents(Model model, ObservableList<?extends RecruitBookEntries> contents) {
        this.contents = contents;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws GeneralSecurityException {
        MimeMessage email;
        String result;
        recipients = model.getFilteredCandidateList();
        try {
            email = Email.createEmail("cs2113f094@gmail.com", "cs2113f094@gmail.com",
                    "testing123", "Hello, I am testing!");
            Email.sendMessage(Email.init(), "me", email);
            result = EMAIL_SUCCESS;

        } catch (MessagingException | IOException e) {
            e.printStackTrace();
            result = EMAIL_FAILURE;
        }

        return new CommandResult(result);
    }

}
