package seedu.planner.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.planner.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.planner.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.planner.testutil.TypicalLimits.LIMIT_100;
import static seedu.planner.testutil.TypicalLimits.LIMIT_ALL_DIFFERENT;
import static seedu.planner.testutil.TypicalRecords.getTypicalFinancialPlanner;

import org.junit.Test;

import seedu.planner.commons.core.Messages;
import seedu.planner.logic.CommandHistory;
import seedu.planner.model.Model;
import seedu.planner.model.ModelManager;
import seedu.planner.model.UserPrefs;
import seedu.planner.model.record.Limit;
import seedu.planner.testutil.LimitBuilder;



/**
 * To test the functionality of deleteLimitCommand. including multiple tests.
 */
public class DeleteLimitCommandTest {
    private Model model = new ModelManager(getTypicalFinancialPlanner(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validLimitDatesInList_success() {
        Limit limitToDelete = new LimitBuilder().build();
        model.addLimit(limitToDelete);
        DeleteLimitCommand deleteLimitCommand =
                new DeleteLimitCommand(limitToDelete);

        String expectedMessage = DeleteLimitCommand.MESSAGE_SUCCESS;

        ModelManager expectedModel = new ModelManager(model.getFinancialPlanner(), new UserPrefs());
        expectedModel.deleteLimit(limitToDelete);
        expectedModel.commitFinancialPlanner();

        assertCommandSuccess(deleteLimitCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_limitDatesNotInList_throwCommandException() {
        Limit limitNotInside = new LimitBuilder(LIMIT_ALL_DIFFERENT).build();
        DeleteLimitCommand deleteLimitCommand = new DeleteLimitCommand(limitNotInside);

        assertCommandFailure(deleteLimitCommand, model, commandHistory, Messages.MESSAGE_LIMITS_DO_NOT_EXIST);
    }

    @Test
    public void executeUndoRedo_invalidDeleteLimitExecution_failure() {
        Limit limitNotInside = new LimitBuilder(LIMIT_ALL_DIFFERENT).build();
        DeleteLimitCommand deleteLimitCommand = new DeleteLimitCommand(limitNotInside);

        // execution failed -> planner book state not added into model
        assertCommandFailure(deleteLimitCommand, model, commandHistory, Messages.MESSAGE_LIMITS_DO_NOT_EXIST);

        // single planner book state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    @Test
    public void executeUndoRedo_validLimitDeletionExecution_success() throws Exception {
        Limit limitToDelete = new LimitBuilder(LIMIT_100).build();
        model.addLimit(limitToDelete);
        DeleteLimitCommand deleteLimitCommand = new DeleteLimitCommand(limitToDelete);
        Model expectedModel = new ModelManager(model.getFinancialPlanner(), new UserPrefs());
        expectedModel.deleteLimit(limitToDelete);
        expectedModel.commitFinancialPlanner();

        // delete -> first limit deleted
        deleteLimitCommand.execute(model, commandHistory);

        // undo -> reverts financialplanner back to previous state.
        expectedModel.undoFinancialPlanner();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first limit deleted again
        expectedModel.redoFinancialPlanner();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }


    @Test
    public void equals() {
        DeleteLimitCommand deleteFirstCommand = new DeleteLimitCommand(LIMIT_100);
        DeleteLimitCommand deleteSecondCommand = new DeleteLimitCommand(LIMIT_ALL_DIFFERENT);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteLimitCommand deleteFirstCommandCopy = new DeleteLimitCommand(LIMIT_100);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different record -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

}
