package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ATTENDEE_WITCH;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showEventAtIndex;
import static seedu.address.testutil.TypicalEvents.getTypicalEventManager;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EVENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_EVENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_EVENT;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.attendee.Attendee;
import seedu.address.model.event.Event;
import seedu.address.model.user.User;
import seedu.address.model.user.Username;
import seedu.address.testutil.EventBuilder;
import seedu.address.testutil.UserBuilder;


/**
 * Contains integration tests (interaction with the Model) for {@code RemoveAttendeeCommand}.
 */


public class RemoveAttendeeCommandTest {
    private Model model = new ModelManager(getTypicalEventManager(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        User user = new UserBuilder().build();
        model = new ModelManager(getTypicalEventManager(), new UserPrefs());
        model.logUser(user);
    }

    @Test
    public void execute_registeredEventUnfilteredList_success() {
        Username testUsername = new Username(VALID_ATTENDEE_WITCH);
        Event eventToRemoveAttendee = model.getFilteredEventList().get(INDEX_FIRST_EVENT.getZeroBased());
        assertTrue(eventToRemoveAttendee.getAttendance().contains(new Attendee(testUsername.toString())));
        RemoveAttendeeCommand removeAttendeeCommand =
                new RemoveAttendeeCommand(INDEX_FIRST_EVENT, testUsername);

        String expectedMessage =
                String.format(RemoveAttendeeCommand.MESSAGE_REMOVE_ATTENDEE_SUCCESS,
                        testUsername.toString(), INDEX_FIRST_EVENT.getOneBased());

        Event eventAttendeeRemoved = new EventBuilder(eventToRemoveAttendee)
                .withRemoveAttendees(testUsername.toString()).build();
        ModelManager expectedModel = new ModelManager(model.getEventManager(), new UserPrefs());
        expectedModel.logUser(new UserBuilder().build());
        expectedModel.updateEvent(eventToRemoveAttendee, eventAttendeeRemoved);
        expectedModel.commitEventManager();

        assertCommandSuccess(removeAttendeeCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_unregisteredEventUnfilteredList_throwsCommandException() {
        Username testUsername = new Username(VALID_ATTENDEE_WITCH);
        Event eventToRemoveAttendee = model.getFilteredEventList().get(INDEX_SECOND_EVENT.getZeroBased());
        assertFalse(eventToRemoveAttendee.getAttendance().contains(new Attendee(testUsername.toString())));
        RemoveAttendeeCommand removeAttendeeCommand =
                new RemoveAttendeeCommand(INDEX_SECOND_EVENT, testUsername);

        Event eventAttendeeRemoved =
                new EventBuilder(eventToRemoveAttendee).withRemoveAttendees(testUsername.toString()).build();
        model.updateEvent(eventToRemoveAttendee, eventAttendeeRemoved);
        model.commitEventManager();

        assertCommandFailure(removeAttendeeCommand, model, commandHistory,
                RemoveAttendeeCommand.MESSAGE_INVALID_ATTENDEE);
    }

    /**
     * 1. Removes attendee for a {@code Event} from a filtered list.
     * 2. Undo the removal of attendee.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously registered event in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the removal of attendee. This ensures {@code RedoCommand} removes an attendee for the event object
     * regardless of indexing.
     */
    @Test
    public void executeUndoRedo_registeredEventFilteredList_sameEventAttendeeRemoved() throws Exception {
        Username testUsername = new Username(VALID_ATTENDEE_WITCH);
        RemoveAttendeeCommand removeAttendeeCommand = new RemoveAttendeeCommand(INDEX_FIRST_EVENT, testUsername);
        Model expectedModel = new ModelManager(model.getEventManager(), new UserPrefs());

        showEventAtIndex(model, INDEX_THIRD_EVENT);
        Event eventToRemoveAttendee = model.getFilteredEventList().get(INDEX_FIRST_EVENT.getZeroBased());
        Event eventAttendeeRemoved =
                new EventBuilder(eventToRemoveAttendee).withRemoveAttendees(testUsername.toString()).build();
        expectedModel.logUser(new UserBuilder().build());
        expectedModel.updateEvent(eventToRemoveAttendee, eventAttendeeRemoved);
        expectedModel.commitEventManager();

        // removeAttendee -> removes attendee from
        // third event in unfiltered event list / first event in filtered event list
        removeAttendeeCommand.execute(model, commandHistory);

        // undo -> reverts addressbook back to previous state and filtered event list to show all persons
        expectedModel.undoEventManager();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        assertNotEquals(eventAttendeeRemoved, model.getFilteredEventList().get(INDEX_FIRST_EVENT.getZeroBased()));
        // redo -> remove attendee from same third event in unfiltered event list
        expectedModel.redoEventManager();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        Username testUsername = new Username(VALID_ATTENDEE_WITCH);
        RemoveAttendeeCommand removeAttendeeFirstCommand = new RemoveAttendeeCommand(INDEX_FIRST_EVENT, testUsername);
        RemoveAttendeeCommand removeAttendeeSecondCommand = new RemoveAttendeeCommand(INDEX_SECOND_EVENT, testUsername);

        // same object -> returns true
        assertTrue(removeAttendeeFirstCommand.equals(removeAttendeeFirstCommand));

        // same values -> returns true
        RemoveAttendeeCommand removeAttendeeFirstCommandCopy =
                new RemoveAttendeeCommand(INDEX_FIRST_EVENT, testUsername);
        assertTrue(removeAttendeeFirstCommand.equals(removeAttendeeFirstCommandCopy));

        // different types -> returns false
        assertFalse(removeAttendeeFirstCommand.equals(1));

        // null -> returns false
        assertFalse(removeAttendeeFirstCommand.equals(null));

        // different event -> returns false
        assertFalse(removeAttendeeFirstCommand.equals(removeAttendeeSecondCommand));
    }
}
