package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalSchedules.CS1231;

import org.junit.Rule;
import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Schedule;
import seedu.address.ui.testutil.EventsCollectorRule;

//@@Author: driedmelon

/**
 * Contains integration tests (interaction with the Model) for {@code ScheduleCommand}.
 */

public class ScheduleCommandTest {

    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void equals() {
        ScheduleCommand scheduleFirstCommand = new ScheduleCommand(CS1231, INDEX_FIRST_PERSON);

        // same object -> returns true
        assertTrue(scheduleFirstCommand.equals(scheduleFirstCommand));

        // different types -> returns false
        assertFalse(scheduleFirstCommand.equals(1));

        // null -> returns false
        assertFalse(scheduleFirstCommand.equals(null));
    }

    /**
     * Executes a {@code ScheduleCommand} with the given {@code schedule and index},
     * and checks that {@code JumpToListRequestEvent} is raised with the correct index.
     */
    private void assertExecutionSuccess(Schedule schedule, Index index) {
        ScheduleCommand scheduleCommand = new ScheduleCommand(schedule, index);
        String expectedMessage = String.format(ScheduleCommand.MESSAGE_SUCCESS, index.getOneBased());

        assertCommandSuccess(scheduleCommand, model, commandHistory, expectedMessage, expectedModel);

        JumpToListRequestEvent lastEvent = (JumpToListRequestEvent) eventsCollectorRule.eventsCollector.getMostRecent();
        assertEquals(index, Index.fromZeroBased(lastEvent.targetIndex));
    }

    /**
     * Executes a {@code ScheduleCommand} with the given {@code schedule and index},
     * and checks that a {@code CommandException is thrown with the {@code expectedMessage}.
     */
    private void assertExecutionFailure(Schedule schedule, Index index, String expectedMessage) {
        ScheduleCommand scheduleCommand = new ScheduleCommand(schedule, index);
        assertCommandFailure(scheduleCommand, model, commandHistory, expectedMessage);
        assertTrue(eventsCollectorRule.eventsCollector.isEmpty());
    }
}
