package seedu.recruit.logic.commands;

import static seedu.recruit.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.recruit.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.recruit.testutil.TestUtil.getIndexSet;
import static seedu.recruit.testutil.TypicalCompanies.getTypicalCompanyBook;
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
 * Contains integration tests (interaction with the Model, UndoCandidateBookCommand and RedoCandidateBookCommand)
 * and unit tests for {@code DeleteCompanyCommand}.
 */

public class DeleteCompanyCommandTest {

    private Model model;
    private CommandHistory commandHistory = new CommandHistory();

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
        expectedModel.commitCompanyBook();

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

        expectedModel.commitCompanyBook();

        assertCommandSuccess(deleteCompanyCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredCompanyList().size() + 1);
        DeleteCompanyCommand deleteCompanyCommand = new DeleteCompanyCommand(getIndexSet(outOfBoundIndex));

        assertCommandFailure(deleteCompanyCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_COMPANY_DISPLAYED_INDEX);
    }


}
