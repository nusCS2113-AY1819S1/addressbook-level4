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
public class UndoCommandTest {
/*
    private final Model model = new ModelManager(getTypicalStockList(), new UserPrefs(), getTypicalAccountList());
    private final Model expectedModel = new ModelManager(getTypicalStockList(), new UserPrefs(), model
    .getAccountList());
    private final CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        // set up of models' undo/redo history
        deleteFirstItem(model);
        deleteFirstItem(model);

        deleteFirstItem(expectedModel);
        deleteFirstItem(expectedModel);
    }

    @Test
    public void execute() {
        // multiple undoable states in model
        expectedModel.undoStockList();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // single undoable state in model
        expectedModel.undoStockList();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // no undoable states in model
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
    }
    */
}

