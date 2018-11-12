package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalAccounts.getTypicalAccountList;
import static seedu.address.testutil.TypicalFileNames.TYPICAL_FILE_NAME_1;
import static seedu.address.testutil.TypicalFileNames.TYPICAL_FILE_NAME_2;
import static seedu.address.testutil.TypicalItems.getTypicalStockList;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import seedu.address.commons.events.model.OpenStockListVersionEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.account.Username;
import seedu.address.ui.testutil.EventsCollectorRule;

/**
 * Contains integration tests (interaction with the Model) for {@code OpenCommand}.
 */

public class OpenCommandTest {

    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private Model model;
    private Model expectedModel;

    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setup() {
        Username admin = new Username("admin");
        model = new ModelManager(getTypicalStockList(), new UserPrefs(), getTypicalAccountList());
        expectedModel = new ModelManager(getTypicalStockList(), new UserPrefs(), getTypicalAccountList());
        model.setLoggedInUser(admin);
        expectedModel.setLoggedInUser(admin);
    }

    @Test
    public void execute_validFileName_success() {
        assertExecutionSuccess(TYPICAL_FILE_NAME_1);
        assertExecutionSuccess(TYPICAL_FILE_NAME_2);
    }

    @Test
    public void equals() {
        OpenCommand saveFirstCommand = new OpenCommand(TYPICAL_FILE_NAME_1);
        OpenCommand saveSecondCommand = new OpenCommand(TYPICAL_FILE_NAME_2);

        // same object -> returns true
        assertTrue(saveFirstCommand.equals(saveFirstCommand));

        // same values -> returns true
        OpenCommand saveFirstCommandCopy = new OpenCommand(TYPICAL_FILE_NAME_1);
        assertTrue(saveFirstCommand.equals(saveFirstCommandCopy));

        // different types -> returns false
        assertFalse(saveFirstCommand.equals(1));

        // null -> returns false
        assertFalse(saveFirstCommand.equals(null));

        // different item -> returns false
        assertFalse(saveFirstCommand.equals(saveSecondCommand));
    }

    /**
     * Executes a {@code OpenCommand} with the given {@code index}, and checks that {@code JumpToListRequestEvent}
     * is raised with the correct index.
     */


    private void assertExecutionSuccess(String fileName) {
        OpenCommand saveCommand = new OpenCommand(fileName);
        String expectedMessage = String.format(OpenCommand.MESSAGE_SUCCESS);

        assertCommandSuccess(saveCommand, model, commandHistory, expectedMessage, expectedModel);

        OpenStockListVersionEvent lastEvent = (OpenStockListVersionEvent) eventsCollectorRule.eventsCollector
                .getMostRecent();
        assertEquals(fileName, lastEvent.fileName);
    }

}

