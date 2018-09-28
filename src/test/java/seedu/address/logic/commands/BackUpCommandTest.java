package seedu.address.logic.commands;

import static junit.framework.TestCase.assertEquals;

import org.junit.jupiter.api.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;

class BackUpCommandTest {
    private Model model = new ModelManager();
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_success() {
        CommandResult result = new BackUpCommand().execute(model, commandHistory);
        assertEquals(BackUpCommand.MESSAGE_SUCCESS, result.feedbackToUser);
    }
}
