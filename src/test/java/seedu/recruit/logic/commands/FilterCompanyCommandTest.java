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
import seedu.recruit.model.company.CompanyContainsFilterKeywordsPredicate;
import seedu.recruit.testutil.CompanyContainsFilterKeywordsPredicateBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCandidateCommand}.
 */
public class FilterCompanyCommandTest {
    private Model model = new ModelManager(new CandidateBook(), getTypicalCompanyBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(new CandidateBook(), getTypicalCompanyBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    @Ignore
    public void equals() throws ParseException {
        CompanyContainsFilterKeywordsPredicate firstPredicate =
                new CompanyContainsFilterKeywordsPredicateBuilder("n/first").getCompanyPredicate();
        CompanyContainsFilterKeywordsPredicate secondPredicate =
                new CompanyContainsFilterKeywordsPredicateBuilder("n/second").getCompanyPredicate();

        FilterCompanyCommand filterFirstCommand = new FilterCompanyCommand(firstPredicate);
        FilterCompanyCommand filterSecondCommand = new FilterCompanyCommand(secondPredicate);

        // same object -> returns true
        assertTrue(filterFirstCommand.equals(filterFirstCommand));

        // same values -> returns true
        FilterCompanyCommand filterFirstCommandCopy = new FilterCompanyCommand(firstPredicate);
        assertTrue(filterFirstCommand.equals(filterFirstCommandCopy));

        // different types -> returns false
        assertFalse(filterFirstCommand.equals(1));

        // null -> returns false
        assertFalse(filterFirstCommand.equals(null));

        //different person -> returns false
        assertFalse(filterFirstCommand.equals(filterSecondCommand));
    }

    @Test
    @Ignore
    public void execute_zeroKeywords_noCompanyFound() throws ParseException {
        String expectedMessage = String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                FindCompanyCommand.MESSAGE_USAGE);
        CompanyContainsFilterKeywordsPredicate predicate =
                new CompanyContainsFilterKeywordsPredicateBuilder(" ").getCompanyPredicate();
        FilterCompanyCommand command = new FilterCompanyCommand(predicate);
        expectedModel.updateFilteredCompanyList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredCompanyList());
    }

    @Test
    @Ignore
    public void execute_multipleKeywords_multipleCompaniesFound() throws ParseException {
        String expectedMessage = "Company Book showing: findC c/Audi c/BENTLEY c/CHEVROLET"
                + String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        CompanyContainsFilterKeywordsPredicate predicate = new CompanyContainsFilterKeywordsPredicateBuilder(
                "c/Audi c/BENTLEY c/CHEVROLET").getCompanyPredicate();
        FilterCompanyCommand command = new FilterCompanyCommand(predicate);
        expectedModel.updateFilteredCompanyList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(AUDI, BENTLEY, CHEVROLET), model.getFilteredCompanyList());
    }
}
