package seedu.address.logic.commands;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ModelManager;
import seedu.address.model.gradebook.Gradebook;
import seedu.address.model.gradebook.GradebookManager;
import seedu.address.testutil.GradebookBuilder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.commands.GradebookFindCommand.MESSAGE_FIND_FAIL;
import static seedu.address.logic.commands.GradebookFindCommand.MESSAGE_FIND_SUCCESS;

public class GradebookFindCommandTest {
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
    public void execute_gradebookFind_success() throws CommandException {
        String moduleCode = "CS2113";
        String gradebookComponentName = "Finals";
        int gradebookMaxMarks = 20;
        int gradebookWeightage = 10;
        String expectedMessage = String.format(
                MESSAGE_FIND_SUCCESS,
                moduleCode,
                gradebookComponentName,
                gradebookMaxMarks,
                gradebookWeightage);

        gradebookManager.addGradebookComponent(dummyGradebookComponent.build());
        gradebookManager.saveGradebookList();

        Gradebook gradebook = new Gradebook(moduleCode, gradebookComponentName);
        GradebookFindCommand gradebookFindCommand = new GradebookFindCommand(gradebook);
        CommandResult result = gradebookFindCommand.execute(new ModelManager(), new CommandHistory());

        assertEquals(expectedMessage, result.feedbackToUser);
    }

    @Test
    public void execute_gradebookFind_fail() throws CommandException {
        String moduleCode = "CS2113";
        String gradebookComponentName = "Test";
        int gradebookMaxMarks = 50;
        int gradebookWeightage = 10;
        String expectedMessage = String.format(
                MESSAGE_FIND_FAIL,
                moduleCode,
                gradebookComponentName,
                gradebookMaxMarks,
                gradebookWeightage);

        gradebookManager.addGradebookComponent(dummyGradebookComponent.build());
        gradebookManager.saveGradebookList();

        Gradebook gradebook = new Gradebook(moduleCode, gradebookComponentName);
        GradebookFindCommand gradebookFindCommand = new GradebookFindCommand(gradebook);
        CommandResult result = gradebookFindCommand.execute(new ModelManager(), new CommandHistory());

        assertEquals(expectedMessage, result.feedbackToUser);
    }


    @AfterClass
    public static void tearDown() {
        gradebookManager.clearGradebook();
        gradebookManager.saveGradebookList();
    }

}
