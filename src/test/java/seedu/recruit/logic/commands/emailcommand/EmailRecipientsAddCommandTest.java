package seedu.recruit.logic.commands.emailcommand;

import org.junit.Rule;
import org.junit.jupiter.api.Test;

import org.junit.rules.ExpectedException;
import seedu.recruit.logic.CommandHistory;
import seedu.recruit.model.Model;
import seedu.recruit.model.ModelManager;
import seedu.recruit.model.UserPrefs;

public class EmailRecipientsAddCommandTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model = new ModelManager();
    private CommandHistory commandHistory = new CommandHistory();
    private UserPrefs userPrefs = new UserPrefs();

    @Test

    public void execute_emailRecipientsAdd_noRecipients() {
    }
}
