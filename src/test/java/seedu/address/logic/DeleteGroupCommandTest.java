package seedu.address.logic;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.DeleteGroupCommand.MESSAGE_ARGUMENTS;
import static seedu.address.testutil.TypicalGroups.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_GROUP;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_GROUP;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteGroupCommand;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with Model) and unit tests for DeleteGroupCommand
 */
public class DeleteGroupCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute() {
        final Index index = Index.fromOneBased(1);
        assertCommandFailure(new DeleteGroupCommand(index), model, new CommandHistory(),
                String.format(MESSAGE_ARGUMENTS, index.getOneBased()));
    }

    @Test
    public void equals() {
        DeleteGroupCommand deleteGroupFirstCommand = new DeleteGroupCommand(INDEX_FIRST_GROUP);
        DeleteGroupCommand deleteGroupSecondCommand = new DeleteGroupCommand(INDEX_SECOND_GROUP);

        // same object -> returns true
        assertTrue(deleteGroupFirstCommand.equals(deleteGroupFirstCommand));

        // same values -> returns true
        DeleteGroupCommand deleteGroupFirstCommandCopy = new DeleteGroupCommand(INDEX_FIRST_GROUP);
        assertTrue(deleteGroupFirstCommand.equals(deleteGroupFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteGroupFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteGroupFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteGroupFirstCommand.equals(deleteGroupSecondCommand));
    }

}
