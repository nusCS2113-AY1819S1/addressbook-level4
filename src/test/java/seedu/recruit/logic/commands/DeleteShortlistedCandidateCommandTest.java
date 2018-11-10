package seedu.recruit.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.recruit.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.recruit.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.recruit.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.recruit.testutil.TestUtil.getIndexSet;
import static seedu.recruit.testutil.TypicalCompaniesAndJobOffers.getTypicalCompanyBook;
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
import seedu.recruit.model.company.Company;
import seedu.recruit.model.joboffer.JobOffer;

/**
 * Contains integration tests (interaction with the Model, DeleteShortlistedCandidateCommand)
 * and unit tests for {@code DeleteShortlistedCandidateCommand}.
 */
@Ignore
public class DeleteShortlistedCandidateCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalCompanyBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();
    private UserPrefs userPrefs = new UserPrefs();

    @Test
    public void execute_validIndexUnfilteredList_success() {
        ModelManager expectedModel = new ModelManager(model.getCandidateBook(), model.getCompanyBook(),
                new UserPrefs());

        DeleteShortlistedCandidateInitializationCommand first = new DeleteShortlistedCandidateInitializationCommand();
        String expected = DeleteShortlistedCandidateInitializationCommand.MESSAGE_ENTERING_DELETE_PROCESS
                + DeleteShortlistedCandidateInitializationCommand.MESSAGE_NEXT_STEP
                + SelectCompanyCommand.MESSAGE_USAGE;
        assertCommandSuccess(first, model, commandHistory, expected, expectedModel);

        Company selectedCompany = model.getFilteredCompanyList().get(INDEX_FIRST.getZeroBased());
        SelectCompanyCommand selectCompanyCommand = new SelectCompanyCommand(INDEX_FIRST);
        String expectedMessageForCompany = String.format(SelectCompanyCommand.MESSAGE_SELECT_COMPANY_SUCCESS,
                INDEX_FIRST.getOneBased()) + SelectCompanyCommand.MESSAGE_SELECT_COMPANY_SUCCESS_NEXT_STEP
                + SelectJobCommand.MESSAGE_USAGE;
        assertCommandSuccess(selectCompanyCommand, model, commandHistory, expectedMessageForCompany, expectedModel);

        JobOffer selectedJobOffer = model.getFilteredCompanyJobList().get(INDEX_FIRST.getZeroBased());
        Candidate candidateToDelete = selectedJobOffer.getObservableCandidateList().get(INDEX_FIRST.getZeroBased());

        SelectJobCommand selectJobCommand = new SelectJobCommand(INDEX_FIRST);
        String expectedMessageForJob = String.format(SelectJobCommand.MESSAGE_SELECT_JOB_SUCCESS,
                INDEX_FIRST.getOneBased()) + SelectJobCommand.MESSAGE_SELECT_JOB_SUCCESS_NEXT_STEP_IN_SHORTLIST_DELETE
                + DeleteShortlistedCandidateCommand.MESSAGE_USAGE;
        assertCommandSuccess(selectJobCommand, model, commandHistory, expectedMessageForJob, expectedModel);

        DeleteShortlistedCandidateCommand deleteCandidateCommand =
                new DeleteShortlistedCandidateCommand(INDEX_FIRST);

        String expectedMessage = String.format(DeleteShortlistedCandidateCommand.MESSAGE_DELETE_CANDIDATE_SUCCESS,
                candidateToDelete.getName(), selectedJobOffer.getJob(), selectedCompany.getCompanyName());

        expectedModel.deleteShortlistedCandidateFromJobOffer(candidateToDelete, selectedJobOffer);
        expectedModel.commitRecruitBook();

        assertCommandSuccess(deleteCandidateCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validMultipleIndexesUnfilteredList_success() {
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
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredCandidateList().size() + 1);
        DeleteCandidateCommand deleteCandidateCommand = new DeleteCandidateCommand(getIndexSet(outOfBoundIndex));

        assertCommandFailure(deleteCandidateCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() throws ParseException {
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
    public void execute_invalidIndexFilteredList_throwsCommandException() throws ParseException {
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
        expectedModel.commitRecruitBook();

        // delete -> first candidate deleted
        deleteCandidateCommand.execute(model, commandHistory, userPrefs);

        // undo -> reverts Candidate book back to previous state and filtered candidate list to show all persons
        expectedModel.undoRecruitBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory,
                UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first candidate deleted again
        expectedModel.redoRecruitBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory,
                RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredCandidateList().size() + 1);
        DeleteCandidateCommand deleteCandidateCommand = new DeleteCandidateCommand(getIndexSet(outOfBoundIndex));

        // execution failed -> recruit book state not added into model
        assertCommandFailure(deleteCandidateCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        // single recruit book state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory,
                UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory,
                RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Deletes a {@code Candidate} from a filtered list.
     * 2. Undo the deletion.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously deleted candidate in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the deletion. This ensures {@code RedoCandidateBookCommand} deletes the candidate object regardless of
     * indexing.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_samePersonDeleted() throws Exception {
        DeleteCandidateCommand deleteCandidateCommand = new DeleteCandidateCommand(getIndexSet(INDEX_FIRST));
        Model expectedModel = new ModelManager(model.getCandidateBook(), new CompanyBook(), new UserPrefs());


        showPersonAtIndex(model, INDEX_SECOND);
        Candidate candidateToDelete = model.getFilteredCandidateList().get(INDEX_FIRST.getZeroBased());
        expectedModel.deleteCandidate(candidateToDelete);
        expectedModel.commitRecruitBook();

        // delete -> deletes second candidate in unfiltered candidate list / first candidate in filtered candidate list
        deleteCandidateCommand.execute(model, commandHistory, userPrefs);

        // undo -> reverts addressbook back to previous state and filtered candidate list to show all persons
        expectedModel.undoRecruitBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory,
                UndoCommand.MESSAGE_SUCCESS, expectedModel);

        assertNotEquals(candidateToDelete, model.getFilteredCandidateList().get(INDEX_FIRST.getZeroBased()));
        // redo -> deletes same second candidate in unfiltered candidate list
        expectedModel.redoRecruitBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory,
                RedoCommand.MESSAGE_SUCCESS, expectedModel);
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
