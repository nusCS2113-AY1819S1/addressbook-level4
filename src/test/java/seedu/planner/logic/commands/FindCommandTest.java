package seedu.planner.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.planner.commons.core.Messages.MESSAGE_RECORDS_LISTED_OVERVIEW;
import static seedu.planner.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.planner.testutil.TypicalRecords.CHICKENRICE;
import static seedu.planner.testutil.TypicalRecords.MALA;
import static seedu.planner.testutil.TypicalRecords.ZT;
import static seedu.planner.testutil.TypicalRecords.getTypicalFinancialPlanner;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Ignore;
import org.junit.Test;

import seedu.planner.logic.CommandHistory;
import seedu.planner.model.Model;
import seedu.planner.model.ModelManager;
import seedu.planner.model.UserPrefs;
import seedu.planner.model.record.NameContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
@Ignore
public class FindCommandTest {
    private Model model = new ModelManager(getTypicalFinancialPlanner(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalFinancialPlanner(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void equals() {
        NameContainsKeywordsPredicate firstPredicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("first"));
        NameContainsKeywordsPredicate secondPredicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("second"));

        FindCommand findFirstCommand = new FindCommand(firstPredicate);
        FindCommand findSecondCommand = new FindCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindCommand findFirstCommandCopy = new FindCommand(firstPredicate);
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
        NameContainsKeywordsPredicate predicate = preparePredicate(" ");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredRecordList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredRecordList());
    }

    @Test
    public void execute_multipleKeywords_multipleRecordsFound() {
        String expectedMessage = String.format(MESSAGE_RECORDS_LISTED_OVERVIEW, 3);
        NameContainsKeywordsPredicate predicate = preparePredicate("ZT mala chicken");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredRecordList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ZT, MALA, CHICKENRICE), model.getFilteredRecordList());
    }

    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private NameContainsKeywordsPredicate preparePredicate(String userInput) {
        return new NameContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
