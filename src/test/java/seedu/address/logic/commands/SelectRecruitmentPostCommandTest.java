package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showRecruitmentAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_RECRUITMENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_RECRUITMENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_RECRUITMENT;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalRecruitments.getTypicalRecruitmentList;
import static seedu.address.testutil.expenses.TypicalExpenses.getTypicalExpensesList;
import static seedu.address.testutil.schedule.TypicalSchedules.getTypicalScheduleList;

import org.junit.Rule;
import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.JumpToListRecruitmentPostRequestEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.ui.testutil.EventsCollectorRule;

/**
 * Contains integration tests (interaction with the Model) for {@code SelectScheduleCommand}.
 */
public class SelectRecruitmentPostCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalExpensesList(),
            getTypicalScheduleList(), getTypicalRecruitmentList(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), getTypicalExpensesList(),
            getTypicalScheduleList(), getTypicalRecruitmentList(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Index lastRecruitmentIndex = Index.fromOneBased(model.getFilteredRecruitmentList().size());

        assertExecutionSuccess(INDEX_FIRST_RECRUITMENT);
        assertExecutionSuccess(INDEX_THIRD_RECRUITMENT);
        assertExecutionSuccess(lastRecruitmentIndex);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_failure() {
        Index outOfBoundsIndex = Index.fromOneBased(model.getFilteredRecruitmentList().size() + 1);

        assertExecutionFailure(outOfBoundsIndex, Messages.MESSAGE_INVALID_RECRUITMENT_POST_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showRecruitmentAtIndex(model, INDEX_FIRST_RECRUITMENT);
        showRecruitmentAtIndex(expectedModel, INDEX_FIRST_RECRUITMENT);

        assertExecutionSuccess(INDEX_FIRST_RECRUITMENT);
    }

    @Test
    public void execute_invalidIndexFilteredList_failure() {
        showRecruitmentAtIndex(model, INDEX_FIRST_RECRUITMENT);
        showRecruitmentAtIndex(expectedModel, INDEX_FIRST_RECRUITMENT);

        Index outOfBoundsIndex = INDEX_SECOND_RECRUITMENT;
        // ensures that outOfBoundIndex is still in bounds of recruitment List
        assertTrue(outOfBoundsIndex.getZeroBased() < model.getRecruitmentList().getRecruitmentList().size());

        assertExecutionFailure(outOfBoundsIndex, Messages.MESSAGE_INVALID_RECRUITMENT_POST_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        SelectRecruitmentPostCommand selectFirstCommand = new SelectRecruitmentPostCommand(INDEX_FIRST_RECRUITMENT);
        SelectRecruitmentPostCommand selectSecondCommand = new SelectRecruitmentPostCommand(INDEX_SECOND_RECRUITMENT);

        // same object -> returns true
        assertTrue(selectFirstCommand.equals(selectFirstCommand));

        // same values -> returns true
        SelectRecruitmentPostCommand selectFirstCommandCopy = new SelectRecruitmentPostCommand(INDEX_FIRST_RECRUITMENT);
        assertTrue(selectFirstCommand.equals(selectFirstCommandCopy));

        // different types -> returns false
        assertFalse(selectFirstCommand.equals(1));

        // null -> returns false
        assertFalse(selectFirstCommand.equals(null));

        // different recruitment posts -> returns false
        assertFalse(selectFirstCommand.equals(selectSecondCommand));
    }

    /**
     * Executes a {@code SelectRecruitmentPostCommand} with the given {@code index},
     * and checks that {@code JumpToListRequestEvent} is raised with the correct index.
     */
    private void assertExecutionSuccess(Index index) {
        SelectRecruitmentPostCommand selectRecruitmentPostCommand = new SelectRecruitmentPostCommand(index);
        String expectedMessage = String.format(
                SelectRecruitmentPostCommand.MESSAGE_SELECT_RECRUITMENT_SUCCESS, index.getOneBased());

        try {
            assertCommandSuccess(selectRecruitmentPostCommand, model, commandHistory, expectedMessage, expectedModel);
        } catch (ParseException pe) {
            pe.printStackTrace();
        }

        JumpToListRecruitmentPostRequestEvent lastEvent =
                (JumpToListRecruitmentPostRequestEvent) eventsCollectorRule.eventsCollector.getMostRecent();
        assertEquals(index, Index.fromZeroBased(lastEvent.targetIndex));
    }

    /**
     * Executes a {@code SelectRecruitmentPostCommand} with the given {@code index},
     * and checks that a {@code CommandException} is thrown with the {@code expectedMessage}.
     */
    private void assertExecutionFailure(Index index, String expectedMessage) {
        SelectRecruitmentPostCommand selectRecruitmentPostCommand = new SelectRecruitmentPostCommand(index);
        assertCommandFailure(selectRecruitmentPostCommand, model, commandHistory, expectedMessage);
        assertTrue(eventsCollectorRule.eventsCollector.isEmpty());
    }

}
