package seedu.address.logic.commands;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import seedu.address.logic.CommandHistory;
import seedu.address.model.gradebook.Gradebook;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

public class GradebookAddCommandTest {
    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();
    private CommandHistory commandHistory = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void execute_addGradebookComponentSuccessful() {
        final String moduleCode = "PC1222";
        final String gradebookComponentName = "Finals";
        final int gradebookMaxMarks = 20;
        final int gradebookWeightage = 10;

        assertCommandSuccess(new GradebookAddCommand(new Gradebook(
                moduleCode,
                gradebookComponentName,
                gradebookMaxMarks,
                gradebookWeightage)),
                new CommandHistory(),
                String.format(GradebookAddCommand.MESSAGE_ADD_SUCCESS,
                        moduleCode,
                        gradebookComponentName,
                        gradebookMaxMarks,
                        gradebookWeightage));
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }
}
