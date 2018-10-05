package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.deleteFirstRecord;
import static seedu.address.testutil.TypicalRecords.getTypicalFinancialPlanner;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class RedoCommandTest {

    private final Model model = new ModelManager(getTypicalFinancialPlanner(), new UserPrefs());
    private final Model expectedModel = new ModelManager(getTypicalFinancialPlanner(), new UserPrefs());
    private final CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        // set up of both models' undo/redo history
        deleteFirstRecord(model);
        deleteFirstRecord(model);
        model.undoFinancialPlanner();
        model.undoFinancialPlanner();

        deleteFirstRecord(expectedModel);
        deleteFirstRecord(expectedModel);
        expectedModel.undoFinancialPlanner();
        expectedModel.undoFinancialPlanner();
    }

    @Test
    public void execute() {
        // multiple redoable states in model
        expectedModel.redoFinancialPlanner();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);

        // single redoable state in model
        expectedModel.redoFinancialPlanner();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);

        // no redoable state in model
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }
}
