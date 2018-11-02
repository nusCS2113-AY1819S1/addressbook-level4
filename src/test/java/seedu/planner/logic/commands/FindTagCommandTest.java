package seedu.planner.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.planner.commons.core.Messages.MESSAGE_RECORDS_LISTED_OVERVIEW;
import static seedu.planner.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.planner.testutil.TypicalRecords.CAIFAN;
import static seedu.planner.testutil.TypicalRecords.INDO;
import static seedu.planner.testutil.TypicalRecords.ZT;
import static seedu.planner.testutil.TypicalRecords.getTypicalFinancialPlanner;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Test;

import seedu.planner.logic.CommandHistory;
import seedu.planner.model.Model;
import seedu.planner.model.ModelManager;
import seedu.planner.model.UserPrefs;
import seedu.planner.model.record.TagsContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindTagCommand}.
 */
public class FindTagCommandTest {
    private Model model = new ModelManager(getTypicalFinancialPlanner(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalFinancialPlanner(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void equals() {
        TagsContainsKeywordsPredicate firstPredicate =
                new TagsContainsKeywordsPredicate(Collections.singletonList("first"));
        TagsContainsKeywordsPredicate secondPredicate =
                new TagsContainsKeywordsPredicate(Collections.singletonList("second"));

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

        // different record -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noRecordFound() {
        String expectedMessage = String.format(MESSAGE_RECORDS_LISTED_OVERVIEW, 0);
        TagsContainsKeywordsPredicate predicate = preparePredicate(" ");
        FindTagCommand command = new FindTagCommand(predicate);
        expectedModel.updateFilteredRecordList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredRecordList());
    }

    @Test
    public void execute_multipleKeywords_multipleRecordsFound() {
        String expectedMessage = String.format(MESSAGE_RECORDS_LISTED_OVERVIEW, 3);
        TagsContainsKeywordsPredicate predicate = preparePredicate("friends owesmoney");
        FindTagCommand command = new FindTagCommand(predicate);
        expectedModel.updateFilteredRecordList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(INDO, CAIFAN, ZT), model.getFilteredRecordList());
    }

    private TagsContainsKeywordsPredicate preparePredicate(String userInput) {
        return new TagsContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
