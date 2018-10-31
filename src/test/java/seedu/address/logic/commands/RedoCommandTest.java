package seedu.address.logic.commands;
/*
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.deleteFirstItem;
import static seedu.address.testutil.TypicalItems.getTypicalAccountList;
import static seedu.address.testutil.TypicalItems.getTypicalStockList;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
*/

public class RedoCommandTest {
/*
    private final Model model = new ModelManager(getTypicalStockList(), new UserPrefs(), getTypicalAccountList());
    private final Model expectedModel = new ModelManager(getTypicalStockList(), new UserPrefs(),
    getTypicalAccountList());
    private final CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        // set up of both models' undo/redo history
        deleteFirstItem(model);
        deleteFirstItem(model);
        model.undoStockList();
        model.undoStockList();

        deleteFirstItem(expectedModel);
        deleteFirstItem(expectedModel);
        expectedModel.undoStockList();
        expectedModel.undoStockList();
    }

    @Test
    public void execute() {
        // multiple redoable states in model
        expectedModel.redoStockList();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);

        // single redoable state in model
        expectedModel.redoStockList();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);

        // no redoable state in model
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }
    */
}
