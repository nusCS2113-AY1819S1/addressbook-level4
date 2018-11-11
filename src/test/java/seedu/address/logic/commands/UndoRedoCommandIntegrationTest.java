package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.model.EventModel.PREDICATE_SHOW_ALL_EVENTS;
import static seedu.address.testutil.TypicalEvents.getTypicalEventList;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EVENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Set;

import org.junit.Rule;
import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.model.AddressBook;
import seedu.address.model.EventList;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.event.AttendeeContainsEmailPredicate;
import seedu.address.model.event.Attendees;
import seedu.address.model.event.Event;
import seedu.address.model.person.Person;
import seedu.address.testutil.EditEventDescriptorBuilder;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.EventBuilder;
import seedu.address.testutil.PersonBuilder;
import seedu.address.ui.testutil.EventsCollectorRule;

/**
 * Contains integration tests (interaction with the Model and all undo/redoable commands)
 * for {@code UndoCommand} and {@code RedoCommand}. Since Undo and Redo are connected to all undo/redoable
 * commands, it is necessary to test it against the commands to ensure that it works as intended.
 * Handles integration testing for multiple chained commands (e.g. edit -> select -> undo -> redo)
 */
public class UndoRedoCommandIntegrationTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private final Model model = new ModelManager(getTypicalAddressBook(), getTypicalEventList(), new UserPrefs());
    private final CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_undoRedoAddEditDeleteEmployeeCommand_success() {
        // Instantiate a new model for each test for defensive purposes
        Model testModel = new ModelManager(model.getAddressBook(), model.getEventList(), new UserPrefs());

        // Add Command
        Person personToAdd = new PersonBuilder().withEmail(VALID_EMAIL_BOB).build();
        Model expectedModel = new ModelManager(model.getAddressBook(), model.getEventList(), new UserPrefs());
        expectedModel.addPerson(personToAdd);
        expectedModel.commitAddressBook();

        assertCommandSuccess(new AddCommand(personToAdd), testModel, commandHistory,
                String.format(AddCommand.MESSAGE_SUCCESS, personToAdd), expectedModel);

        // Edit Command
        Index indexAddedPerson = Index.fromOneBased(testModel.getFilteredPersonList().size());
        PersonBuilder personInList = new PersonBuilder(personToAdd);
        Person editedPerson = personInList.withPhone(VALID_PHONE_BOB)
                .withTags(VALID_TAG_HUSBAND).build();

        EditCommand.EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder()
                .withPhone(VALID_PHONE_BOB).withTags(VALID_TAG_HUSBAND).build();
        EditCommand editCommand = new EditCommand(indexAddedPerson, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedPerson);

        expectedModel.updatePerson(personToAdd, editedPerson);
        expectedModel.commitAddressBook();

        assertCommandSuccess(editCommand, testModel, commandHistory, expectedMessage, expectedModel);

        // Delete Command
        DeleteCommand deleteCommand = new DeleteCommand(indexAddedPerson);

        expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS, editedPerson);
        expectedModel.deletePerson(editedPerson);
        expectedModel.commitAddressBook();
        expectedModel.commitEventList();

        assertCommandSuccess(deleteCommand, testModel, commandHistory, expectedMessage, expectedModel);

        // Test that all three commands can be undone and redone successfully
        expectedModel.undo();
        assertCommandSuccess(new UndoCommand(), testModel, commandHistory,
                UndoCommand.MESSAGE_SUCCESS, expectedModel);

        expectedModel.undo();
        assertCommandSuccess(new UndoCommand(), testModel, commandHistory,
                UndoCommand.MESSAGE_SUCCESS, expectedModel);

        expectedModel.undo();
        assertCommandSuccess(new UndoCommand(), testModel, commandHistory,
                UndoCommand.MESSAGE_SUCCESS, expectedModel);

        expectedModel.redo();
        assertCommandSuccess(new RedoCommand(), testModel, commandHistory,
                RedoCommand.MESSAGE_SUCCESS, expectedModel);

        expectedModel.redo();
        assertCommandSuccess(new RedoCommand(), testModel, commandHistory,
                RedoCommand.MESSAGE_SUCCESS, expectedModel);

        expectedModel.redo();
        assertCommandSuccess(new RedoCommand(), testModel, commandHistory,
                RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_undoRedoAddEditDeleteEventCommand_success() {
        Model testModel = new ModelManager(model.getAddressBook(), model.getEventList(), new UserPrefs());

        // AddEvent command
        Event eventToAdd = new EventBuilder().build();
        Model expectedModel = new ModelManager(testModel.getAddressBook(), testModel.getEventList(), new UserPrefs());
        expectedModel.addEvent(eventToAdd);
        expectedModel.commitEventList();

        assertCommandSuccess(new AddEventCommand(eventToAdd), testModel, commandHistory,
                String.format(AddEventCommand.MESSAGE_SUCCESS, eventToAdd), expectedModel);

        // EditEvent command
        Index indexAddedEvent = Index.fromOneBased(testModel.getFilteredEventList().size());
        Event editedEvent = new EventBuilder().build();
        EditEventCommand.EditEventDescriptor descriptor = new EditEventDescriptorBuilder(editedEvent).build();
        EditEventCommand editEventCommand = new EditEventCommand(indexAddedEvent, descriptor);

        String expectedMessage = String.format(EditEventCommand.MESSAGE_EDIT_EVENT_SUCCESS, editedEvent);
        expectedModel.updateEvent(testModel.getFilteredEventList().get(indexAddedEvent.getZeroBased()),
                editedEvent);
        expectedModel.commitEventList();

        assertCommandSuccess(editEventCommand, testModel, commandHistory, expectedMessage, expectedModel);

        // DeleteEvent command
        DeleteEventCommand deleteEventCommand = new DeleteEventCommand(indexAddedEvent);

        expectedMessage = String.format(DeleteEventCommand.MESSAGE_CANCEL_EVENT_SUCCESS, editedEvent);

        expectedModel.deleteEvent(editedEvent);
        expectedModel.commitEventList();

        assertCommandSuccess(deleteEventCommand, testModel, commandHistory, expectedMessage, expectedModel);

        // Test that all three commands can be undone and redone successfully
        expectedModel.undo();
        assertCommandSuccess(new UndoCommand(), testModel, commandHistory,
                UndoCommand.MESSAGE_SUCCESS, expectedModel);

        expectedModel.undo();
        assertCommandSuccess(new UndoCommand(), testModel, commandHistory,
                UndoCommand.MESSAGE_SUCCESS, expectedModel);

        expectedModel.undo();
        assertCommandSuccess(new UndoCommand(), testModel, commandHistory,
                UndoCommand.MESSAGE_SUCCESS, expectedModel);

        expectedModel.redo();
        assertCommandSuccess(new RedoCommand(), testModel, commandHistory,
                RedoCommand.MESSAGE_SUCCESS, expectedModel);

        expectedModel.redo();
        assertCommandSuccess(new RedoCommand(), testModel, commandHistory,
                RedoCommand.MESSAGE_SUCCESS, expectedModel);

        expectedModel.redo();
        assertCommandSuccess(new RedoCommand(), testModel, commandHistory,
                RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_undoRedoEditSelectCommand_success() {
        // Test Path: Edit -> Select -> Undo -> Undo -> Redo

        Model testModel = new ModelManager(model.getAddressBook(), model.getEventList(), new UserPrefs());
        Person person = testModel.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        // Call Edit command
        PersonBuilder personInList = new PersonBuilder(person);
        Person editedPerson = personInList.withPhone(VALID_PHONE_BOB)
                .withTags(VALID_TAG_HUSBAND).build();

        EditCommand.EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder()
                .withPhone(VALID_PHONE_BOB).withTags(VALID_TAG_HUSBAND).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(testModel.getAddressBook()),
                new EventList(testModel.getEventList()), new UserPrefs());
        expectedModel.updatePerson(person, editedPerson);
        expectedModel.commitAddressBook();

        assertCommandSuccess(editCommand, testModel, commandHistory, expectedMessage, expectedModel);

        // Call Select Command
        SelectCommand selectCommand = new SelectCommand(INDEX_FIRST_PERSON);
        expectedMessage = String.format(SelectCommand.MESSAGE_SELECT_PERSON_SUCCESS, INDEX_FIRST_PERSON.getOneBased());

        Person personChosen = expectedModel.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        String personEmail = personChosen.getEmail().toString();
        AttendeeContainsEmailPredicate predicate = new AttendeeContainsEmailPredicate(personEmail);
        expectedModel.updateFilteredEventList(predicate);
        expectedModel.sortByDate();

        assertCommandSuccess(selectCommand, testModel, commandHistory, expectedMessage, expectedModel);

        JumpToListRequestEvent lastEvent = (JumpToListRequestEvent) eventsCollectorRule.eventsCollector.getMostRecent();
        assertEquals(INDEX_FIRST_PERSON, Index.fromZeroBased(lastEvent.targetIndex));

        // Undo and Redo should target the single Edit Command and ignore Select Command
        expectedModel.undo();
        expectedModel.updateFilteredEventList(PREDICATE_SHOW_ALL_EVENTS);
        assertCommandSuccess(new UndoCommand(), testModel, commandHistory,
                UndoCommand.MESSAGE_SUCCESS, expectedModel);

        assertCommandFailure(new UndoCommand(), testModel, commandHistory, UndoCommand.MESSAGE_FAILURE);

        expectedModel.redo();
        assertCommandSuccess(new RedoCommand(), testModel, commandHistory,
                RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_undoRedoInviteDeleteEmployee_success() {
        // Test Path: Invite -> Delete -> Undo -> Undo -> Redo -> Redo

        Person personChosen = model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        Event eventChosen = model.getFilteredEventList().get(INDEX_FIRST_EVENT.getZeroBased());
        String personEmail = personChosen.getEmail().toString();

        InviteCommand inviteCommand = new InviteCommand(INDEX_SECOND_PERSON, INDEX_FIRST_EVENT);

        String expectedMessage = String.format(InviteCommand.MESSAGE_INVITE_PERSON_SUCCESS,
                personChosen.getName(), eventChosen.getEventName());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                new EventList(model.getEventList()), new UserPrefs());

        Attendees attendeesChosen = eventChosen.getAttendees();
        Attendees attendeesUpdated = attendeesChosen.createAttendeesWithAddedEmail(personEmail);
        Set<String> setUpdated = attendeesUpdated.getAttendeesSet();
        Event eventUpdated = new EventBuilder(eventChosen).withAttendee(setUpdated).build();

        expectedModel.updateEvent(eventChosen, eventUpdated);
        expectedModel.commitEventList();

        assertCommandSuccess(inviteCommand, model, commandHistory, expectedMessage, expectedModel);

        DeleteCommand deleteCommand = new DeleteCommand(INDEX_SECOND_PERSON);
        String expectedMessage2 = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS, personChosen);

        expectedModel.deletePerson(personChosen);
        expectedModel.commitEventList();
        expectedModel.commitAddressBook();

        assertCommandSuccess(deleteCommand, model, commandHistory, expectedMessage2, expectedModel);
        assertFalse(model.getFilteredEventList()
                .get(INDEX_FIRST_EVENT.getZeroBased())
                .getAttendees()
                .hasPerson(personEmail));

        expectedModel.undo();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);
        assertTrue(model.getFilteredEventList()
                .get(INDEX_FIRST_EVENT.getZeroBased())
                .getAttendees()
                .hasPerson(personEmail));

        expectedModel.undo();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        expectedModel.redo();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
        assertTrue(model.getFilteredEventList()
                .get(INDEX_FIRST_EVENT.getZeroBased())
                .getAttendees()
                .hasPerson(personEmail));

        expectedModel.redo();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
        assertFalse(model.getFilteredEventList()
                .get(INDEX_FIRST_EVENT.getZeroBased())
                .getAttendees()
                .hasPerson(personEmail));
    }

}
