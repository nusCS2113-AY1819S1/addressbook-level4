package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showWorkoutAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_WORKOUT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_WORKOUT;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_WORKOUT;
import static seedu.address.testutil.TypicalWorkouts.getTypicalWorkoutBook;

import org.junit.Rule;
import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.ui.testutil.EventsCollectorRule;

/**
 * Contains integration tests (interaction with the Model) for {@code SelectCommand}.
 */
public class SelectCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private Model model = new ModelManager(getTypicalWorkoutBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalWorkoutBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Index lastWorkoutIndex = Index.fromOneBased(model.getFilteredWorkoutList().size());

        assertExecutionSuccess(INDEX_FIRST_WORKOUT);
        assertExecutionSuccess(INDEX_THIRD_WORKOUT);
        assertExecutionSuccess(lastWorkoutIndex);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_failure() {
        Index outOfBoundsIndex = Index.fromOneBased(model.getFilteredWorkoutList().size() + 1);

        assertExecutionFailure(outOfBoundsIndex, Messages.MESSAGE_INVALID_WORKOUT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showWorkoutAtIndex(model, INDEX_FIRST_WORKOUT);
        showWorkoutAtIndex(expectedModel, INDEX_FIRST_WORKOUT);

        assertExecutionSuccess(INDEX_FIRST_WORKOUT);
    }

    @Test
    public void execute_invalidIndexFilteredList_failure() {
        showWorkoutAtIndex(model, INDEX_FIRST_WORKOUT);
        showWorkoutAtIndex(expectedModel, INDEX_FIRST_WORKOUT);

        Index outOfBoundsIndex = INDEX_SECOND_WORKOUT;
        // ensures that outOfBoundIndex is still in bounds of workout book list
        assertTrue(outOfBoundsIndex.getZeroBased() < model.getWorkoutBook().getWorkoutList().size());

        assertExecutionFailure(outOfBoundsIndex, Messages.MESSAGE_INVALID_WORKOUT_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        SelectCommand selectFirstCommand = new SelectCommand(INDEX_FIRST_WORKOUT);
        SelectCommand selectSecondCommand = new SelectCommand(INDEX_SECOND_WORKOUT);

        // same object -> returns true
        assertTrue(selectFirstCommand.equals(selectFirstCommand));

        // same values -> returns true
        SelectCommand selectFirstCommandCopy = new SelectCommand(INDEX_FIRST_WORKOUT);
        assertTrue(selectFirstCommand.equals(selectFirstCommandCopy));

        // different types -> returns false
        assertFalse(selectFirstCommand.equals(1));

        // null -> returns false
        assertFalse(selectFirstCommand.equals(null));

        // different workout -> returns false
        assertFalse(selectFirstCommand.equals(selectSecondCommand));
    }

    /**
     * Executes a {@code SelectCommand} with the given {@code index}, and checks that {@code JumpToListRequestEvent}
     * is raised with the correct index.
     */
    private void assertExecutionSuccess(Index index) {
        SelectCommand selectCommand = new SelectCommand(index);
        String expectedMessage = String.format(SelectCommand.MESSAGE_SELECT_WORKOUT_SUCCESS, index.getOneBased());

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
