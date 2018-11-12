package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.commands.GradebookListCommand.MESSAGE_LIST_SUCCESS;

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
 * Contains tests for GradebookListCommand.
 */
public class GradebookListCommandTest {
    private static GradebookManager gradebookManager = new GradebookManager();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() {
        StorageController.enterTestMode();
        gradebookManager.clearGradebook();
        Gradebook gradebook = new GradebookBuilder()
                .withModuleCode("CG2271")
                .withComponentName("Test")
                .withMaxmarks(20)
                .withWeightage(10)
                .build();
        gradebookManager.addGradebookComponent(gradebook);
        gradebookManager.saveGradebookList();
    }

    @Test
    public void execute_gradebookList_success() {
        int expectedSize = 1;
        String expectedMessage = MESSAGE_LIST_SUCCESS + "%1$s\n";

        GradebookListCommand gradebookListCommand = new GradebookListCommand();
        CommandResult result = gradebookListCommand.execute(new ModelManager(), new CommandHistory());

        assertEquals(String.format(expectedMessage, expectedSize), result.feedbackToUser);
    }
}
