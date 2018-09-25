package seedu.address.logic.commands;

import org.junit.jupiter.api.Test;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;

import static org.junit.jupiter.api.Assertions.*;

class RestoreCommandTest {
    private Model model = new ModelManager();
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_success() {
        CommandResult result = new RestoreCommand().execute(model, commandHistory);
        assertEquals(RestoreCommand.MESSAGE_UPDATE, result.feedbackToUser);
    }
}