package seedu.recruit.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.recruit.commons.core.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.recruit.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.recruit.testutil.TypicalCompaniesAndJobOffers.CASHIER_AUDI;
import static seedu.recruit.testutil.TypicalCompaniesAndJobOffers.CASHIER_BENTLEY;
import static seedu.recruit.testutil.TypicalCompaniesAndJobOffers.CASHIER_CHEVROLET;
import static seedu.recruit.testutil.TypicalCompaniesAndJobOffers.CASHIER_DODGE;
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
import seedu.recruit.model.joboffer.JobOfferContainsFilterKeywordsPredicate;
import seedu.recruit.testutil.JobOfferContainsFilterKeywordsPredicateBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCandidateCommand}.
 */
public class FilterJobOfferCommandTest {
    private Model model = new ModelManager(new CandidateBook(), getTypicalCompanyBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(new CandidateBook(), getTypicalCompanyBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    @Ignore
    public void equals() throws ParseException {
        JobOfferContainsFilterKeywordsPredicate firstPredicate =
                new JobOfferContainsFilterKeywordsPredicateBuilder("n/first").getJobOfferPredicate();
        JobOfferContainsFilterKeywordsPredicate secondPredicate =
                new JobOfferContainsFilterKeywordsPredicateBuilder("n/second").getJobOfferPredicate();

        FilterJobOfferCommand filterFirstCommand = new FilterJobOfferCommand(firstPredicate);
        FilterJobOfferCommand filterSecondCommand = new FilterJobOfferCommand(secondPredicate);

        // same object -> returns true
        assertTrue(filterFirstCommand.equals(filterFirstCommand));

        // same values -> returns true
        FilterJobOfferCommand findFirstCommandCopy = new FilterJobOfferCommand(firstPredicate);
        assertTrue(filterFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(filterFirstCommand.equals(1));

        // null -> returns false
        assertFalse(filterFirstCommand.equals(null));

        //different person -> returns false
        assertFalse(filterFirstCommand.equals(filterSecondCommand));
    }

    @Test
    @Ignore
    public void execute_zeroKeywords_noJobOfferFound() throws ParseException {
        String expectedMessage = String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                FilterJobOfferCommand.MESSAGE_USAGE);
        JobOfferContainsFilterKeywordsPredicate predicate = (
                new JobOfferContainsFilterKeywordsPredicateBuilder(" ")).getJobOfferPredicate();
        FilterJobOfferCommand command = new FilterJobOfferCommand(predicate);
        expectedModel.updateFilteredCompanyJobList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredCompanyJobList());
    }

    @Test
    @Ignore
    public void execute_multipleKeywords_multipleJobOffersFound() throws ParseException {
        String expectedMessage = "Company Book showing: findj j/CASHIER_AUDI j/CASHIER_BENTLEY j/CASHIER_CHEVROLET "
                + "j/CASHIER_DODGE" + String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 4);
        JobOfferContainsFilterKeywordsPredicate predicate = new JobOfferContainsFilterKeywordsPredicateBuilder(
                "j/CASHIER_AUDI j/CASHIER_BENTLEY j/CASHIER_CHEVROLET j/CASHIER_DODGE").getJobOfferPredicate();
        FilterJobOfferCommand command = new FilterJobOfferCommand(predicate);
        expectedModel.updateFilteredCompanyJobList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CASHIER_AUDI, CASHIER_BENTLEY, CASHIER_CHEVROLET, CASHIER_DODGE),
                model.getFilteredCompanyJobList());
    }
}
