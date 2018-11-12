package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showScheduleAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_SCHEDULE;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_SCHEDULE;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_SCHEDULE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalRecruitments.getTypicalRecruitmentList;
import static seedu.address.testutil.expenses.TypicalExpenses.getTypicalExpensesList;
import static seedu.address.testutil.schedule.TypicalSchedules.getTypicalScheduleList;

import org.junit.Rule;
import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.JumpToListScheduleRequestEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.ui.testutil.EventsCollectorRule;

/**
 * Contains integration tests (interaction with the Model) for {@code SelectScheduleCommand}.
 */
public class SelectScheduleCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalExpensesList(),
            getTypicalScheduleList(), getTypicalRecruitmentList(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), getTypicalExpensesList(),
            getTypicalScheduleList(), getTypicalRecruitmentList(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Index lastScheduleIndex = Index.fromOneBased(model.getFilteredScheduleList().size());

        assertExecutionSuccess(INDEX_FIRST_SCHEDULE);
        assertExecutionSuccess(INDEX_THIRD_SCHEDULE);
        assertExecutionSuccess(lastScheduleIndex);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_failure() {
        Index outOfBoundsIndex = Index.fromOneBased(model.getFilteredScheduleList().size() + 1);

        assertExecutionFailure(outOfBoundsIndex, Messages.MESSAGE_INVALID_SCHEDULE_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showScheduleAtIndex(model, INDEX_FIRST_SCHEDULE);
        showScheduleAtIndex(expectedModel, INDEX_FIRST_SCHEDULE);

        assertExecutionSuccess(INDEX_FIRST_SCHEDULE);
    }

    @Test
    public void execute_invalidIndexFilteredList_failure() {
        showScheduleAtIndex(model, INDEX_FIRST_SCHEDULE);
        showScheduleAtIndex(expectedModel, INDEX_FIRST_SCHEDULE);

        Index outOfBoundsIndex = INDEX_SECOND_SCHEDULE;
        // ensures that outOfBoundIndex is still in bounds of schedule List list
        assertTrue(outOfBoundsIndex.getZeroBased() < model.getScheduleList().getScheduleList().size());

        assertExecutionFailure(outOfBoundsIndex, Messages.MESSAGE_INVALID_SCHEDULE_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        SelectScheduleCommand selectFirstCommand = new SelectScheduleCommand(INDEX_FIRST_SCHEDULE);
        SelectScheduleCommand selectSecondCommand = new SelectScheduleCommand(INDEX_SECOND_SCHEDULE);

        // same object -> returns true
        assertTrue(selectFirstCommand.equals(selectFirstCommand));

        // same values -> returns true
        SelectScheduleCommand selectFirstCommandCopy = new SelectScheduleCommand(INDEX_FIRST_SCHEDULE);
        assertTrue(selectFirstCommand.equals(selectFirstCommandCopy));

        // different types -> returns false
        assertFalse(selectFirstCommand.equals(1));

        // null -> returns false
        assertFalse(selectFirstCommand.equals(null));

        // different schedule -> returns false
        assertFalse(selectFirstCommand.equals(selectSecondCommand));
    }

    /**
     * Executes a {@code SelectScheduleCommand} with the given {@code index},
     * and checks that {@code JumpToListRequestEvent} is raised with the correct index.
     */
    private void assertExecutionSuccess(Index index) {
        SelectScheduleCommand selectScheduleCommand = new SelectScheduleCommand(index);
        String expectedMessage = String.format(
                SelectScheduleCommand.MESSAGE_SELECT_SCHEDULE_SUCCESS, index.getOneBased());

        try {
            assertCommandSuccess(selectScheduleCommand, model, commandHistory, expectedMessage, expectedModel);
        } catch (ParseException pe) {
            pe.printStackTrace();
        }

        JumpToListScheduleRequestEvent lastEvent =
                (JumpToListScheduleRequestEvent) eventsCollectorRule.eventsCollector.getMostRecent();
        assertEquals(index, Index.fromZeroBased(lastEvent.targetIndex));
    }

    /**
     * Executes a {@code SelectScheduleCommand} with the given {@code index},
     * and checks that a {@code CommandException} is thrown with the {@code expectedMessage}.
     */
    private void assertExecutionFailure(Index index, String expectedMessage) {
        SelectScheduleCommand selectScheduleCommand = new SelectScheduleCommand(index);
        assertCommandFailure(selectScheduleCommand, model, commandHistory, expectedMessage);
        assertTrue(eventsCollectorRule.eventsCollector.isEmpty());
    }

}
