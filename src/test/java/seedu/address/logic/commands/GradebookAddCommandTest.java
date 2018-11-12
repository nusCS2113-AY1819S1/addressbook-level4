package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.commands.GradebookAddCommand.MESSAGE_ADD_SUCCESS;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ModelManager;
import seedu.address.model.StorageController;
import seedu.address.model.gradebook.Gradebook;
import seedu.address.model.gradebook.GradebookManager;

/**
 * Contains tests for GradebookAddCommand.
 */
public class GradebookAddCommandTest {
    private static GradebookManager gradebookManager = new GradebookManager();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() {
        StorageController.enterTestMode();
        gradebookManager.clearGradebook();
        gradebookManager.saveGradebookList();
    }

    @Test
    public void constructor_nullGradebook_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new GradebookAddCommand(null);
    }

    @Test
    public void execute_gradebookAddWithoutOptionalParam_success() throws CommandException {
        String moduleCode = "CS2113";
        String gradebookComponentName = "Practical Exam";
        int expectedSize = 1;
        String expectedMessage = MESSAGE_ADD_SUCCESS + "%1$s\n";

        Gradebook gradebook = new Gradebook(moduleCode, gradebookComponentName);
        GradebookAddCommand gradebookAddCommand = new GradebookAddCommand(gradebook);
        CommandResult result = gradebookAddCommand.execute(new ModelManager(), new CommandHistory());

        assertEquals(String.format(expectedMessage, expectedSize), result.feedbackToUser);
    }

    @Test
    public void execute_gradebookAddWithOptionalParams_success() throws CommandException {
        String moduleCode = "CS2113";
        String gradebookComponentName = "Practical Exam";
        int maxMarks = 20;
        int weightage = 10;
        int expectedSize = 1;
        String expectedMessage = MESSAGE_ADD_SUCCESS + "%1$s\n";

        Gradebook gradebook = new Gradebook(moduleCode, gradebookComponentName, maxMarks, weightage);
        GradebookAddCommand gradebookAddCommand = new GradebookAddCommand(gradebook);
        CommandResult result = gradebookAddCommand.execute(new ModelManager(), new CommandHistory());

        assertEquals(String.format(expectedMessage, expectedSize), result.feedbackToUser);
    }

    @AfterClass
    public static void tearDown() {
        gradebookManager.clearGradebook();
        gradebookManager.saveGradebookList();
    }
}
