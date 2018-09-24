package seedu.address.logic.commands;

import org.junit.jupiter.api.Test;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;

import static org.junit.jupiter.api.Assertions.*;

class BackUpCommandTest {
    private Model model = new ModelManager();
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_success() {
        CommandResult result = new BackUpCommand().execute(model, commandHistory);
        assertEquals(BackUpCommand.MESSAGE_SUCCESS, result.feedbackToUser);
    }
}