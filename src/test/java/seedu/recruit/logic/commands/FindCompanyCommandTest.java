package seedu.recruit.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.recruit.commons.core.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.recruit.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.recruit.testutil.TypicalCompaniesAndJobOffers.AUDI;
import static seedu.recruit.testutil.TypicalCompaniesAndJobOffers.BENTLEY;
import static seedu.recruit.testutil.TypicalCompaniesAndJobOffers.CHEVROLET;
import static seedu.recruit.testutil.TypicalCompaniesAndJobOffers.getTypicalCompanyBook;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Ignore;
import org.junit.Test;

import seedu.recruit.commons.core.Messages;
import seedu.recruit.logic.CommandHistory;
import seedu.recruit.logic.parser.exceptions.ParseException;
import seedu.recruit.model.CandidateBook;
import seedu.recruit.model.Model;
import seedu.recruit.model.ModelManager;
import seedu.recruit.model.UserPrefs;
import seedu.recruit.model.company.CompanyContainsFindKeywordsPredicate;
import seedu.recruit.testutil.CompanyContainsFindKeywordsPredicateBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCandidateCommand}.
 */
public class FindCompanyCommandTest {
    private Model model = new ModelManager(new CandidateBook(), getTypicalCompanyBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(new CandidateBook(), getTypicalCompanyBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    @Ignore
    public void equals() throws ParseException {
        CompanyContainsFindKeywordsPredicate firstPredicate =
                new CompanyContainsFindKeywordsPredicateBuilder("n/first").getCompanyPredicate();
        CompanyContainsFindKeywordsPredicate secondPredicate =
                new CompanyContainsFindKeywordsPredicateBuilder("n/second").getCompanyPredicate();

        FindCompanyCommand findFirstCommand = new FindCompanyCommand(firstPredicate);
        FindCompanyCommand findSecondCommand = new FindCompanyCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindCompanyCommand findFirstCommandCopy = new FindCompanyCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        //different person -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    @Ignore
    public void execute_zeroKeywords_noCompanyFound() throws ParseException {
        //String expectedMessage = "Company Book showing: findC  \n"
        //  + String.format(MESSAGE_COMPANIES_LISTED_OVERVIEW, 0);
        String expectedMessage = String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                FindCompanyCommand.MESSAGE_USAGE);
        CompanyContainsFindKeywordsPredicate predicate =
                new CompanyContainsFindKeywordsPredicateBuilder(" ").getCompanyPredicate();
        FindCompanyCommand command = new FindCompanyCommand(predicate);
        expectedModel.updateFilteredCompanyList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredCompanyList());
    }

    @Test
    @Ignore
    public void execute_multipleKeywords_multipleCompaniesFound() throws ParseException {
        String expectedMessage = "Company Book showing: findC c/Audi c/BENTLEY c/CHEVROLET"
                + String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        CompanyContainsFindKeywordsPredicate predicate = new CompanyContainsFindKeywordsPredicateBuilder(
                "c/Audi c/BENTLEY c/CHEVROLET").getCompanyPredicate();
        FindCompanyCommand command = new FindCompanyCommand(predicate);
        expectedModel.updateFilteredCompanyList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(AUDI, BENTLEY, CHEVROLET), model.getFilteredCompanyList());
    }
}
