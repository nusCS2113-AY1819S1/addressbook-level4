package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.commands.GradebookAddCommand.MESSAGE_ADD_SUCCESS;
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

public class GradebookAddCommandTest {
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
    public void constructor_nullGradebook_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new GradebookAddCommand(null);
    }

    @Test
    public void execute_gradebookAdd_success() throws CommandException {
        String moduleCode = "CS2113";
        String gradebookComponentName = "Practical Exam";
        int expectedSize = 2;
        String expectedMessage = MESSAGE_ADD_SUCCESS + "%1$s\n";

        gradebookManager.addGradebookComponent(dummyGradebookComponent.build());
        gradebookManager.saveGradebookList();

        Gradebook gradebook = new Gradebook(moduleCode, gradebookComponentName);
        GradebookAddCommand gradebookAddCommand = new GradebookAddCommand(gradebook);
        CommandResult result = gradebookAddCommand.execute(new ModelManager(), new CommandHistory());

        assertEquals(String.format(expectedMessage, expectedSize), result.feedbackToUser);
    }
}
