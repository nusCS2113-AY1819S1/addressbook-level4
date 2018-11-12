package seedu.recruit.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.recruit.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.recruit.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.recruit.logic.commands.CommandTestUtil.showJobOfferAtIndex;
import static seedu.recruit.testutil.TestUtil.getIndexSet;
import static seedu.recruit.testutil.TypicalCompaniesAndJobOffers.getTypicalCompanyBook;
import static seedu.recruit.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.recruit.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.recruit.testutil.TypicalIndexes.INDEX_THIRD;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import seedu.recruit.commons.core.Messages;
import seedu.recruit.commons.core.index.Index;
import seedu.recruit.logic.CommandHistory;
import seedu.recruit.model.CandidateBook;
import seedu.recruit.model.Model;
import seedu.recruit.model.ModelManager;
import seedu.recruit.model.UserPrefs;
import seedu.recruit.model.joboffer.JobOffer;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand)
 * and unit tests for {@code DeleteJobOfferCommand}.
 */

public class DeleteJobOfferCommandTest {

    private Model model;
    private CommandHistory commandHistory = new CommandHistory();
    private UserPrefs userPrefs = new UserPrefs();


    @Before
    public void setUp() {
        model = new ModelManager(new CandidateBook(), getTypicalCompanyBook(), new UserPrefs());
    }


    @Test
    public void execute_validIndexUnfilteredList_success() {
        JobOffer jobOfferToDelete = model.getFilteredCompanyJobList().get(INDEX_FIRST.getZeroBased());
        DeleteJobOfferCommand deleteJobOfferCommand = new DeleteJobOfferCommand(getIndexSet(INDEX_FIRST));

        String expectedMessage = String.format(DeleteJobOfferCommand.MESSAGE_DELETE_JOB_OFFER_SUCCESS,
                jobOfferToDelete + "\n");

        ModelManager expectedModel = new ModelManager(new CandidateBook(), model.getCompanyBook(), new UserPrefs());
        expectedModel.deleteJobOffer(jobOfferToDelete);
        expectedModel.commitRecruitBook();

        assertCommandSuccess(deleteJobOfferCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validMultipleIndexesUnfilteredList_success() {
        //Set up deleted job offers
        ArrayList<JobOffer> jobOffersToDelete = new ArrayList<JobOffer>();
        jobOffersToDelete.add(model.getFilteredCompanyJobList().get(INDEX_FIRST.getZeroBased()));
        jobOffersToDelete.add(model.getFilteredCompanyJobList().get(INDEX_SECOND.getZeroBased()));
        jobOffersToDelete.add(model.getFilteredCompanyJobList().get(INDEX_THIRD.getZeroBased()));

        DeleteJobOfferCommand deleteJobOfferCommand = new DeleteJobOfferCommand(getIndexSet(INDEX_FIRST, INDEX_SECOND,
                INDEX_THIRD));

        String expectedMessage = String.format(DeleteJobOfferCommand.MESSAGE_DELETE_JOB_OFFER_SUCCESS,
                jobOffersToDelete.get(2) + "\n"
                        + jobOffersToDelete.get(1) + "\n"
                        + jobOffersToDelete.get(0) + "\n");

        ModelManager expectedModel = new ModelManager(new CandidateBook(), model.getCompanyBook(), new UserPrefs());

        for (JobOffer jobOffer : jobOffersToDelete) {
            expectedModel.deleteJobOffer(jobOffer);
        }
        expectedModel.commitRecruitBook();

        assertCommandSuccess(deleteJobOfferCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredCompanyJobList().size() + 1);
        DeleteJobOfferCommand deleteJobOfferCommand = new DeleteJobOfferCommand(getIndexSet(outOfBoundIndex));

        assertCommandFailure(deleteJobOfferCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_JOB_OFFER_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        JobOffer jobOfferToDelete = model.getFilteredCompanyJobList().get(INDEX_FIRST.getZeroBased());
        DeleteJobOfferCommand deleteJobOfferCommand = new DeleteJobOfferCommand(getIndexSet(INDEX_FIRST));
        Model expectedModel = new ModelManager(model.getCandidateBook(), model.getCompanyBook(), new UserPrefs());
        expectedModel.deleteJobOffer(jobOfferToDelete);
        expectedModel.commitRecruitBook();

        // delete -> first company deleted
        deleteJobOfferCommand.execute(model, commandHistory, userPrefs);

        // undo -> reverts Companybook back to previous state and filtered job list to show all companies
        expectedModel.undoRecruitBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory,
                UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first job deleted again
        expectedModel.redoRecruitBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory,
                RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredCompanyJobList().size() + 1);
        DeleteJobOfferCommand deleteJobOfferCommand = new DeleteJobOfferCommand(getIndexSet(outOfBoundIndex));

        // execution failed -> recruit book state not added into model
        assertCommandFailure(deleteJobOfferCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_JOB_OFFER_DISPLAYED_INDEX);

        // single recruit book state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory,
                UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory,
                RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Deletes a {@code Job} from a filtered list.
     * 2. Undo the deletion.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously deleted job offer in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the deletion. This ensures {@code RedoCommand} deletes the JobOffer object regardless of
     * indexing.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_samePersonDeleted() throws Exception {
        DeleteJobOfferCommand deleteJobOfferCommand = new DeleteJobOfferCommand(getIndexSet(INDEX_FIRST));
        Model expectedModel = new ModelManager(model.getCandidateBook(), model.getCompanyBook(), new UserPrefs());


        showJobOfferAtIndex(model, INDEX_FIRST);
        JobOffer jobOfferToDelete = model.getFilteredCompanyJobList().get(INDEX_FIRST.getZeroBased());
        expectedModel.deleteJobOffer(jobOfferToDelete);
        expectedModel.commitRecruitBook();

        // delete -> deletes second job in unfiltered job offer list / first job offer in filtered job list
        deleteJobOfferCommand.execute(model, commandHistory, userPrefs);

        // undo -> reverts RecruitBook back to previous state and filtered job list to show all persons
        expectedModel.undoRecruitBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory,
                UndoCommand.MESSAGE_SUCCESS, expectedModel);

        assertNotEquals(jobOfferToDelete, model.getFilteredCompanyList().get(INDEX_FIRST.getZeroBased()));
        // redo -> deletes same second job in unfiltered job list
        expectedModel.redoRecruitBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory,
                RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        DeleteJobOfferCommand deleteFirstCommand = new DeleteJobOfferCommand(getIndexSet(INDEX_FIRST));
        DeleteJobOfferCommand deleteSecondCommand = new DeleteJobOfferCommand(getIndexSet(INDEX_SECOND));

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteJobOfferCommand deleteFirstCommandCopy = new DeleteJobOfferCommand(getIndexSet(INDEX_FIRST));
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different job offer -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }
}
