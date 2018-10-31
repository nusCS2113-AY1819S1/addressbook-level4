package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.deleteFirstExpense;
import static seedu.address.testutil.TypicalExpenses.getTypicalExpenseBook;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.AddressBook;
import seedu.address.model.EventBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.TaskBook;
import seedu.address.model.UserPrefs;

public class UndoExpenseCommandTest {

    private final Model model = new ModelManager(new AddressBook(), getTypicalExpenseBook(), new EventBook(),
            new TaskBook(), new UserPrefs());
    private final Model expectedModel = new ModelManager(new AddressBook(), getTypicalExpenseBook(), new EventBook(),
            new TaskBook(), new UserPrefs());
    private final CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        // set up of models' undo/redo history
        deleteFirstExpense(model);
        deleteFirstExpense(model);

        deleteFirstExpense(expectedModel);
        deleteFirstExpense(expectedModel);
    }

    @Test
    public void execute() {
        // multiple undoable states in model
        expectedModel.undoExpenseBook();
        assertCommandSuccess(new UndoExpenseCommand(), model, commandHistory,
                UndoExpenseCommand.MESSAGE_SUCCESS, expectedModel);

        // single undoable state in model
        expectedModel.undoExpenseBook();
        assertCommandSuccess(new UndoExpenseCommand(), model, commandHistory,
                UndoExpenseCommand.MESSAGE_SUCCESS, expectedModel);

        // no undoable states in model
        assertCommandFailure(new UndoExpenseCommand(), model, commandHistory,
                UndoExpenseCommand.MESSAGE_FAILURE);
    }
}
