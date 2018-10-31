package seedu.recruit.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.recruit.commons.core.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.recruit.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.recruit.testutil.TypicalPersons.CARL;
import static seedu.recruit.testutil.TypicalPersons.ELLE;
import static seedu.recruit.testutil.TypicalPersons.FIONA;
import static seedu.recruit.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Ignore;
import org.junit.Test;

import seedu.recruit.logic.CommandHistory;
import seedu.recruit.model.CompanyBook;
import seedu.recruit.model.Model;
import seedu.recruit.model.ModelManager;
import seedu.recruit.model.UserPrefs;
import seedu.recruit.model.candidate.CandidateContainsKeywordsPredicate;
import seedu.recruit.testutil.CandidateContainsKeywordsPredicateBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCandidateCommand}.
 */
public class FindCandidateCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new CompanyBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new CompanyBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    @Ignore
    public void equals() {
        CandidateContainsKeywordsPredicate firstPredicate =
                new CandidateContainsKeywordsPredicateBuilder("n/first").getCandidatePredicate();
        CandidateContainsKeywordsPredicate secondPredicate =
                new CandidateContainsKeywordsPredicateBuilder("n/second").getCandidatePredicate();

        FindCandidateCommand findFirstCommand = new FindCandidateCommand(firstPredicate);
        FindCandidateCommand findSecondCommand = new FindCandidateCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindCandidateCommand findFirstCommandCopy = new FindCandidateCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different candidate -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        CandidateContainsKeywordsPredicate predicate =
                new CandidateContainsKeywordsPredicateBuilder(" ").getCandidatePredicate();
        FindCandidateCommand command = new FindCandidateCommand(predicate);
        expectedModel.updateFilteredCandidateList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredCandidateList());
    }

    @Test
    @Ignore
    public void execute_multipleKeywords_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        CandidateContainsKeywordsPredicate predicate =
                new CandidateContainsKeywordsPredicateBuilder("n/Kurz n/Elle n/Kunz").getCandidatePredicate();
        FindCandidateCommand command = new FindCandidateCommand(predicate);
        expectedModel.updateFilteredCandidateList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL, ELLE, FIONA), model.getFilteredCandidateList());
    }
}
