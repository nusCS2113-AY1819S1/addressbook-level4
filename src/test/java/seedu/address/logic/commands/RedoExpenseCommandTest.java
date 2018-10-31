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

public class RedoExpenseCommandTest {

    private final Model model = new ModelManager(new AddressBook(), getTypicalExpenseBook(), new EventBook(),
            new TaskBook(), new UserPrefs());
    private final Model expectedModel = new ModelManager(new AddressBook(), getTypicalExpenseBook(), new EventBook(),
            new TaskBook(), new UserPrefs());
    private final CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        // set up of both models' undo/redo history
        deleteFirstExpense(model);
        deleteFirstExpense(model);
        model.undoExpenseBook();
        model.undoExpenseBook();

        deleteFirstExpense(expectedModel);
        deleteFirstExpense(expectedModel);
        expectedModel.undoExpenseBook();
        expectedModel.undoExpenseBook();
    }

    @Test
    public void execute() {
        // multiple redoable states in model
        expectedModel.redoExpenseBook();
        assertCommandSuccess(new RedoExpenseCommand(), model, commandHistory,
                RedoExpenseCommand.MESSAGE_SUCCESS, expectedModel);

        // single redoable state in model
        expectedModel.redoExpenseBook();
        assertCommandSuccess(new RedoExpenseCommand(), model, commandHistory,
                RedoExpenseCommand.MESSAGE_SUCCESS, expectedModel);

        // no redoable state in model
        assertCommandFailure(new RedoExpenseCommand(), model, commandHistory,
                RedoExpenseCommand.MESSAGE_FAILURE);
    }
}
