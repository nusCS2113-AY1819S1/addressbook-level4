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
import static seedu.address.logic.commands.GradebookEditCommand.MESSAGE_EDIT_GRADEBOOK_SUCCESS;

public class GradebookEditCommandTest {
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
    public void execute_gradebookEdit_success() throws CommandException {
        String moduleCode = "CS2113";
        String gradebookComponentName = "Finals";
        int gradebookMaxMarks = 10;
        int gradebookWeightage = 20;
        String expectedMessage = MESSAGE_EDIT_GRADEBOOK_SUCCESS;

        Gradebook gradebook = new Gradebook(moduleCode, gradebookComponentName, gradebookMaxMarks, gradebookWeightage);
        GradebookEditCommand gradebookEditCommand = new GradebookEditCommand(gradebook);

        gradebookManager.addGradebookComponent(dummyGradebookComponent.build());
        gradebookManager.saveGradebookList();

        CommandResult result = gradebookEditCommand.execute(new ModelManager(), new CommandHistory());
        assertEquals(expectedMessage, result.feedbackToUser);
    }
}
