package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.DeferDeadlineCommand.MESSAGE_NOT_IMPLEMENTED_YET;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

//@@ChanChunCheong
/**
 * Contains integration tests (interaction with the Model) and unit tests for RemarkCommand.
 */

public class DeferDeadlineCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute() {
        assertCommandFailure(new DeferDeadlineCommand(), model, new CommandHistory(), MESSAGE_NOT_IMPLEMENTED_YET);
    }

}