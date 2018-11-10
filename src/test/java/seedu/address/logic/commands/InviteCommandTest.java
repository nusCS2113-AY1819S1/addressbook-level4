package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BENSON;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalEvents.getTypicalEventList;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EVENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_EVENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_EVENT;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.model.AddressBook;
import seedu.address.model.EventList;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.event.Event;
import seedu.address.model.person.Person;
import seedu.address.testutil.EventBuilder;

//@@author jieliangang

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand)
 * and unit tests for InviteCommand.
 */
public class InviteCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalEventList(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    private Event event1;
    private Event event2;
    private Event event3;

    @Before
    public void setup() {
        Set<String> set = new HashSet<>();
        set.add(VALID_EMAIL_ALICE);

        event1 = new EventBuilder().build();
        event2 = new EventBuilder().withAttendee(set).build();
    }

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Person personChosen = model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        Event eventChosen = model.getFilteredEventList().get(INDEX_FIRST_EVENT.getZeroBased());

        InviteCommand inviteCommand = new InviteCommand(INDEX_SECOND_PERSON, INDEX_FIRST_EVENT);

        String expectedMessage = String.format(InviteCommand.MESSAGE_INVITE_PERSON_SUCCESS,
                personChosen.getName(), eventChosen.getEventName());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                new EventList(model.getEventList()), new UserPrefs());

        Event eventUpdated = new EventBuilder(eventChosen).withAttendee(VALID_EMAIL_BENSON).build();

        expectedModel.updateEvent(eventChosen, eventUpdated);
        expectedModel.commitEventList();

        assertCommandSuccess(inviteCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        InviteCommand inviteCommand = new InviteCommand(outOfBoundIndex, INDEX_FIRST_EVENT);

        assertCommandFailure(inviteCommand, model, commandHistory, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidEventIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredEventList().size() + 1);
        InviteCommand inviteCommand = new InviteCommand(INDEX_FIRST_PERSON, outOfBoundIndex);

        assertCommandFailure(inviteCommand, model, commandHistory, Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
    }

    //TODO: On Filtered List

    @Test
    public void execute_duplicatePersonInEventList_throwsCommandException() {

        InviteCommand inviteCommand = new InviteCommand(INDEX_FIRST_PERSON, INDEX_THIRD_EVENT);

        assertCommandFailure(inviteCommand, model, commandHistory, InviteCommand.MESSAGE_DUPLICATE_PERSON);

    }

    @Test
    public void execute_personEventClashesWithEventList_throwsCommandException() {

        Person personChosen = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Event eventChosen = model.getFilteredEventList().get(INDEX_FIRST_EVENT.getZeroBased());

        String expectedMessage = String.format(InviteCommand.MESSAGE_CLASH_EVENT,
                eventChosen.getEventName(), personChosen.getName());

        InviteCommand inviteCommand = new InviteCommand(INDEX_FIRST_PERSON, INDEX_FIRST_EVENT);

        assertCommandFailure(inviteCommand, model, commandHistory, expectedMessage);

    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        Person personChosen = model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        Event eventChosen = model.getFilteredEventList().get(INDEX_FIRST_EVENT.getZeroBased());

        InviteCommand inviteCommand = new InviteCommand(INDEX_SECOND_PERSON, INDEX_FIRST_EVENT);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                new EventList(model.getEventList()), new UserPrefs());

        Event eventUpdated = new EventBuilder(eventChosen).withAttendee(personChosen.getEmail().toString()).build();

        expectedModel.updateEvent(eventChosen, eventUpdated);
        expectedModel.commitEventList();

        // invite -> person invited
        inviteCommand.execute(model, commandHistory);

        // undo -> uninvite
        expectedModel.undoEventList();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> person invited again
        expectedModel.redoEventList();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        InviteCommand inviteCommand = new InviteCommand(outOfBoundIndex, INDEX_FIRST_EVENT);

        assertCommandFailure(inviteCommand, model, commandHistory, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        // single address book state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    @Test
    public void executeUndoRedo_invalidEventIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredEventList().size() + 1);
        InviteCommand inviteCommand = new InviteCommand(INDEX_FIRST_PERSON, outOfBoundIndex);

        assertCommandFailure(inviteCommand, model, commandHistory, Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);

        // single address book state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }


    @Test
    public void equals() {

        InviteCommand command1 = new InviteCommand(INDEX_FIRST_PERSON, INDEX_FIRST_EVENT);
        InviteCommand command2 = new InviteCommand(INDEX_FIRST_PERSON, INDEX_SECOND_EVENT);
        InviteCommand command3 = new InviteCommand(INDEX_SECOND_PERSON, INDEX_FIRST_EVENT);

        // same object -> returns true
        assertTrue(command1.equals(command1));

        // same values -> returns true
        InviteCommand command1Copy = new InviteCommand(INDEX_FIRST_PERSON, INDEX_FIRST_EVENT);
        assertTrue(command1.equals(command1Copy));

        // different types -> returns false
        assertFalse(command1.equals(1));

        // null -> returns false
        assertFalse(command1.equals(null));

        // different event -> returns false
        assertFalse(command1.equals(command2));
        assertFalse(command1.equals(command3));
    }

}
