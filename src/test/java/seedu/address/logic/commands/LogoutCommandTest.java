package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;

public class LogoutCommandTest {

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void executeSuccessfulLogout() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();
        expectedModel.clearUser();

        assertCommandSuccess(new LogoutCommand(), model, commandHistory, LogoutCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
