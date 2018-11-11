package seedu.recruit.logic.commands.emailcommand;

import org.junit.jupiter.api.Test;

import seedu.recruit.logic.CommandHistory;
import seedu.recruit.model.Model;
import seedu.recruit.model.ModelManager;
import seedu.recruit.model.UserPrefs;

class EmailRecipientsAddCommandTest {

    private Model model = new ModelManager();
    private CommandHistory commandHistory = new CommandHistory();
    private UserPrefs userPrefs = new UserPrefs();

    @Test

    void execute_emailRecipientsAdd_noRecipients() {
    }
}
