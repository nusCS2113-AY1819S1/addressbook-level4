package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalDistributors.getTypicalDistributorBook;
import static seedu.address.testutil.TypicalProducts.GRAPE;
import static seedu.address.testutil.TypicalProducts.ORANGE;
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
import seedu.address.model.product.TagContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindTagDistributorCommand}.
 */
public class FindTagCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalDistributorBook(), new UserPrefs(),
            new UserDatabase(), new TestStorage());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), getTypicalDistributorBook(),
            new UserPrefs(), new UserDatabase(), new TestStorage());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void equals() {
        TagContainsKeywordsPredicate firstPredicate =
                new TagContainsKeywordsPredicate(Collections.singletonList("first"));
        TagContainsKeywordsPredicate secondPredicate =
                new TagContainsKeywordsPredicate(Collections.singletonList("second"));

        FindTagCommand findFirstCommand = new FindTagCommand(firstPredicate);
        FindTagCommand findSecondCommand = new FindTagCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindTagCommand findFirstCommandCopy = new FindTagCommand(firstPredicate);
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
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        TagContainsKeywordsPredicate predicate = preparePredicate(" ");
        FindTagCommand command = new FindTagCommand(predicate);
        expectedModel.updateFilteredProductList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredProductList());
    }

    @Test
    public void execute_multipleKeywords_multipleDistributorsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 2);
        TagContainsKeywordsPredicate predicate = preparePredicate("healthy sweet");
        FindTagCommand command = new FindTagCommand(predicate);
        expectedModel.updateFilteredProductList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ORANGE, GRAPE), model.getFilteredProductList());
    }

    /**
     * Parses {@code userInput} into a {@code DTagContainsKeywordsPredicate}.
     */
    private TagContainsKeywordsPredicate preparePredicate(String userInput) {
        return new TagContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
