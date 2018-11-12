package seedu.address.logic.commands;

import static seedu.address.logic.commands.ClassListCommand.MESSAGE_SUCCESS;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.Test;

import seedu.address.logic.CommandHistory;

/**
 * Provides a test for the class list command
 */
public class ClassListCommandTest {

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_classroomListSuccessful() {
        assertCommandSuccess(new ClassListCommand(), commandHistory, MESSAGE_SUCCESS + "\n");
    }
}
