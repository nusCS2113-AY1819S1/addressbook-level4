package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static seedu.address.logic.commands.GradebookDeleteCommand.MESSAGE_DELETE_FAIL;
import static seedu.address.logic.commands.GradebookDeleteCommand.MESSAGE_DELETE_SUCCESS;

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
import seedu.address.testutil.GradebookBuilder;

/**
 * Contains tests for GradebookDeleteCommand.
 */
public class GradebookDeleteCommandTest {
    private static GradebookManager gradebookManager = new GradebookManager();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() {
        StorageController.enterTestMode();
        gradebookManager.clearGradebook();
        Gradebook gradebook = new GradebookBuilder().withModuleCode("CS2113").withComponentName("Finals").build();
        gradebookManager.addGradebookComponent(gradebook);
        gradebookManager.saveGradebookList();
    }

    @Test
    public void constructor_nullGradebook_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new GradebookDeleteCommand(null, null);
    }

    @Test
    public void execute_gradebookDelete_success() throws CommandException {
        String moduleCode = "CS2113";
        String gradebookComponentName = "Finals";
        int expectedSize = 0;
        String expectedMessage = MESSAGE_DELETE_SUCCESS + "%1$s\n";

        Gradebook gradebook = new Gradebook(moduleCode, gradebookComponentName);
        GradebookDeleteCommand gradebookDeleteCommand = new GradebookDeleteCommand(
                gradebook.getModuleCode(),
                gradebook.getGradeComponentName());
        CommandResult result = gradebookDeleteCommand.execute(new ModelManager(), new CommandHistory());

        assertEquals(String.format(expectedMessage, expectedSize), result.feedbackToUser);
    }

    @Test
    public void execute_gradebookDeleteInvalidComponent_success() throws CommandException {
        String moduleCode = "CS2113";
        String gradebookComponentName = "Assignment 1";
        String expectedMessage = MESSAGE_DELETE_FAIL;

        Gradebook gradebook = new Gradebook(moduleCode, gradebookComponentName);
        GradebookDeleteCommand gradebookDeleteCommand = new GradebookDeleteCommand(
                gradebook.getModuleCode(),
                gradebook.getGradeComponentName());
        CommandResult result = gradebookDeleteCommand.execute(new ModelManager(), new CommandHistory());

        assertEquals(String.format(expectedMessage), result.feedbackToUser);
    }
}
