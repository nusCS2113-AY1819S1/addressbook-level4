package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.commands.GradebookFindCommand.MESSAGE_FIND_FAIL;
import static seedu.address.logic.commands.GradebookFindCommand.MESSAGE_FIND_SUCCESS;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.CommandHistory;
import seedu.address.model.ModelManager;
import seedu.address.model.StorageController;
import seedu.address.model.gradebook.Gradebook;
import seedu.address.model.gradebook.GradebookManager;
import seedu.address.testutil.GradebookBuilder;

/**
 * Contains tests for GradebookFindCommand.
 */
public class GradebookFindCommandTest {
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
                .withMaxmarks(50)
                .withWeightage(60)
                .build();
        gradebookManager.addGradebookComponent(gradebook);
        gradebookManager.saveGradebookList();
    }

    @Test
    public void constructor_nullGradebook_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new GradebookFindCommand(null);
    }

    @Test
    public void execute_gradebookFind_success() {
        String moduleCode = "CS2113";
        String gradebookComponentName = "Finals";
        String expectedMessage = MESSAGE_FIND_SUCCESS + "\n";

        Gradebook gradebook = new Gradebook(
                moduleCode,
                gradebookComponentName);
        GradebookFindCommand gradebookFindCommand = new GradebookFindCommand(gradebook);
        CommandResult result = gradebookFindCommand.execute(new ModelManager(), new CommandHistory());

        assertEquals(String.format(expectedMessage), result.feedbackToUser);
    }

    @Test
    public void execute_gradebookFindInvalid_success() {
        String moduleCode = "MA1511";
        String gradebookComponentName = "Assignment 1";
        String expectedMessage = MESSAGE_FIND_FAIL;

        Gradebook gradebook = new Gradebook(
                moduleCode,
                gradebookComponentName);
        GradebookFindCommand gradebookFindCommand = new GradebookFindCommand(gradebook);
        CommandResult result = gradebookFindCommand.execute(new ModelManager(), new CommandHistory());

        assertEquals(String.format(expectedMessage), result.feedbackToUser);
    }

    @AfterClass
    public static void tearDown() {
        gradebookManager.clearGradebook();
        gradebookManager.saveGradebookList();
    }

}
