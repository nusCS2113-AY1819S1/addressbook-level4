package seedu.recruit.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.recruit.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.recruit.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.recruit.logic.commands.CommandTestUtil.showCompanyAtIndex;
import static seedu.recruit.testutil.TypicalCompaniesAndJobOffers.getTypicalCompanyBook;
import static seedu.recruit.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.recruit.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.recruit.testutil.TypicalIndexes.INDEX_THIRD;

import org.junit.Rule;
import org.junit.Test;

import seedu.recruit.commons.core.Messages;
import seedu.recruit.commons.core.index.Index;
import seedu.recruit.commons.events.ui.JumpToCompanyListRequestEvent;
import seedu.recruit.logic.CommandHistory;
import seedu.recruit.model.CandidateBook;
import seedu.recruit.model.Model;
import seedu.recruit.model.ModelManager;
import seedu.recruit.model.UserPrefs;
import seedu.recruit.ui.testutil.EventsCollectorRule;

/**
 * Contains integration tests (interaction with the Model) for {@code SelectCompanyCommand}.
 */
public class SelectCompanyCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private Model model = new ModelManager(new CandidateBook(), getTypicalCompanyBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(new CandidateBook(), getTypicalCompanyBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Index lastCompanyIndex = Index.fromOneBased(model.getFilteredCompanyList().size());

        assertExecutionSuccess(INDEX_FIRST);
        assertExecutionSuccess(INDEX_THIRD);
        assertExecutionSuccess(lastCompanyIndex);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_failure() {
        Index outOfBoundsIndex = Index.fromOneBased(model.getFilteredCompanyList().size() + 1);

        assertExecutionFailure(outOfBoundsIndex, Messages.MESSAGE_INVALID_COMPANY_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showCompanyAtIndex(model, INDEX_FIRST);
        showCompanyAtIndex(expectedModel, INDEX_FIRST);

        assertExecutionSuccess(INDEX_FIRST);
    }

    @Test
    public void execute_invalidIndexFilteredList_failure() {
        showCompanyAtIndex(model, INDEX_FIRST);
        showCompanyAtIndex(expectedModel, INDEX_FIRST);

        Index outOfBoundsIndex = INDEX_SECOND;
        // ensures that outOfBoundIndex is still in bounds of company book list
        assertTrue(outOfBoundsIndex.getZeroBased() < model.getCompanyBook().getCompanyList().size());

        assertExecutionFailure(outOfBoundsIndex, Messages.MESSAGE_INVALID_COMPANY_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        SelectCompanyCommand selectFirstCommand = new SelectCompanyCommand(INDEX_FIRST);
        SelectCompanyCommand selectSecondCommand = new SelectCompanyCommand(INDEX_SECOND);

        // same object -> returns true
        assertTrue(selectFirstCommand.equals(selectFirstCommand));

        // same values -> returns true
        SelectCompanyCommand selectFirstCommandCopy = new SelectCompanyCommand(INDEX_FIRST);
        assertTrue(selectFirstCommand.equals(selectFirstCommandCopy));

        // different types -> returns false
        assertFalse(selectFirstCommand.equals(1));

        // null -> returns false
        assertFalse(selectFirstCommand.equals(null));

        // different company -> returns false
        assertFalse(selectFirstCommand.equals(selectSecondCommand));
    }

    /**
     * Executes a {@code SelectCompanyCommand} with the given {@code index},
     * and checks that {@code JumpToCompanyListRequestEvent} is raised with the correct index.
     */
    private void assertExecutionSuccess(Index index) {
        SelectCompanyCommand selectCompanyCommand = new SelectCompanyCommand(index);

        String expectedMessage = String.format(SelectCompanyCommand.MESSAGE_SELECT_COMPANY_SUCCESS,
                index.getOneBased());
        assertCommandSuccess(selectCompanyCommand, model, commandHistory, expectedMessage, expectedModel);

        JumpToCompanyListRequestEvent lastEvent =
                (JumpToCompanyListRequestEvent) eventsCollectorRule.eventsCollector.getMostRecent();
        assertEquals(index, Index.fromZeroBased(lastEvent.targetIndex));
    }

    /**
     * Executes a {@code SelectCompanyCommand} with the given {@code index},
     * and checks that a {@code CommandException} is thrown with the {@code expectedMessage}.
     */
    private void assertExecutionFailure(Index index, String expectedMessage) {
        SelectCompanyCommand selectCompanyCommand = new SelectCompanyCommand(index);
        assertCommandFailure(selectCompanyCommand, model, commandHistory, expectedMessage);
        assertTrue(eventsCollectorRule.eventsCollector.isEmpty());
    }
}
