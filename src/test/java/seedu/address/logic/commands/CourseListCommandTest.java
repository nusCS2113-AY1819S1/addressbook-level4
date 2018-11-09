package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CourseListCommand.MESSAGE_SUCCESS;

import org.junit.Test;

import seedu.address.logic.CommandHistory;

import seedu.address.model.StorageController;

/**
 * This is a test class for the CourseListCommand.
 */
public class CourseListCommandTest {

    @Test
    public void execute_addSuccessful() {

        StorageController.enterTestMode();

        assertCommandSuccess(new CourseListCommand(), new CommandHistory(), MESSAGE_SUCCESS);

    }
}
