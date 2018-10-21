package seedu.recruit.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.recruit.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.recruit.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.recruit.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.recruit.testutil.TestUtil.getIndexSet;
import static seedu.recruit.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.recruit.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.recruit.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.recruit.commons.core.Messages;
import seedu.recruit.commons.core.index.Index;
import seedu.recruit.logic.CommandHistory;
import seedu.recruit.model.CompanyBook;
import seedu.recruit.model.Model;
import seedu.recruit.model.ModelManager;
import seedu.recruit.model.UserPrefs;
import seedu.recruit.model.candidate.Candidate;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteCandidateCommand}.
 */
public class DeleteCandidateCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new CompanyBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Candidate candidateToDelete = model.getFilteredCandidateList().get(INDEX_FIRST.getZeroBased());
        DeleteCandidateCommand deleteCandidateCommand = new DeleteCandidateCommand(getIndexSet(INDEX_FIRST));

        String expectedMessage = String.format(DeleteCandidateCommand.MESSAGE_DELETE_PERSON_SUCCESS,
                candidateToDelete + "\n");

        ModelManager expectedModel = new ModelManager(model.getCandidateBook(), new CompanyBook(), new UserPrefs());
        expectedModel.deleteCandidate(candidateToDelete);
        expectedModel.commitCandidateBook();

        assertCommandSuccess(deleteCandidateCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredCandidateList().size() + 1);
        DeleteCandidateCommand deleteCandidateCommand = new DeleteCandidateCommand(getIndexSet(outOfBoundIndex));

        assertCommandFailure(deleteCandidateCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST);

        Candidate candidateToDelete = model.getFilteredCandidateList().get(INDEX_FIRST.getZeroBased());
        DeleteCandidateCommand deleteCandidateCommand = new DeleteCandidateCommand(getIndexSet(INDEX_FIRST));

        String expectedMessage = String.format(DeleteCandidateCommand.MESSAGE_DELETE_PERSON_SUCCESS,
                candidateToDelete + "\n");

        Model expectedModel = new ModelManager(model.getCandidateBook(), model.getCompanyBook(), new UserPrefs());
        expectedModel.deleteCandidate(candidateToDelete);
        expectedModel.commitCandidateBook();
        showNoPerson(expectedModel);

        assertCommandSuccess(deleteCandidateCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showPersonAtIndex(model, INDEX_FIRST);

        Index outOfBoundIndex = INDEX_SECOND;
        // ensures that outOfBoundIndex is still in bounds of recruit book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getCandidateBook().getCandidateList().size());

        DeleteCandidateCommand deleteCandidateCommand = new DeleteCandidateCommand(getIndexSet(outOfBoundIndex));

        assertCommandFailure(deleteCandidateCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        Candidate candidateToDelete = model.getFilteredCandidateList().get(INDEX_FIRST.getZeroBased());
        DeleteCandidateCommand deleteCandidateCommand = new DeleteCandidateCommand(getIndexSet(INDEX_FIRST));
        Model expectedModel = new ModelManager(model.getCandidateBook(), model.getCompanyBook(), new UserPrefs());
        expectedModel.deleteCandidate(candidateToDelete);
        expectedModel.commitCandidateBook();

        // delete -> first candidate deleted
        deleteCandidateCommand.execute(model, commandHistory);

        // undo -> reverts addressbook back to previous state and filtered candidate list to show all persons
        expectedModel.undoCandidateBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first candidate deleted again
        expectedModel.redoCandidateBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredCandidateList().size() + 1);
        DeleteCandidateCommand deleteCandidateCommand = new DeleteCandidateCommand(getIndexSet(outOfBoundIndex));

        // execution failed -> recruit book state not added into model
        assertCommandFailure(deleteCandidateCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        // single recruit book state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Deletes a {@code Candidate} from a filtered list.
     * 2. Undo the deletion.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously deleted candidate in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the deletion. This ensures {@code RedoCommand} deletes the candidate object regardless of indexing.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_samePersonDeleted() throws Exception {
        DeleteCandidateCommand deleteCandidateCommand = new DeleteCandidateCommand(getIndexSet(INDEX_FIRST));
        Model expectedModel = new ModelManager(model.getCandidateBook(), new CompanyBook(), new UserPrefs());

        showPersonAtIndex(model, INDEX_SECOND);
        Candidate candidateToDelete = model.getFilteredCandidateList().get(INDEX_FIRST.getZeroBased());
        expectedModel.deleteCandidate(candidateToDelete);
        expectedModel.commitCandidateBook();

        // delete -> deletes second candidate in unfiltered candidate list / first candidate in filtered candidate list
        deleteCandidateCommand.execute(model, commandHistory);

        // undo -> reverts addressbook back to previous state and filtered candidate list to show all persons
        expectedModel.undoCandidateBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        assertNotEquals(candidateToDelete, model.getFilteredCandidateList().get(INDEX_FIRST.getZeroBased()));
        // redo -> deletes same second candidate in unfiltered candidate list
        expectedModel.redoCandidateBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
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
