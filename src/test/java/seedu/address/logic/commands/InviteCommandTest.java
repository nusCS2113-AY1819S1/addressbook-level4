package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalEvents.getTypicalEventList;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EVENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_EVENT;
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
import seedu.address.model.event.Attendees;
import seedu.address.model.event.Event;
import seedu.address.model.person.Person;
import seedu.address.testutil.EventBuilder;

//@@author jieliangang

public class InviteCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalEventList(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    private Event event1;
    private Event event2;
    private Event event3;

    @Before
    public void setup() {
        Set<String> set = new HashSet<>();
        set.add("Alice");

        event1 = new EventBuilder().build();
        event2 = new EventBuilder().withAttendee(set).build();
    }

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Person personChosen = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Event eventChosen = model.getFilteredEventList().get(INDEX_FIRST_EVENT.getZeroBased());
        String personName = personChosen.getName().toString();

        InviteCommand inviteCommand = new InviteCommand(INDEX_FIRST_PERSON, INDEX_FIRST_EVENT);

        String expectedMessage = String.format(InviteCommand.MESSAGE_INVITE_PERSON_SUCCESS,
                personChosen.getName(), eventChosen.getEventName());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                new EventList(model.getEventList()), new UserPrefs());

        Attendees attendeesChosen = eventChosen.getAttendees();
        Attendees attendeesUpdated = attendeesChosen.createAttendeesWithAddedName(personName);
        Set<String> setUpdated = attendeesUpdated.getAttendeesSet();
        Event eventUpdated = new EventBuilder(eventChosen).withAttendee(setUpdated).build();

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

    @Test
    public void execute_invalidBothIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndexPerson = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        Index outOfBoundIndexEvent = Index.fromOneBased(model.getFilteredEventList().size() + 1);
        InviteCommand inviteCommand = new InviteCommand(outOfBoundIndexPerson, outOfBoundIndexEvent);

        assertCommandFailure(inviteCommand, model, commandHistory, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    //TODO: On Filtered List

    @Test
    public void execute_duplicatePersonInEventList_throwsCommandException() {

        InviteCommand inviteCommand = new InviteCommand(INDEX_FIRST_PERSON, INDEX_THIRD_EVENT);

        assertCommandFailure(inviteCommand, model, commandHistory, InviteCommand.MESSAGE_DUPLICATE_PERSON);

    }

    @Test
    public void equals() {

        InviteCommand command1 = new InviteCommand(INDEX_FIRST_PERSON, INDEX_FIRST_EVENT);
        InviteCommand command2 = new InviteCommand(INDEX_FIRST_PERSON, INDEX_SECOND_EVENT);

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
    }



}
