package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_ITEMS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalAccounts.getTypicalAccountList;
import static seedu.address.testutil.TypicalItems.ARDUINO;
import static seedu.address.testutil.TypicalItems.RPLIDAR;
import static seedu.address.testutil.TypicalItems.getTypicalStockList;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.account.Username;
import seedu.address.model.item.TagContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code TagCommand}.
 */

public class TagCommandTest {
    private Model model = new ModelManager(getTypicalStockList(), new UserPrefs(), getTypicalAccountList());
    private Model expectedModel = new ModelManager(getTypicalStockList(), new UserPrefs(), getTypicalAccountList());
    private CommandHistory commandHistory = new CommandHistory();
    @Before
    public void setUp() {
        Username admin = new Username("admin");
        model = new ModelManager(getTypicalStockList(), new UserPrefs(), getTypicalAccountList());
        model.setLoggedInUser(admin);
    }
    @Test
    public void equals() {
        TagContainsKeywordsPredicate firstPredicate =
                new TagContainsKeywordsPredicate(Collections.singletonList("first"));
        TagContainsKeywordsPredicate secondPredicate =
                new TagContainsKeywordsPredicate(Collections.singletonList("second"));
        TagCommand tagFirstCommand = new TagCommand(firstPredicate);
        TagCommand tagSecondCommand = new TagCommand(secondPredicate);
        // same object -> returns true
        assertTrue(tagFirstCommand.equals(tagFirstCommand));
        // different types -> returns false
        assertFalse(tagFirstCommand.equals(1));
        // null -> returns false
        assertFalse(tagFirstCommand == (null));
        // different item -> returns false
        assertFalse(tagFirstCommand.equals(tagSecondCommand));
    }
    @Test
    public void executeZeroKeywordsNoItemFound() {
        String expectedMessage = String.format(MESSAGE_ITEMS_LISTED_OVERVIEW, 0);
        TagContainsKeywordsPredicate predicate = preparePredicate(" ");
        TagCommand command = new TagCommand(predicate);
        expectedModel.updateFilteredItemListByTag(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredItemList());
    }
    @Test
    public void executeMultipleKeywordsMultipleItemsFound() {
        String expectedMessage = String.format(MESSAGE_ITEMS_LISTED_OVERVIEW, 2);
        TagContainsKeywordsPredicate predicate = preparePredicate("Lab1 Lab2");
        TagCommand command = new TagCommand(predicate);
        expectedModel.updateFilteredItemListByTag(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ARDUINO, RPLIDAR), model.getFilteredItemList());
    }

    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */

    private TagContainsKeywordsPredicate preparePredicate(String userInput) {
        return new TagContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }

}
