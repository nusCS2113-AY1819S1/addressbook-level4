package seedu.recruit.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.recruit.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.recruit.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.recruit.logic.commands.CommandTestUtil.showCompanyAtIndex;
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
import seedu.recruit.model.company.Company;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand)
 * and unit tests for {@code DeleteCompanyCommand}.
 */

public class DeleteCompanyCommandTest {

    private Model model;
    private CommandHistory commandHistory = new CommandHistory();
    private UserPrefs userPrefs = new UserPrefs();

    @Before
    public void setUp() {
        model = new ModelManager(new CandidateBook(), getTypicalCompanyBook(), new UserPrefs());
    }


    @Test
    public void execute_validIndexUnfilteredList_success() {
        Company companyToDelete = model.getFilteredCompanyList().get(INDEX_FIRST.getZeroBased());
        DeleteCompanyCommand deleteCompanyCommand = new DeleteCompanyCommand(getIndexSet(INDEX_FIRST));

        String expectedMessage = String.format(DeleteCompanyCommand.MESSAGE_DELETE_COMPANY_SUCCESS,
                companyToDelete + "\n");

        ModelManager expectedModel = new ModelManager(new CandidateBook(), model.getCompanyBook(), new UserPrefs());
        expectedModel.deleteCompany(companyToDelete);
        expectedModel.commitRecruitBook();

        assertCommandSuccess(deleteCompanyCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validMultipleIndexesUnfilteredList_success() {
        //Set up deleted companies
        ArrayList<Company> companiesToDelete = new ArrayList<Company>();
        companiesToDelete.add(model.getFilteredCompanyList().get(INDEX_FIRST.getZeroBased()));
        companiesToDelete.add(model.getFilteredCompanyList().get(INDEX_SECOND.getZeroBased()));
        companiesToDelete.add(model.getFilteredCompanyList().get(INDEX_THIRD.getZeroBased()));

        DeleteCompanyCommand deleteCompanyCommand = new DeleteCompanyCommand(getIndexSet(INDEX_FIRST, INDEX_SECOND,
                                                        INDEX_THIRD));

        String expectedMessage = String.format(DeleteCompanyCommand.MESSAGE_DELETE_COMPANY_SUCCESS,
                companiesToDelete.get(2) + "\n"
                + companiesToDelete.get(1) + "\n"
                + companiesToDelete.get(0) + "\n");

        ModelManager expectedModel = new ModelManager(new CandidateBook(), model.getCompanyBook(), new UserPrefs());

        for (Company company : companiesToDelete) {
            expectedModel.deleteCompany(company);
        }

        expectedModel.commitRecruitBook();

        assertCommandSuccess(deleteCompanyCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredCompanyList().size() + 1);
        DeleteCompanyCommand deleteCompanyCommand = new DeleteCompanyCommand(getIndexSet(outOfBoundIndex));

        assertCommandFailure(deleteCompanyCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_COMPANY_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        Company companyToDelete = model.getFilteredCompanyList().get(INDEX_FIRST.getZeroBased());
        DeleteCompanyCommand deleteCompanyCommand = new DeleteCompanyCommand(getIndexSet(INDEX_FIRST));
        Model expectedModel = new ModelManager(model.getCandidateBook(), model.getCompanyBook(), new UserPrefs());
        expectedModel.deleteCompany(companyToDelete);
        expectedModel.commitRecruitBook();

        // delete -> first company deleted
        deleteCompanyCommand.execute(model, commandHistory, userPrefs);

        // undo -> reverts Companybook back to previous state and filtered company list to show all companies
        expectedModel.undoRecruitBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory,
                UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first company deleted again
        expectedModel.redoRecruitBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory,
                RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredCompanyList().size() + 1);
        DeleteCompanyCommand deleteCompanyCommand = new DeleteCompanyCommand(getIndexSet(outOfBoundIndex));

        // execution failed -> recruit book state not added into model
        assertCommandFailure(deleteCompanyCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_COMPANY_DISPLAYED_INDEX);

        // single recruit book state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory,
                UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory,
                RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Deletes a {@code Company} from a filtered list.
     * 2. Undo the deletion.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously deleted company in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the deletion. This ensures {@code RedoCommand} deletes the company object regardless of
     * indexing.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_samePersonDeleted() throws Exception {
        DeleteCompanyCommand deleteCompanyCommand = new DeleteCompanyCommand(getIndexSet(INDEX_FIRST));
        Model expectedModel = new ModelManager(model.getCandidateBook(), model.getCompanyBook(), new UserPrefs());


        showCompanyAtIndex(model, INDEX_SECOND);
        Company companyToDelete = model.getFilteredCompanyList().get(INDEX_FIRST.getZeroBased());
        expectedModel.deleteCompany(companyToDelete);
        expectedModel.commitRecruitBook();

        // delete -> deletes second company in unfiltered company list / first company in filtered company list
        deleteCompanyCommand.execute(model, commandHistory, userPrefs);

        // undo -> reverts RecruitBook back to previous state and filtered company list to show all persons
        expectedModel.undoRecruitBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory,
                UndoCommand.MESSAGE_SUCCESS, expectedModel);

        assertNotEquals(companyToDelete, model.getFilteredCompanyList().get(INDEX_FIRST.getZeroBased()));
        // redo -> deletes same second company in unfiltered company list
        expectedModel.redoRecruitBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory,
                RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        DeleteCompanyCommand deleteFirstCommand = new DeleteCompanyCommand(getIndexSet(INDEX_FIRST));
        DeleteCompanyCommand deleteSecondCommand = new DeleteCompanyCommand(getIndexSet(INDEX_SECOND));

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteCompanyCommand deleteFirstCommandCopy = new DeleteCompanyCommand(getIndexSet(INDEX_FIRST));
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different company -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

}
