package seedu.divelog.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.divelog.commons.core.Messages.MESSAGE_DIVE_LISTED_OVERVIEW;
import static seedu.divelog.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.divelog.testutil.TypicalDiveSessions.getTypicalDiveLog;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Test;

import seedu.divelog.logic.CommandHistory;
import seedu.divelog.model.Model;
import seedu.divelog.model.ModelManager;
import seedu.divelog.model.UserPrefs;
import seedu.divelog.model.dive.LocationContainsKeywordPredicate;
/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindCommandTest {
    private Model model = new ModelManager(getTypicalDiveLog(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalDiveLog(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void equals() {
        LocationContainsKeywordPredicate firstPredicate =
                new LocationContainsKeywordPredicate(Collections.singletonList("first"));
        LocationContainsKeywordPredicate secondPredicate =
                new LocationContainsKeywordPredicate(Collections.singletonList("second"));

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

        // different dive session -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noDiveSessionFound() {
        String expectedMessage = String.format(MESSAGE_DIVE_LISTED_OVERVIEW, 0);
        LocationContainsKeywordPredicate predicate = preparePredicate(" ");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredDiveList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredDiveList());
    }

    @Test
    public void execute_multipleKeywords_multipleDiveSessionsFound() {
        //TODO: REWRITE
    }

    /**
     * Parses {@code userInput} into a {@code LocationContainsKeywordPredicate}.
     */
    private LocationContainsKeywordPredicate preparePredicate(String userInput) {
        return new LocationContainsKeywordPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
