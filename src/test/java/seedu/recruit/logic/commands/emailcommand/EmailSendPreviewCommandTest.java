package seedu.recruit.logic.commands.emailcommand;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.recruit.logic.commands.emailcommand.EmailSendCommand.EMAIL_SEND_SHOWING_PREVIEW_MESSAGE;

import org.junit.jupiter.api.Test;

import seedu.recruit.logic.CommandHistory;
import seedu.recruit.model.Model;
import seedu.recruit.model.ModelManager;
import seedu.recruit.model.UserPrefs;

class EmailSendPreviewCommandTest {

    private CommandHistory commandHistory = new CommandHistory();
    private UserPrefs userPrefs = new UserPrefs();
    private Model model = new ModelManager();
    private EmailSendPreviewCommand command = new EmailSendPreviewCommand();

    @Test
    void execute_emailSendPreview() {
        assertEquals(EMAIL_SEND_SHOWING_PREVIEW_MESSAGE
                + EmailSendCommand.MESSAGE_USAGE, command.execute(model, commandHistory, userPrefs).feedbackToUser);
    }
}
