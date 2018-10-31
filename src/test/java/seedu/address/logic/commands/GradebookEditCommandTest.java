package seedu.address.logic.commands;

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
import static seedu.address.logic.commands.GradebookEditCommand.MESSAGE_FIND_FAIL;

public class GradebookEditCommandTest {
    private static GradebookBuilder dummyGradebookComponent = new GradebookBuilder();
    private static GradebookManager gradebookManager = new GradebookManager();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() {
        gradebookManager.clearGradebook();
        gradebookManager.saveGradebookList();
    }

    @Test
    public void execute_gradebookEditInvalid_success() throws CommandException {
        String moduleCode = "CS2113";
        String gradebookComponentName = "Mid-Term";
        String newGradebookComponentName = "Test";
        String expectedMessage = MESSAGE_FIND_FAIL;
        int gradebookMaxMarks = 10;
        int gradebookWeightage = 20;

        gradebookManager.addGradebookComponent(dummyGradebookComponent.build());
        gradebookManager.saveGradebookList();

        Gradebook gradebook = new Gradebook(
                moduleCode,
                gradebookComponentName,
                newGradebookComponentName,
                gradebookMaxMarks,
                gradebookWeightage);
        GradebookEditCommand gradebookEditCommand = new GradebookEditCommand(gradebook);
        CommandResult result = gradebookEditCommand.execute(new ModelManager(), new CommandHistory());
        assertEquals(expectedMessage, result.feedbackToUser);
    }


}
