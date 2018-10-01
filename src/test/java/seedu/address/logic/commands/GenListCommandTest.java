package seedu.address.logic.commands;

import org.junit.Test;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;

/**
 * Contains integration tests (interaction with the Model) for {@code GenListCommand}.
 */
public class GenListCommandTest {

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_emptyObservableList_throwsCommandException() {
        Model model = new ModelManager();
        String expectedMessage = String.format(GenListCommand.MESSAGE_EMPTY_LIST);
        assertCommandFailure(new GenListCommand(), model, commandHistory, expectedMessage);
    }
}
