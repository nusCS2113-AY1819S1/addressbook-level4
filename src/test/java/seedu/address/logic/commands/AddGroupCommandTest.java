package seedu.address.logic.commands;

import static seedu.address.logic.AddGroupCommand.MESSAGE_NOT_IMPLEMENTED_YET;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.testutil.TypicalGroups.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.logic.AddGroupCommand;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for AddGroupCommand.
 */
public class AddGroupCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    @Test
    public void execute() {
        assertCommandFailure(new AddGroupCommand(), model, new CommandHistory(), MESSAGE_NOT_IMPLEMENTED_YET);
    }
}
