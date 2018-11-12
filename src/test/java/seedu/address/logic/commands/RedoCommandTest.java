package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.deleteFirstItem;
import static seedu.address.testutil.TypicalAccounts.getTypicalAccountList;
import static seedu.address.testutil.TypicalItems.getTypicalStockList;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.account.Username;


public class RedoCommandTest {

    private Model model;
    private Model expectedModel;
    private final CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        Username admin = new Username("admin");

        model = new ModelManager(getTypicalStockList(), new UserPrefs(), getTypicalAccountList());
        model.setLoggedInUser(admin);

        expectedModel = new ModelManager(getTypicalStockList(), new UserPrefs(), getTypicalAccountList());
        expectedModel.setLoggedInUser(admin);

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

}
