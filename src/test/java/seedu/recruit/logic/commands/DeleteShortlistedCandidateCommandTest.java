package seedu.recruit.logic.commands;

import static org.junit.Assert.assertTrue;
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

import org.junit.Test;

import seedu.recruit.commons.core.Messages;
import seedu.recruit.commons.core.index.Index;
import seedu.recruit.logic.CommandHistory;
import seedu.recruit.logic.commands.exceptions.CommandException;
import seedu.recruit.logic.parser.exceptions.ParseException;
import seedu.recruit.model.Model;
import seedu.recruit.model.ModelManager;
import seedu.recruit.model.UserPrefs;
import seedu.recruit.model.joboffer.JobOfferContainsFindKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model, DeleteShortlistedCandidateCommand)
 * and unit tests for {@code DeleteShortlistedCandidateCommand}.
 */
public class DeleteShortlistedCandidateCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalCompanyBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();
    private CommandHistory expectedCommandHistory = new CommandHistory();
    private UserPrefs userPrefs = new UserPrefs();

    @Test
    public void execute_validIndexInCandidateList_success() {
        Model expectedModel = new ModelManager(getTypicalAddressBook(), getTypicalCompanyBook(), new UserPrefs());

        Command firstStage = new DeleteShortlistedCandidateInitializationCommand();
        Command secondStage = new SelectCompanyCommand(INDEX_FIRST); // selects AUDI
        Command thirdStage = new SelectJobCommand(INDEX_FIRST); // selects CASHIER
        Command lastStage = new DeleteShortlistedCandidateCommand(INDEX_FIRST); // deletes ALICE

        try {
            firstStage.execute(expectedModel, expectedCommandHistory, userPrefs);
            secondStage.execute(expectedModel, expectedCommandHistory, userPrefs);
            thirdStage.execute(expectedModel, expectedCommandHistory, userPrefs);
            lastStage.execute(expectedModel, expectedCommandHistory, userPrefs);
        } catch (CommandException | ParseException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }

        try {
            firstStage.execute(model, commandHistory, userPrefs);
            secondStage.execute(model, commandHistory, userPrefs);
            thirdStage.execute(model, commandHistory, userPrefs);
        } catch (CommandException | ParseException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }

        String expectedMessage = String.format(DeleteShortlistedCandidateCommand.MESSAGE_DELETE_CANDIDATE_SUCCESS,
                ALICE.getName(), CASHIER_AUDI.getJob(), AUDI.getCompanyName());

        assertCommandSuccess(new DeleteShortlistedCandidateCommand(INDEX_FIRST), model, commandHistory,
                expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexInCandidateList_throwsCommandException() {
        Model expectedModel = new ModelManager(model.getCandidateBook(), model.getCompanyBook(), new UserPrefs());
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredCompanyJobList()
                .get(INDEX_FIRST.getZeroBased()).getObservableCandidateList().size() + 1);
        // ensures that outOfBoundIndex is still in bounds of candidate list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getCandidateBook().getCandidateList().size());

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

        assertCommandSuccess(new CancelCommand(DeleteShortlistedCandidateInitializationCommand.COMMAND_WORD), model,
                commandHistory, String.format(CancelCommand.MESSAGE_SUCCESS,
                        DeleteShortlistedCandidateInitializationCommand.COMMAND_WORD), expectedModel);
    }


    @Test
    public void execute_emptyCandidateList_exitsDeleteShortlistProcess() {
        Model expectedModel = new ModelManager(model.getCandidateBook(), model.getCompanyBook(), new UserPrefs());

        // delete ALICE from CASHIER_AUDI -> Candidate List will be emptied
        Command firstStage = new DeleteShortlistedCandidateInitializationCommand();
        Command secondStage = new SelectCompanyCommand(INDEX_FIRST); // selects AUDI
        Command thirdStage = new SelectJobCommand(INDEX_FIRST); // selects CASHIER
        Command lastStage = new DeleteShortlistedCandidateCommand(INDEX_FIRST); // deletes ALICE

        try {
            firstStage.execute(expectedModel, expectedCommandHistory, userPrefs);
            secondStage.execute(expectedModel, expectedCommandHistory, userPrefs);
            thirdStage.execute(expectedModel, expectedCommandHistory, userPrefs);
            lastStage.execute(expectedModel, expectedCommandHistory, userPrefs);
            firstStage.execute(model, commandHistory, userPrefs);
            secondStage.execute(model, commandHistory, userPrefs);
            thirdStage.execute(model, commandHistory, userPrefs);
            lastStage.execute(model, commandHistory, userPrefs);
        } catch (CommandException | ParseException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }

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

        /**
         * Asserts command success because deleting on an empty list is impossible,
         * so this command exits deleteShortlist process immediately
         * with no CommandException thrown
         */
        assertCommandSuccess(new DeleteShortlistedCandidateCommand(INDEX_FIRST), model, commandHistory,
                DeleteShortlistedCandidateCommand.MESSAGE_EMPTY_LIST, expectedModel);
    }

}
