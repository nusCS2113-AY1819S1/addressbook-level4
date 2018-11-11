package seedu.recruit.logic.commands;

import static org.junit.Assert.assertTrue;
import static seedu.recruit.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.recruit.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.recruit.logic.commands.CommandTestUtil.showCompanyAtIndex;
import static seedu.recruit.testutil.TypicalCompaniesAndJobOffers.AUDI;
import static seedu.recruit.testutil.TypicalCompaniesAndJobOffers.CASHIER_AUDI;
import static seedu.recruit.testutil.TypicalCompaniesAndJobOffers.getTypicalCompanyBook;
import static seedu.recruit.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.recruit.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.recruit.testutil.TypicalPersons.BENSON;
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
import seedu.recruit.model.company.CompanyContainsFindKeywordsPredicate;
import seedu.recruit.model.joboffer.JobOfferContainsFindKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model, ShortlistCandidateCommand)
 * and unit tests for {@code ShortlistCandidateCommand}.
 */
public class ShortlistCandidateCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalCompanyBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();
    private CommandHistory expectedCommandHistory = new CommandHistory();
    private UserPrefs userPrefs = new UserPrefs();

    @Test
    public void execute_validCandidateShortlisted_success() {
        Model expectedModel = new ModelManager(getTypicalAddressBook(), getTypicalCompanyBook(), new UserPrefs());

        Command firstStage = new ShortlistCandidateInitializationCommand();
        Command secondStage = new SelectCompanyCommand(INDEX_FIRST);
        Command thirdStage = new SelectJobCommand(INDEX_FIRST);
        // Since ALICE is already shortlisted, we will shortlist BENSON
        Command fourthStage = new SelectCandidateCommand(INDEX_SECOND);
        Command lastStage = new ShortlistCandidateCommand();
        try {
            firstStage.execute(expectedModel, expectedCommandHistory, userPrefs);
            secondStage.execute(expectedModel, expectedCommandHistory, userPrefs);
            thirdStage.execute(expectedModel, expectedCommandHistory, userPrefs);
            fourthStage.execute(expectedModel, expectedCommandHistory, userPrefs);
            lastStage.execute(expectedModel, expectedCommandHistory, userPrefs);
        } catch (CommandException | ParseException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }

        String expectedMessage = String.format(ShortlistCandidateCommand.MESSAGE_SUCCESS,
                BENSON.getName().fullName, CASHIER_AUDI.getJob().value, AUDI.getCompanyName().value);

        try {
            firstStage.execute(model, commandHistory, userPrefs);
            secondStage.execute(model, commandHistory, userPrefs);
            thirdStage.execute(model, commandHistory, userPrefs);
            fourthStage.execute(model, commandHistory, userPrefs);
        } catch (CommandException | ParseException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }

        ShortlistCandidateCommand shortlistCandidateCommand = new ShortlistCandidateCommand();
        assertCommandSuccess(shortlistCandidateCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateCandidateShortlisted_throwsCommandException() {

        Command firstStage = new ShortlistCandidateInitializationCommand();
        Command secondStage = new SelectCompanyCommand(INDEX_FIRST);
        Command thirdStage = new SelectJobCommand(INDEX_FIRST);
        // Since ALICE is already shortlisted, we will try to shortlist ALICE again -> rejected
        Command fourthStage = new SelectCandidateCommand(INDEX_FIRST);

        String expectedMessage = String.format(ShortlistCandidateCommand.MESSAGE_DUPLICATE_CANDIDATE_SHORTLISTED,
                CASHIER_AUDI.getJob().value, AUDI.getCompanyName().value)
                + ShortlistCandidateCommand.MESSAGE_USAGE;

        try {
            firstStage.execute(model, commandHistory, userPrefs);
            secondStage.execute(model, commandHistory, userPrefs);
            thirdStage.execute(model, commandHistory, userPrefs);
            fourthStage.execute(model, commandHistory, userPrefs);
        } catch (CommandException | ParseException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }

        ShortlistCandidateCommand shortlistCandidateCommand = new ShortlistCandidateCommand();
        assertCommandFailure(shortlistCandidateCommand, model, commandHistory, expectedMessage);
        Model expectedModel = new ModelManager(getTypicalAddressBook(), getTypicalCompanyBook(), new UserPrefs());
        assertCommandSuccess(new CancelCommand(ShortlistCandidateInitializationCommand.COMMAND_WORD), model,
                commandHistory, String.format(CancelCommand.MESSAGE_SUCCESS,
                        ShortlistCandidateInitializationCommand.COMMAND_WORD), expectedModel);
    }

    @Test
    public void execute_shortlistBlacklistedCandidate_throwsCommandException() {

        // blacklist BENSON
        Command blacklist = new BlacklistCommand(true, INDEX_SECOND);
        try {
            blacklist.execute(model, commandHistory, userPrefs);
        } catch (CommandException | ParseException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }

        Command firstStage = new ShortlistCandidateInitializationCommand();
        Command secondStage = new SelectCompanyCommand(INDEX_FIRST);
        Command thirdStage = new SelectJobCommand(INDEX_FIRST);
        // Since BENSON is blacklisted, we will try to shortlist BENSON -> rejected
        Command fourthStage = new SelectCandidateCommand(INDEX_SECOND);

        String expectedMessage = SelectCandidateCommand.MESSAGE_SELECT_PERSON_FAILURE_DUE_TO_BLACKLIST_TAG
                + SelectCandidateCommand.MESSAGE_USAGE;

        try {
            firstStage.execute(model, commandHistory, userPrefs);
            secondStage.execute(model, commandHistory, userPrefs);
            thirdStage.execute(model, commandHistory, userPrefs);
        } catch (CommandException | ParseException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }

        assertCommandFailure(fourthStage, model, commandHistory, expectedMessage);

        Model expectedModel = new ModelManager(getTypicalAddressBook(), getTypicalCompanyBook(), new UserPrefs());
        try {
            Command expectedBlacklist = new BlacklistCommand(true, INDEX_SECOND);
            expectedBlacklist.execute(expectedModel, commandHistory, userPrefs);
        } catch (CommandException | ParseException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
        assertCommandSuccess(new CancelCommand(ShortlistCandidateInitializationCommand.COMMAND_WORD), model,
                commandHistory, String.format(CancelCommand.MESSAGE_SUCCESS,
                        ShortlistCandidateInitializationCommand.COMMAND_WORD), expectedModel);
    }


    @Test
    public void execute_invalidIndexOfJobOfferInFilteredJobListAfterSelectingCompany_throwsCommandException() {
        Model expectedModel = new ModelManager(getTypicalAddressBook(), getTypicalCompanyBook(), new UserPrefs());
        assertCommandSuccess(new ShortlistCandidateInitializationCommand(), model, commandHistory,
                ShortlistCandidateInitializationCommand.MESSAGE_ENTERING_SHORTLIST_PROCESS
                        + ShortlistCandidateInitializationCommand.MESSAGE_NEXT_STEP
                        + SelectCompanyCommand.MESSAGE_USAGE, expectedModel);

        showCompanyAtIndex(expectedModel, INDEX_FIRST);
        HashMap<String, List<String>> keywordsList = new HashMap<>();
        List<String> companyName = new ArrayList<>();
        companyName.add(AUDI.getCompanyName().toString());
        keywordsList.put("CompanyName", companyName);
        assertCommandSuccess(new FindCompanyCommand(new CompanyContainsFindKeywordsPredicate(keywordsList)),
                model, commandHistory, String.format(Messages.MESSAGE_COMPANIES_LISTED_OVERVIEW,
                        expectedModel.getFilteredCompanyList().size())
                        + ShortlistCandidateInitializationCommand.MESSAGE_NEXT_STEP
                        + SelectCompanyCommand.MESSAGE_USAGE, expectedModel);

        assertCommandSuccess(new SelectCompanyCommand(INDEX_FIRST), model, commandHistory,
                String.format(SelectCompanyCommand.MESSAGE_SELECT_COMPANY_SUCCESS,
                        INDEX_FIRST.getOneBased()) + SelectCompanyCommand.MESSAGE_SELECT_COMPANY_SUCCESS_NEXT_STEP
                        + SelectJobCommand.MESSAGE_USAGE, expectedModel);

        model.updateFilteredCompanyJobList(new JobOfferContainsFindKeywordsPredicate(keywordsList));
        // Company AUDI only has 3 jobs Cashier, Salesperson, Manager.
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredCompanyJobList().size() + 1);
        // ensures that outOfBoundIndex is still in bounds of company job list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getCompanyBook().getCompanyJobList().size());

        SelectJobCommand selectJobCommand = new SelectJobCommand(outOfBoundIndex);
        assertCommandFailure(selectJobCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_JOB_OFFER_DISPLAYED_INDEX);

        assertCommandSuccess(new CancelCommand(ShortlistCandidateInitializationCommand.COMMAND_WORD), model,
                commandHistory, String.format(CancelCommand.MESSAGE_SUCCESS,
                        ShortlistCandidateInitializationCommand.COMMAND_WORD), expectedModel);
    }

}
