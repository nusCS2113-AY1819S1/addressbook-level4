package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_DISTRIBUTORS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalDistributors.AHBENG;
import static seedu.address.testutil.TypicalDistributors.AHHUAT;
import static seedu.address.testutil.TypicalDistributors.AHKUAH;
import static seedu.address.testutil.TypicalDistributors.getTypicalDistributorBook;
import static seedu.address.testutil.TypicalProducts.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.TestStorage;
import seedu.address.model.UserDatabase;
import seedu.address.model.UserPrefs;
import seedu.address.model.distributor.DNameContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindDistributorCommand}.
 */
public class FindDistributorCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalDistributorBook(), new UserPrefs(),
            new UserDatabase(), new TestStorage());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), getTypicalDistributorBook(),
            new UserPrefs(), new UserDatabase(), new TestStorage());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void equals() {
        DNameContainsKeywordsPredicate firstPredicate =
                new DNameContainsKeywordsPredicate(Collections.singletonList("first"));
        DNameContainsKeywordsPredicate secondPredicate =
                new DNameContainsKeywordsPredicate(Collections.singletonList("second"));

        FindDistributorCommand findFirstCommand = new FindDistributorCommand(firstPredicate);
        FindDistributorCommand findSecondCommand = new FindDistributorCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindDistributorCommand findFirstCommandCopy = new FindDistributorCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different product -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noDistributorFound() {
        String expectedMessage = String.format(MESSAGE_DISTRIBUTORS_LISTED_OVERVIEW, 0);
        DNameContainsKeywordsPredicate predicate = preparePredicate(" ");
        FindDistributorCommand command = new FindDistributorCommand(predicate);
        expectedModel.updateFilteredDistributorList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredDistributorList());
    }

    @Test
    public void execute_multipleKeywords_multipleDistributorsFound() {
        String expectedMessage = String.format(MESSAGE_DISTRIBUTORS_LISTED_OVERVIEW, 3);
        DNameContainsKeywordsPredicate predicate = preparePredicate("Beng Huat Kuah");
        FindDistributorCommand command = new FindDistributorCommand(predicate);
        expectedModel.updateFilteredDistributorList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(AHBENG, AHHUAT, AHKUAH), model.getFilteredDistributorList());
    }

    /**
     * Parses {@code userInput} into a {@code DNameContainsKeywordsPredicate}.
     */
    private DNameContainsKeywordsPredicate preparePredicate(String userInput) {
        return new DNameContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
