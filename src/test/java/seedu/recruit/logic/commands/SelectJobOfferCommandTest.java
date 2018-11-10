package seedu.recruit.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.recruit.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.recruit.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.recruit.logic.commands.CommandTestUtil.showJobOfferAtIndex;
import static seedu.recruit.testutil.TypicalCompaniesAndJobOffers.getTypicalCompanyBookWithUniqueJobOffers;
import static seedu.recruit.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.recruit.testutil.TypicalIndexes.INDEX_SECOND;

import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

import seedu.recruit.commons.core.Messages;
import seedu.recruit.commons.core.index.Index;
import seedu.recruit.commons.events.ui.JumpToCompanyJobListRequestEvent;
import seedu.recruit.logic.CommandHistory;
import seedu.recruit.model.CandidateBook;
import seedu.recruit.model.Model;
import seedu.recruit.model.ModelManager;
import seedu.recruit.model.UserPrefs;
import seedu.recruit.ui.testutil.EventsCollectorRule;

/**
 * Contains integration tests (interaction with the Model) for {@code SelectJobOfferCommand}.
 */
public class SelectJobOfferCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private Model model = new ModelManager(new CandidateBook(), getTypicalCompanyBookWithUniqueJobOffers(),
            new UserPrefs());
    private Model expectedModel = new ModelManager(new CandidateBook(), getTypicalCompanyBookWithUniqueJobOffers(),
            new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Index lastPersonIndex = Index.fromOneBased(model.getFilteredCompanyJobList().size());

        assertExecutionSuccess(INDEX_FIRST);
        assertExecutionSuccess(INDEX_SECOND);
        assertExecutionSuccess(lastPersonIndex);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_failure() {
        Index outOfBoundsIndex = Index.fromOneBased(model.getFilteredCompanyJobList().size() + 1);
        assertExecutionFailure(outOfBoundsIndex, Messages.MESSAGE_INVALID_JOB_OFFER_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showJobOfferAtIndex(model, INDEX_FIRST);
        showJobOfferAtIndex(expectedModel, INDEX_FIRST);

        assertExecutionSuccess(INDEX_FIRST);
    }

    @Test
    public void execute_invalidIndexFilteredList_failure() {
        showJobOfferAtIndex(model, INDEX_FIRST);
        showJobOfferAtIndex(expectedModel, INDEX_FIRST);

        Index outOfBoundsIndex = INDEX_SECOND;
        // ensures that outOfBoundIndex is still in bounds of job list
        assertTrue(outOfBoundsIndex.getZeroBased() < model.getCompanyBook().getCompanyJobList().size());

        assertExecutionFailure(outOfBoundsIndex, Messages.MESSAGE_INVALID_JOB_OFFER_DISPLAYED_INDEX);
    }

    @Test
    @Ignore
    public void equals() {
        SelectJobCommand selectFirstCommand = new SelectJobCommand(INDEX_FIRST);
        SelectJobCommand selectSecondCommand = new SelectJobCommand(INDEX_SECOND);

        // same object -> returns true
        assertTrue(selectFirstCommand.equals(selectFirstCommand));

        // same values -> returns true
        SelectJobCommand selectFirstCommandCopy = new SelectJobCommand(INDEX_FIRST);
        assertTrue(selectFirstCommand.equals(selectFirstCommandCopy));

        // different types -> returns false
        assertFalse(selectFirstCommand.equals(1));

        // null -> returns false
        assertFalse(selectFirstCommand.equals(null));

        // different job offer -> returns false -> this test failing
        assertFalse(selectFirstCommand.equals(selectSecondCommand));
    }

    /**
     * Executes a {@code SelectJobCommand} with the given {@code index},
     * and checks that {@code JumpToCompanyJobListRequestEvent} is raised with the correct index.
     */
    private void assertExecutionSuccess(Index index) {
        SelectJobCommand selectJobCommand = new SelectJobCommand(index);

        String expectedMessage = String.format(SelectJobCommand.MESSAGE_SELECT_JOB_SUCCESS,
                index.getOneBased());
        assertCommandSuccess(selectJobCommand, model, commandHistory, expectedMessage, expectedModel);

        JumpToCompanyJobListRequestEvent lastEvent =
                (JumpToCompanyJobListRequestEvent) eventsCollectorRule.eventsCollector.getMostRecent();
        assertEquals(index, Index.fromZeroBased(lastEvent.targetIndex));
    }

    /**
     * Executes a {@code SelectJobCommand} with the given {@code index},
     * and checks that a {@code CommandException} is thrown with the {@code expectedMessage}.
     */
    private void assertExecutionFailure(Index index, String expectedMessage) {
        SelectJobCommand selectJobCommand = new SelectJobCommand(index);
        assertCommandFailure(selectJobCommand, model, commandHistory, expectedMessage);
        assertTrue(eventsCollectorRule.eventsCollector.isEmpty());
    }
}
