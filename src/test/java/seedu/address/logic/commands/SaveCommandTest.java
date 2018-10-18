package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalFileNames.TYPICAL_FILE_NAME_1;
import static seedu.address.testutil.TypicalFileNames.TYPICAL_FILE_NAME_2;
import static seedu.address.testutil.TypicalItems.getTypicalStockList;

import org.junit.Rule;
import org.junit.Test;

import seedu.address.commons.events.model.SaveStockListVersionEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.ui.testutil.EventsCollectorRule;

/**
 * Contains integration tests (interaction with the Model) for {@code SaveCommand}.
 */
public class SaveCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private Model model = new ModelManager(getTypicalStockList(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalStockList(), new UserPrefs());

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validFileName_success() {
        assertExecutionSuccess(TYPICAL_FILE_NAME_1);
        assertExecutionSuccess(TYPICAL_FILE_NAME_2);
    }

    @Test
    public void equals() {
        SaveCommand saveFirstCommand = new SaveCommand(TYPICAL_FILE_NAME_1);
        SaveCommand saveSecondCommand = new SaveCommand(TYPICAL_FILE_NAME_2);

        // same object -> returns true
        assertTrue(saveFirstCommand.equals(saveFirstCommand));

        // same values -> returns true
        SaveCommand saveFirstCommandCopy = new SaveCommand(TYPICAL_FILE_NAME_1);
        assertTrue(saveFirstCommand.equals(saveFirstCommandCopy));

        // different types -> returns false
        assertFalse(saveFirstCommand.equals(1));

        // null -> returns false
        assertFalse(saveFirstCommand.equals(null));

        // different item -> returns false
        assertFalse(saveFirstCommand.equals(saveSecondCommand));
    }

    /**
     * Executes a {@code SaveCommand} with the given {@code index}, and checks that {@code JumpToListRequestEvent}
     * is raised with the correct index.
     */
    private void assertExecutionSuccess(String fileName) {
        SaveCommand saveCommand = new SaveCommand(fileName);
        String expectedMessage = String.format(SaveCommand.MESSAGE_SUCCESS);

        assertCommandSuccess(saveCommand, model, commandHistory, expectedMessage, expectedModel);

        SaveStockListVersionEvent lastEvent = (SaveStockListVersionEvent) eventsCollectorRule.eventsCollector
                .getMostRecent();
        assertEquals(fileName, lastEvent.fileName);
    }
}
