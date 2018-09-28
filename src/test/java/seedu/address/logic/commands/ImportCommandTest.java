package seedu.address.logic.commands;

import static junit.framework.TestCase.assertEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;

class ImportCommandTest {
    private Model model = new ModelManager();
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_success() {
        Path path = Paths.get("C:\\Users\\USER\\Desktop\\main\\build");
        CommandResult result = new ImportCommand(path).execute(model, commandHistory);
        assertEquals(ImportCommand.MESSAGE_SUCCESS, result.feedbackToUser);
    }

    @Test
    public void execute_empty() {
        CommandResult result = new ImportCommand().execute(model, commandHistory);
        assertEquals(ImportCommand.MESSAGE_USAGE, result.feedbackToUser);
    }
}
