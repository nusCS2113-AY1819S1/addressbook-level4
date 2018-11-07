package seedu.planner.logic.commands;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static seedu.planner.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.planner.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.planner.testutil.TypicalRecords.getTypicalFinancialPlanner;

import org.junit.Test;

import seedu.planner.commons.core.Messages;
import seedu.planner.logic.CommandHistory;
import seedu.planner.model.FinancialPlanner;
import seedu.planner.model.Model;
import seedu.planner.model.ModelManager;
import seedu.planner.model.UserPrefs;
import seedu.planner.model.record.Limit;
import seedu.planner.testutil.LimitBuilder;
import seedu.planner.testutil.TypicalLimits;
//@@Author OscarZeng

public class EditLimitCommandTest {
    private Model model = new ModelManager(getTypicalFinancialPlanner(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_hasSameDatesLimitInside_success() {
        Limit editedLimit = new LimitBuilder(TypicalLimits.LIMIT_100).build();
        Limit originalLimit = new LimitBuilder(TypicalLimits.LIMIT_500).build();
        EditLimitCommand editLimitCommand = new EditLimitCommand(editedLimit);
        String expectedMessage = EditLimitCommand.MESSAGE_SUCCESS + "Original Limit:\n"
                + model.generateLimitOutput(model.isExceededLimit(originalLimit),
                model.getTotalSpend(originalLimit), originalLimit)
                + "Modified Limit: \n"
                + model.generateLimitOutput(model.isExceededLimit(editedLimit),
                model.getTotalSpend(editedLimit), editedLimit);
        model.addLimit(originalLimit);
        Model expectedModel = new ModelManager(new FinancialPlanner(model.getFinancialPlanner()), new UserPrefs());
        expectedModel.updateLimit(originalLimit, editedLimit);
        expectedModel.commitFinancialPlanner();

        assertCommandSuccess(editLimitCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_singleDateLimitInList_success() {
        Limit editedLimit = new LimitBuilder(TypicalLimits.LIMIT_SINGLE_DATE_100).build();
        Limit originalLimit = new LimitBuilder(TypicalLimits.LIMIT_SINGLE_DATE_500).build();
        EditLimitCommand editLimitCommand = new EditLimitCommand(editedLimit);
        String expectedMessage = EditLimitCommand.MESSAGE_SUCCESS + "Original Limit:\n"
                + model.generateLimitOutput(model.isExceededLimit(originalLimit),
                model.getTotalSpend(originalLimit), originalLimit)
                + "Modified Limit: \n"
                + model.generateLimitOutput(model.isExceededLimit(editedLimit),
                model.getTotalSpend(editedLimit), editedLimit);
        model.addLimit(originalLimit);
        Model expectedModel = new ModelManager(new FinancialPlanner(model.getFinancialPlanner()), new UserPrefs());
        expectedModel.updateLimit(originalLimit, editedLimit);
        expectedModel.commitFinancialPlanner();

        assertCommandSuccess(editLimitCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_hasNoSameDatesLimitInside_fail() {
        Limit editedLimit = new LimitBuilder(TypicalLimits.LIMIT_100).build();
        Limit originalLimit = new LimitBuilder(TypicalLimits.LIMIT_ALL_DIFFERENT).build();
        EditLimitCommand editLimitCommand = new EditLimitCommand(editedLimit);
        String expectedMessage = Messages.MESSAGE_LIMITS_DO_NOT_EXIST;
        model.addLimit(originalLimit);

        assertCommandFailure(editLimitCommand, model, commandHistory, expectedMessage);
    }

    @Test
    public void execute_hasNoSameSingleDateLimitInside_fail() {

        Limit editedLimit = new LimitBuilder(TypicalLimits.LIMIT_SINGLE_DATE_100).build();
        Limit originalLimit = new LimitBuilder(TypicalLimits.LIMIT_SINGLE_DATE_ALL_DIFF).build();

        EditLimitCommand editLimitCommand = new EditLimitCommand(editedLimit);
        String expectedMessage = Messages.MESSAGE_LIMITS_DO_NOT_EXIST;
        model.addLimit(originalLimit);

        assertCommandFailure(editLimitCommand, model, commandHistory, expectedMessage);
    }

    @Test
    public void execute_emptyLimitList_fail() {
        Limit editedLimit = new LimitBuilder(TypicalLimits.LIMIT_100).build();
        EditLimitCommand editLimitCommand = new EditLimitCommand(editedLimit);
        String expectedMessage = Messages.MESSAGE_LIMITS_DO_NOT_EXIST;

        assertCommandFailure(editLimitCommand, model, commandHistory, expectedMessage);
    }

    @Test
    public void executeUndoRedo_validLimitEditedExecution_success() throws Exception {
        Limit originalLimit = new LimitBuilder(TypicalLimits.LIMIT_100).build();
        Limit editedLimit = new LimitBuilder(TypicalLimits.LIMIT_500).build();
        model.addLimit(originalLimit);
        EditLimitCommand editLimitCommand = new EditLimitCommand(editedLimit);
        Model expectedModel = new ModelManager(model.getFinancialPlanner(), new UserPrefs());
        expectedModel.updateLimit(originalLimit, editedLimit);
        expectedModel.commitFinancialPlanner();

        // delete -> first limit deleted
        editLimitCommand.execute(model, commandHistory);

        // undo -> reverts financialplanner back to previous state.
        expectedModel.undoFinancialPlanner();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first limit deleted again
        expectedModel.redoFinancialPlanner();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        final EditLimitCommand editLimitCommand2 = new EditLimitCommand(TypicalLimits.LIMIT_100);
        final EditLimitCommand editLimitCommand1 = new EditLimitCommand(TypicalLimits.LIMIT_SINGLE_DATE_100);

        // same limits -> returns true
        EditLimitCommand sameLimitCommand = new EditLimitCommand(TypicalLimits.LIMIT_100);
        assertTrue(editLimitCommand2.equals(sameLimitCommand));

        // same limits -> returns true
        EditLimitCommand differentLimitCommand = new EditLimitCommand(TypicalLimits.LIMIT_ALL_DIFFERENT);
        assertFalse(editLimitCommand2.equals(differentLimitCommand));

        // same object -> returns true
        assertTrue(editLimitCommand2.equals(editLimitCommand2));

        // null -> returns false
        assertFalse(editLimitCommand2.equals(null));

        // different types -> returns false
        assertFalse(editLimitCommand2.equals(1));

        //different date type
        assertFalse(editLimitCommand1.equals(editLimitCommand2));

    }

}
