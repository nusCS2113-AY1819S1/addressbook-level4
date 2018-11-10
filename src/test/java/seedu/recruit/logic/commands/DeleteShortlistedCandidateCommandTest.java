package seedu.recruit.logic.commands;

import static seedu.recruit.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.recruit.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.recruit.testutil.TypicalCompaniesAndJobOffers.AUDI;
import static seedu.recruit.testutil.TypicalCompaniesAndJobOffers.CASHIER_AUDI;
import static seedu.recruit.testutil.TypicalCompaniesAndJobOffers.getTypicalCompanyBook;
import static seedu.recruit.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.recruit.testutil.TypicalPersons.ALICE;
import static seedu.recruit.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import seedu.recruit.commons.core.Messages;
import seedu.recruit.commons.core.index.Index;
import seedu.recruit.logic.CommandHistory;
import seedu.recruit.model.Model;
import seedu.recruit.model.ModelManager;
import seedu.recruit.model.UserPrefs;
import seedu.recruit.model.candidate.Candidate;
import seedu.recruit.model.company.Company;
import seedu.recruit.model.joboffer.JobOffer;
import seedu.recruit.model.joboffer.JobOfferContainsFindKeywordsPredicate;

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
    @Ignore
    public void execute_validIndexInCandidateList_success() {
        Model expectedModel = new ModelManager(model.getCandidateBook(), model.getCompanyBook(),
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

        //Company selectedCompany = model.getFilteredCompanyList().get(INDEX_FIRST.getZeroBased());
        //JobOffer selectedJobOffer = model.getFilteredCompanyJobList().get(INDEX_FIRST.getZeroBased());

        expectedModel.deleteShortlistedCandidateFromJobOffer(candidateToDelete, selectedJobOffer);
        expectedModel.commitRecruitBook();

        String expectedMessage = String.format(DeleteShortlistedCandidateCommand.MESSAGE_DELETE_CANDIDATE_SUCCESS,
                candidateToDelete.getName(), selectedJobOffer.getJob(), selectedCompany.getCompanyName());


        assertCommandSuccess(new DeleteShortlistedCandidateCommand(INDEX_FIRST), model, commandHistory,
                expectedMessage, expectedModel);
    }


    @Test
    public void execute_invalidIndexInCandidateList_throwsCommandException() {
        Model expectedModel = new ModelManager(model.getCandidateBook(), model.getCompanyBook(), new UserPrefs());
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredCompanyJobList()
                .get(INDEX_FIRST.getZeroBased()).getObservableCandidateList().size() + 1);

        String expectedMessage = DeleteShortlistedCandidateInitializationCommand.MESSAGE_ENTERING_DELETE_PROCESS
                + DeleteShortlistedCandidateInitializationCommand.MESSAGE_NEXT_STEP
                + SelectCompanyCommand.MESSAGE_USAGE;
        assertCommandSuccess(new DeleteShortlistedCandidateInitializationCommand(), model, commandHistory,
                expectedMessage, expectedModel);

        String expectedMessageForCompany = String.format(SelectCompanyCommand.MESSAGE_SELECT_COMPANY_SUCCESS,
                INDEX_FIRST.getOneBased()) + SelectCompanyCommand.MESSAGE_SELECT_COMPANY_SUCCESS_NEXT_STEP
                + SelectJobCommand.MESSAGE_USAGE;
        assertCommandSuccess(new SelectCompanyCommand(INDEX_FIRST), model, commandHistory,
                expectedMessageForCompany, expectedModel);

        String expectedMessageForJob = String.format(SelectJobCommand.MESSAGE_SELECT_JOB_SUCCESS,
                INDEX_FIRST.getOneBased()) + SelectJobCommand.MESSAGE_SELECT_JOB_SUCCESS_NEXT_STEP_IN_SHORTLIST_DELETE
                + DeleteShortlistedCandidateCommand.MESSAGE_USAGE;
        assertCommandSuccess(new SelectJobCommand(INDEX_FIRST), model, commandHistory,
                expectedMessageForJob, expectedModel);

        assertCommandFailure(new DeleteShortlistedCandidateCommand(outOfBoundIndex), model, commandHistory,
                Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }


    @Test
    @Ignore
    public void execute_emptyCandidateList_exitsDeleteShortlistProcess() {
        Model expectedModel = new ModelManager(model.getCandidateBook(), model.getCompanyBook(), new UserPrefs());

        String expectedMessage = DeleteShortlistedCandidateInitializationCommand.MESSAGE_ENTERING_DELETE_PROCESS
                + DeleteShortlistedCandidateInitializationCommand.MESSAGE_NEXT_STEP
                + SelectCompanyCommand.MESSAGE_USAGE;
        assertCommandSuccess(new DeleteShortlistedCandidateInitializationCommand(), model, commandHistory,
                expectedMessage, expectedModel);

        String expectedMessageForCompany = String.format(SelectCompanyCommand.MESSAGE_SELECT_COMPANY_SUCCESS,
                INDEX_FIRST.getOneBased()) + SelectCompanyCommand.MESSAGE_SELECT_COMPANY_SUCCESS_NEXT_STEP
                + SelectJobCommand.MESSAGE_USAGE;
        assertCommandSuccess(new SelectCompanyCommand(INDEX_FIRST), model, commandHistory,
                expectedMessageForCompany, expectedModel);

        HashMap<String, List<String>> keywordsList = new HashMap<>();
        List<String> companyName = new ArrayList<>();
        companyName.add(CASHIER_AUDI.getCompanyName().toString());
        keywordsList.put("CompanyName", companyName);
        expectedModel.updateFilteredCompanyJobList(new JobOfferContainsFindKeywordsPredicate(keywordsList));
        String expectedMessageForJob = String.format(SelectJobCommand.MESSAGE_SELECT_JOB_SUCCESS,
                INDEX_FIRST.getOneBased()) + SelectJobCommand.MESSAGE_SELECT_JOB_SUCCESS_NEXT_STEP_IN_SHORTLIST_DELETE
                + DeleteShortlistedCandidateCommand.MESSAGE_USAGE;
        assertCommandSuccess(new SelectJobCommand(INDEX_FIRST), model, commandHistory,
                expectedMessageForJob, expectedModel);

        model.updateFilteredCompanyJobList(new JobOfferContainsFindKeywordsPredicate(keywordsList));
        expectedModel.deleteShortlistedCandidateFromJobOffer(ALICE, CASHIER_AUDI);
        expectedModel.commitRecruitBook();
        assertCommandSuccess(new DeleteShortlistedCandidateCommand(INDEX_FIRST), model, commandHistory,
                String.format(DeleteShortlistedCandidateCommand.MESSAGE_DELETE_CANDIDATE_SUCCESS,
                        ALICE.getName().fullName, CASHIER_AUDI.getJob().value, AUDI.getCompanyName().value),
                expectedModel);

    }

}
