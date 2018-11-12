package seedu.recruit.logic.commands.emailcommand;

import org.junit.Ignore;
import org.junit.Test;

import seedu.recruit.logic.CommandHistory;
import seedu.recruit.logic.commands.CommandResult;
import seedu.recruit.model.Model;
import seedu.recruit.model.ModelManager;
import seedu.recruit.model.UserPrefs;

@Ignore("Can't really test this one")
public class EmailSendSendCommandTest {

    private CommandHistory commandHistory = new CommandHistory();
    private UserPrefs userPrefs = new UserPrefs();
    private Model model = new ModelManager();
    private EmailSendSendCommand command = new EmailSendSendCommand();

    @Test
    public void execute_sendCommand() {
        CommandResult result = command.execute(model, commandHistory, userPrefs);
    }

}
