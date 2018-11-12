package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_LEAVES_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalLeave.REQUEST_1;
import static seedu.address.testutil.TypicalLeave.REQUEST_2;
import static seedu.address.testutil.TypicalLeave.getTypicalLeaveList;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.leave.NricContainsKeywordsPredicate;

//@@author Hafizuddin-NUS
/**
 * Contains integration tests (interaction with the Model) for {@code FilterListCommand}.
 */
public class FilterLeaveCommandTest {

    private Model model = new ModelManager(getTypicalLeaveList(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalLeaveList(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void equals() {
        NricContainsKeywordsPredicate firstPredicate =
                new NricContainsKeywordsPredicate(Collections.singletonList("first"));
        NricContainsKeywordsPredicate secondPredicate =
                new NricContainsKeywordsPredicate(Collections.singletonList("second"));

        FilterLeaveCommand findFirstCommand = new FilterLeaveCommand(firstPredicate);
        FilterLeaveCommand findSecondCommand = new FilterLeaveCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FilterLeaveCommand findFirstCommandCopy = new FilterLeaveCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_LEAVES_LISTED_OVERVIEW, 0);
        NricContainsKeywordsPredicate predicate = preparePredicate(" ");
        FilterLeaveCommand command = new FilterLeaveCommand(predicate);
        expectedModel.updateFilteredLeaveList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredLeaveList());
    }

    @Test
    public void execute_multipleKeywords_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_LEAVES_LISTED_OVERVIEW, 2);
        NricContainsKeywordsPredicate predicate = preparePredicate("S1234567A S1234597A");
        FilterLeaveCommand command = new FilterLeaveCommand(predicate);
        expectedModel.updateFilteredLeaveList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(REQUEST_1, REQUEST_2), model.getFilteredLeaveList());
    }

    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private NricContainsKeywordsPredicate preparePredicate(String userInput) {
        return new NricContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
