package seedu.address.logic.commands;

import org.junit.jupiter.api.Test;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;

import static org.junit.jupiter.api.Assertions.*;

class ImportCommandTest {
    private Model model = new ModelManager();
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_success() {
        CommandResult result = new ImportCommand().execute(model, commandHistory);
        assertEquals(ImportCommand.MESSAGE_UPDATE, result.feedbackToUser);
    }
}