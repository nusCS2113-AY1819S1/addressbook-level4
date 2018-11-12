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
import seedu.recruit.model.joboffer.JobOfferContainsFindKeywordsPredicate;
import seedu.recruit.testutil.JobOfferContainsFindKeywordsPredicateBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCandidateCommand}.
 */
public class FindJobOfferCommandTest {
    private Model model = new ModelManager(new CandidateBook(), getTypicalCompanyBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(new CandidateBook(), getTypicalCompanyBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    @Ignore
    public void equals() throws ParseException {
        JobOfferContainsFindKeywordsPredicate firstPredicate =
                new JobOfferContainsFindKeywordsPredicateBuilder("n/first").getJobOfferPredicate();
        JobOfferContainsFindKeywordsPredicate secondPredicate =
                new JobOfferContainsFindKeywordsPredicateBuilder("n/second").getJobOfferPredicate();

        FindJobOfferCommand findFirstCommand = new FindJobOfferCommand(firstPredicate);
        FindJobOfferCommand findSecondCommand = new FindJobOfferCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindJobOfferCommand findFirstCommandCopy = new FindJobOfferCommand(firstPredicate);
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
    public void execute_zeroKeywords_noJobOfferFound() throws ParseException {
        String expectedMessage = String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                FindJobOfferCommand.MESSAGE_USAGE);
        JobOfferContainsFindKeywordsPredicate predicate = (
                new JobOfferContainsFindKeywordsPredicateBuilder(" ")).getJobOfferPredicate();
        FindJobOfferCommand command = new FindJobOfferCommand(predicate);
        expectedModel.updateFilteredCompanyJobList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredCompanyJobList());
    }

    @Test
    @Ignore
    public void execute_multipleKeywords_multipleJobOffersFound() throws ParseException {
        String expectedMessage = "Company Book showing: findj j/CASHIER_AUDI j/CASHIER_BENTLEY j/CASHIER_CHEVROLET "
                + "j/CASHIER_DODGE" + String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 4);
        JobOfferContainsFindKeywordsPredicate predicate = new JobOfferContainsFindKeywordsPredicateBuilder(
                "j/CASHIER_AUDI j/CASHIER_BENTLEY j/CASHIER_CHEVROLET j/CASHIER_DODGE").getJobOfferPredicate();
        FindJobOfferCommand command = new FindJobOfferCommand(predicate);
        expectedModel.updateFilteredCompanyJobList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CASHIER_AUDI, CASHIER_BENTLEY, CASHIER_CHEVROLET, CASHIER_DODGE),
                model.getFilteredCompanyJobList());
    }
}
