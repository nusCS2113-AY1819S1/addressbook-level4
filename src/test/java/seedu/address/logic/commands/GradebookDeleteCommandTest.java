package seedu.address.logic.commands;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ModelManager;
import seedu.address.model.gradebook.GradebookManager;
import seedu.address.testutil.GradebookBuilder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.commands.GradebookDeleteCommand.MESSAGE_DELETE_SUCCESS;


public class GradebookDeleteCommandTest {
    private static GradebookManager gradebookManager = new GradebookManager();

    private static GradebookBuilder dummyGradebookComponent = new GradebookBuilder();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() {
        gradebookManager.clearGradebook();
        gradebookManager.saveGradebookList();
    }

    @Test
    public void execute_gradebookDelete_success() throws CommandException {
        String moduleCode = "CS2113";
        String gradebookComponentName = "Finals";
        String expectedMessage = String.format(MESSAGE_DELETE_SUCCESS, moduleCode, gradebookComponentName);

        gradebookManager.addGradebookComponent(dummyGradebookComponent.build());
        gradebookManager.saveGradebookList();

        GradebookDeleteCommand gradebookDeleteCommand = new GradebookDeleteCommand(moduleCode, gradebookComponentName);
        CommandResult result = gradebookDeleteCommand.execute(new ModelManager(), new CommandHistory());

        assertEquals(expectedMessage, result.feedbackToUser);
    }

    @AfterClass
    public static void tearDown() {
        gradebookManager.clearGradebook();
        gradebookManager.saveGradebookList();
    }
}
