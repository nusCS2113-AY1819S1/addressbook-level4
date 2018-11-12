package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_ASSIGN;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_TASK;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_TASK;
import static seedu.address.testutil.TypicalTasks.getTypicalTaskBook;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.AddressBook;
import seedu.address.model.EventBook;
import seedu.address.model.ExpenseBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class UpdateTaskCommandTest {

    private Model model = new ModelManager(new AddressBook(), new ExpenseBook(),
            new EventBook(), getTypicalTaskBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();


    @Test
    public void equals() {
        final UpdateTaskCommand standardCommand = new UpdateTaskCommand(INDEX_FIRST_TASK, DESC_ASSIGN);

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new UpdateTaskCommand(INDEX_SECOND_TASK, DESC_ASSIGN)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new UpdateTaskCommand(INDEX_FIRST_TASK, DESC_ASSIGN)));
    }

}
