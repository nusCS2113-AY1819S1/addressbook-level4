package seedu.planner.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.planner.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.planner.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.planner.logic.commands.CommandTestUtil.showRecordAtIndex;
import static seedu.planner.testutil.TypicalIndexes.INDEX_FIRST_RECORD;
import static seedu.planner.testutil.TypicalIndexes.INDEX_SECOND_RECORD;
import static seedu.planner.testutil.TypicalIndexes.INDEX_THIRD_RECORD;
import static seedu.planner.testutil.TypicalRecords.getTypicalFinancialPlanner;

import org.junit.Rule;
import org.junit.Test;

import seedu.planner.commons.core.Messages;
import seedu.planner.commons.core.index.Index;
import seedu.planner.commons.events.ui.JumpToListRequestEvent;
import seedu.planner.logic.CommandHistory;
import seedu.planner.model.Model;
import seedu.planner.model.ModelManager;
import seedu.planner.model.UserPrefs;
import seedu.planner.ui.testutil.EventsCollectorRule;

/**
 * Contains integration tests (interaction with the Model) for {@code SelectCommand}.
 */
public class SelectCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private Model model = new ModelManager(getTypicalFinancialPlanner(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalFinancialPlanner(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Index lastRecordIndex = Index.fromOneBased(model.getFilteredRecordList().size());

        assertExecutionSuccess(INDEX_FIRST_RECORD);
        assertExecutionSuccess(INDEX_THIRD_RECORD);
        assertExecutionSuccess(lastRecordIndex);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_failure() {
        Index outOfBoundsIndex = Index.fromOneBased(model.getFilteredRecordList().size() + 1);

        assertExecutionFailure(outOfBoundsIndex, Messages.MESSAGE_INVALID_RECORD_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showRecordAtIndex(model, INDEX_FIRST_RECORD);
        showRecordAtIndex(expectedModel, INDEX_FIRST_RECORD);

        assertExecutionSuccess(INDEX_FIRST_RECORD);
    }

    @Test
    public void execute_invalidIndexFilteredList_failure() {
        showRecordAtIndex(model, INDEX_FIRST_RECORD);
        showRecordAtIndex(expectedModel, INDEX_FIRST_RECORD);

        Index outOfBoundsIndex = INDEX_SECOND_RECORD;
        // ensures that outOfBoundIndex is still in bounds of planner book list
        assertTrue(outOfBoundsIndex.getZeroBased() < model.getFinancialPlanner().getRecordList().size());

        assertExecutionFailure(outOfBoundsIndex, Messages.MESSAGE_INVALID_RECORD_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        SelectCommand selectFirstCommand = new SelectCommand(INDEX_FIRST_RECORD);
        SelectCommand selectSecondCommand = new SelectCommand(INDEX_SECOND_RECORD);

        // same object -> returns true
        assertTrue(selectFirstCommand.equals(selectFirstCommand));

        // same values -> returns true
        SelectCommand selectFirstCommandCopy = new SelectCommand(INDEX_FIRST_RECORD);
        assertTrue(selectFirstCommand.equals(selectFirstCommandCopy));

        // different types -> returns false
        assertFalse(selectFirstCommand.equals(1));

        // null -> returns false
        assertFalse(selectFirstCommand.equals(null));

        // different record -> returns false
        assertFalse(selectFirstCommand.equals(selectSecondCommand));
    }

    /**
     * Executes a {@code SelectCommand} with the given {@code index}, and checks that {@code JumpToListRequestEvent}
     * is raised with the correct index.
     */
    private void assertExecutionSuccess(Index index) {
        SelectCommand selectCommand = new SelectCommand(index);
        String expectedMessage = String.format(SelectCommand.MESSAGE_SELECT_RECORD_SUCCESS, index.getOneBased());

        assertCommandSuccess(selectCommand, model, commandHistory, expectedMessage, expectedModel);

        JumpToListRequestEvent lastEvent = (JumpToListRequestEvent) eventsCollectorRule.eventsCollector.getMostRecent();
        assertEquals(index, Index.fromZeroBased(lastEvent.targetIndex));
    }

    /**
     * Executes a {@code SelectCommand} with the given {@code index}, and checks that a {@code CommandException}
     * is thrown with the {@code expectedMessage}.
     */
    private void assertExecutionFailure(Index index, String expectedMessage) {
        SelectCommand selectCommand = new SelectCommand(index);
        assertCommandFailure(selectCommand, model, commandHistory, expectedMessage);
        assertTrue(eventsCollectorRule.eventsCollector.isEmpty());
    }
}
