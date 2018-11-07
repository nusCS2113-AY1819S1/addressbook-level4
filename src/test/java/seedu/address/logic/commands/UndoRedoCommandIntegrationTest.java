package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalEvents.getTypicalEventList;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EVENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Set;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.AddressBook;
import seedu.address.model.EventList;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.event.Attendees;
import seedu.address.model.event.Event;
import seedu.address.model.person.Person;
import seedu.address.testutil.EventBuilder;

public class UndoRedoCommandIntegrationTest {

    private final Model model = new ModelManager(getTypicalAddressBook(), getTypicalEventList(), new UserPrefs());
    private final CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_invitePersonToEventDeleted_success() {
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
