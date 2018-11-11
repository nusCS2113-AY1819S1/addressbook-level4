package seedu.recruit.logic.commands.emailcommand;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import seedu.recruit.logic.CommandHistory;
import seedu.recruit.logic.commands.CommandResult;
import seedu.recruit.model.Model;
import seedu.recruit.model.ModelManager;
import seedu.recruit.model.UserPrefs;

class EmailInitialiseCommandTest {

    private Model model = new ModelManager();
    private CommandHistory commandHistory = new CommandHistory();
    private UserPrefs userPrefs = new UserPrefs();

    @Test
    public void execute_initialiseCommand_success() {
        String expectedMessage = EmailRecipientsCommand.MESSAGE_USAGE;
        CommandResult commandResult = new EmailInitialiseCommand().execute(model, commandHistory, userPrefs);
        assertEquals(commandResult.feedbackToUser, expectedMessage);
        assertEquals(model.getEmailUtil().getCandidates().size(), 0);
        assertEquals(model.getEmailUtil().getJobOffers().size(), 0);
        assertFalse(model.getEmailUtil().getHasRecipientsAdded());
    }
}
