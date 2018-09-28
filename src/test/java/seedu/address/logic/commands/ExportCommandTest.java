package seedu.address.logic.commands;

import static junit.framework.TestCase.assertEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;

class ExportCommandTest {
    private Model model = new ModelManager();
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_success() {
        Path path = Paths.get("C:\\Users\\USER\\Desktop\\main\\build");
        CommandResult result = new ExportCommand(path).execute(model, commandHistory);
        assertEquals(ExportCommand.MESSAGE_SUCCESS, result.feedbackToUser);
    }

    @Test
    public void execute_empty() {
        CommandResult result = new ExportCommand().execute(model, commandHistory);
        assertEquals(ExportCommand.MESSAGE_USAGE, result.feedbackToUser);
    }
}
