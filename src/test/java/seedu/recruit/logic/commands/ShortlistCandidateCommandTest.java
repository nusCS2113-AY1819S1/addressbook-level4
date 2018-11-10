package seedu.recruit.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.recruit.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.recruit.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.recruit.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.recruit.testutil.TestUtil.getIndexSet;
import static seedu.recruit.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.recruit.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.recruit.testutil.TypicalIndexes.INDEX_THIRD;
import static seedu.recruit.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.ArrayList;

import org.junit.Ignore;
import org.junit.Test;
import seedu.recruit.commons.core.Messages;
import seedu.recruit.commons.core.index.Index;
import seedu.recruit.logic.CommandHistory;
import seedu.recruit.logic.parser.exceptions.ParseException;
import seedu.recruit.model.CompanyBook;
import seedu.recruit.model.Model;
import seedu.recruit.model.ModelManager;
import seedu.recruit.model.UserPrefs;
import seedu.recruit.model.candidate.Candidate;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand)
 * and unit tests for {@code DeleteCandidateCommand}.
 */
@Ignore
public class ShortlistCandidateCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new CompanyBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();
    private UserPrefs userPrefs = new UserPrefs();


    @Test
    public void execute_validCompanySelected_success() {
        Candidate candidateToDelete = model.getFilteredCandidateList().get(INDEX_FIRST.getZeroBased());
        DeleteCandidateCommand deleteCandidateCommand = new DeleteCandidateCommand(getIndexSet(INDEX_FIRST));

        String expectedMessage = String.format(DeleteCandidateCommand.MESSAGE_DELETE_CANDIDATE_SUCCESS,
                candidateToDelete + "\n");

        ModelManager expectedModel = new ModelManager(model.getCandidateBook(), new CompanyBook(), new UserPrefs());
        expectedModel.deleteCandidate(candidateToDelete);
        expectedModel.commitRecruitBook();

        assertCommandSuccess(deleteCandidateCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validJobSelected_success() {
        //Set up deleted companies
        ArrayList<Candidate> candidatesToDelete = new ArrayList<Candidate>();
        candidatesToDelete.add(model.getFilteredCandidateList().get(INDEX_FIRST.getZeroBased()));
        candidatesToDelete.add(model.getFilteredCandidateList().get(INDEX_SECOND.getZeroBased()));
        candidatesToDelete.add(model.getFilteredCandidateList().get(INDEX_THIRD.getZeroBased()));

        DeleteCandidateCommand deleteCandidateCommand = new DeleteCandidateCommand(getIndexSet(INDEX_FIRST,
                INDEX_SECOND, INDEX_THIRD));

        String expectedMessage = String.format(DeleteCandidateCommand.MESSAGE_DELETE_CANDIDATE_SUCCESS,
                candidatesToDelete.get(2) + "\n"
                        + candidatesToDelete.get(1) + "\n"
                        + candidatesToDelete.get(0) + "\n");

        ModelManager expectedModel = new ModelManager(model.getCandidateBook(), model.getCompanyBook(),
                new UserPrefs());

        for (Candidate candidate : candidatesToDelete) {
            expectedModel.deleteCandidate(candidate);
        }

        expectedModel.commitRecruitBook();

        assertCommandSuccess(deleteCandidateCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validCandidateSelected_success() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredCandidateList().size() + 1);
        DeleteCandidateCommand deleteCandidateCommand = new DeleteCandidateCommand(getIndexSet(outOfBoundIndex));

        assertCommandFailure(deleteCandidateCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_duplicateCandidateShortlisted_throwsCommandException() throws ParseException {
        showPersonAtIndex(model, INDEX_FIRST);

        Candidate candidateToDelete = model.getFilteredCandidateList().get(INDEX_FIRST.getZeroBased());
        DeleteCandidateCommand deleteCandidateCommand = new DeleteCandidateCommand(getIndexSet(INDEX_FIRST));

        String expectedMessage = String.format(DeleteCandidateCommand.MESSAGE_DELETE_CANDIDATE_SUCCESS,
                candidateToDelete + "\n");

        Model expectedModel = new ModelManager(model.getCandidateBook(), model.getCompanyBook(), new UserPrefs());
        expectedModel.deleteCandidate(candidateToDelete);
        expectedModel.commitRecruitBook();
        showNoPerson(expectedModel);

        assertCommandSuccess(deleteCandidateCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_blacklistedCandidateShortlisted_throwsCommandException() throws ParseException {
        showPersonAtIndex(model, INDEX_FIRST);

        Index outOfBoundIndex = INDEX_SECOND;
        // ensures that outOfBoundIndex is still in bounds of recruit book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getCandidateBook().getCandidateList().size());

        DeleteCandidateCommand deleteCandidateCommand = new DeleteCandidateCommand(getIndexSet(outOfBoundIndex));

        assertCommandFailure(deleteCandidateCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteCandidateCommand deleteFirstCommand = new DeleteCandidateCommand(getIndexSet(INDEX_FIRST));
        DeleteCandidateCommand deleteSecondCommand = new DeleteCandidateCommand(getIndexSet(INDEX_SECOND));

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteCandidateCommand deleteFirstCommandCopy = new DeleteCandidateCommand(getIndexSet(INDEX_FIRST));
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different candidate -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoPerson(Model model) {
        model.updateFilteredCandidateList(p -> false);

        assertTrue(model.getFilteredCandidateList().isEmpty());
    }
}
