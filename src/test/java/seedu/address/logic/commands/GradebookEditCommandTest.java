package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.commands.GradebookEditCommand.MESSAGE_EDIT_GRADEBOOK_SUCCESS;
import static seedu.address.logic.commands.GradebookEditCommand.MESSAGE_FIND_FAIL;

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
 * Contains tests for GradebookEditCommand.
 */
public class GradebookEditCommandTest {
    private static GradebookManager gradebookManager = new GradebookManager();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() {
        StorageController.enterTestMode();
        gradebookManager.clearGradebook();
        Gradebook gradebook = new GradebookBuilder()
                .withModuleCode("CS2113")
                .withComponentName("Finals")
                .withMaxmarks(20)
                .withWeightage(10)
                .build();
        gradebookManager.addGradebookComponent(gradebook);
        gradebookManager.saveGradebookList();
    }

    @Test
    public void constructor_nullGradebook_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new GradebookEditCommand(null);
    }

    @Test
    public void execute_gradebookEdit_success() throws CommandException {
        String moduleCode = "CS2113";
        String gradebookComponentName = "Finals";
        String newGradebookComponentName = "Assignment 1";
        int maxMarks = 10;
        int weightage = 5;
        int expectedSize = 1;
        String expectedMessage = MESSAGE_EDIT_GRADEBOOK_SUCCESS + "%1$s\n";

        Gradebook gradebook = new Gradebook(
                moduleCode,
                gradebookComponentName,
                newGradebookComponentName,
                maxMarks,
                weightage);
        GradebookEditCommand gradebookEditCommand = new GradebookEditCommand(gradebook);
        CommandResult result = gradebookEditCommand.execute(new ModelManager(), new CommandHistory());

        assertEquals(String.format(expectedMessage, expectedSize), result.feedbackToUser);
    }

    @Test
    public void execute_gradebookEditInvalid_success() throws CommandException {
        String moduleCode = "CS2113";
        String gradebookComponentName = "Assignment 1";
        String newGradebookComponentName = "Assignment 2";
        int maxMarks = 10;
        int weightage = 5;
        String expectedMessage = MESSAGE_FIND_FAIL;

        Gradebook gradebook = new Gradebook(
                moduleCode,
                gradebookComponentName,
                newGradebookComponentName,
                maxMarks,
                weightage);
        GradebookEditCommand gradebookEditCommand = new GradebookEditCommand(gradebook);
        CommandResult result = gradebookEditCommand.execute(new ModelManager(), new CommandHistory());

        assertEquals(String.format(expectedMessage), result.feedbackToUser);
    }
}
