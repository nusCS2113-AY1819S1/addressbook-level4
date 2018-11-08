package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalEvents.getTypicalEventList;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EVENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_EVENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_EVENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.event.Attendees;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventSingleDisplayPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.PersonAttendingEventPredicate;
import seedu.address.testutil.EventBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ViewAttendeesCommandTest {

    private Model model;
    private Model expectedModel;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), getTypicalEventList(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), model.getEventList(), new UserPrefs());

        // First event to update
        Person personChosen = model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        Event eventChosen = model.getFilteredEventList().get(INDEX_FIRST_EVENT.getZeroBased());
        String personEmail = personChosen.getEmail().toString();

        Attendees attendeesChosen = eventChosen.getAttendees();
        Attendees attendeesUpdated = attendeesChosen.createAttendeesWithAddedEmail(personEmail);
        Set<String> setUpdated = attendeesUpdated.getAttendeesSet();
        Event eventUpdated = new EventBuilder(eventChosen).withAttendee(setUpdated).build();

        // Second event to update
        Person personChosenTwo = model.getFilteredPersonList().get(INDEX_THIRD_PERSON.getZeroBased());
        Event eventChosenTwo = model.getFilteredEventList().get(INDEX_SECOND_EVENT.getZeroBased());
        String personEmailTwo = personChosenTwo.getEmail().toString();

        Attendees attendeesChosenTwo = eventChosenTwo.getAttendees();
        Attendees attendeesUpdatedTwo = attendeesChosenTwo
                .createAttendeesWithAddedEmail(personEmail)
                .createAttendeesWithAddedEmail(personEmailTwo);
        Set<String> setUpdatedTwo = attendeesUpdatedTwo.getAttendeesSet();
        Event eventUpdatedTwo = new EventBuilder(eventChosenTwo).withAttendee(setUpdatedTwo).build();

        model.updateEvent(eventChosen, eventUpdated);
        model.updateEvent(eventChosenTwo, eventUpdatedTwo);
        model.commitEventList();

        expectedModel.updateEvent(eventChosen, eventUpdated);
        expectedModel.updateEvent(eventChosenTwo, eventUpdatedTwo);
        expectedModel.commitEventList();
    }

    @Test
    public void equals() {

        ViewAttendeesCommand viewAttendeesCommandOne = new ViewAttendeesCommand(INDEX_FIRST_EVENT);
        ViewAttendeesCommand viewAttendeesCommandTwo = new ViewAttendeesCommand(INDEX_SECOND_EVENT);

        // same object -> returns true
        assertTrue(viewAttendeesCommandOne.equals(viewAttendeesCommandOne));

        // different types -> returns false
        assertFalse(viewAttendeesCommandOne.equals(1));

        // null -> returns false
        assertFalse(viewAttendeesCommandOne.equals(null));

        // different person -> returns false
        assertFalse(viewAttendeesCommandOne.equals(viewAttendeesCommandTwo));

        // same values -> returns true
        ViewAttendeesCommand viewAttendeesCommandCopy = new ViewAttendeesCommand(INDEX_FIRST_EVENT);
        assertEquals(viewAttendeesCommandOne, viewAttendeesCommandCopy);

    }

    @Test
    public void execute_eventContainsAttendee_success() {
        Event eventChosen = model.getFilteredEventList().get(INDEX_FIRST_EVENT.getZeroBased());

        EventSingleDisplayPredicate eventPredicate = new EventSingleDisplayPredicate(eventChosen);
        PersonAttendingEventPredicate personPredicate = new PersonAttendingEventPredicate(eventChosen);
        expectedModel.updateFilteredEventList(eventPredicate);
        expectedModel.updateFilteredPersonList(personPredicate);

        ViewAttendeesCommand viewAttendeesCommand = new ViewAttendeesCommand(INDEX_FIRST_EVENT);

        String expectedMessage = String.format(ViewAttendeesCommand.MESSAGE_SUCCESS, INDEX_FIRST_EVENT.getOneBased());

        assertCommandSuccess(viewAttendeesCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_eventContainsMultipleAttendees_success() {
        Event eventChosen = model.getFilteredEventList().get(INDEX_SECOND_EVENT.getZeroBased());

        EventSingleDisplayPredicate eventPredicate = new EventSingleDisplayPredicate(eventChosen);
        PersonAttendingEventPredicate personPredicate = new PersonAttendingEventPredicate(eventChosen);
        expectedModel.updateFilteredEventList(eventPredicate);
        expectedModel.updateFilteredPersonList(personPredicate);

        ViewAttendeesCommand viewAttendeesCommand = new ViewAttendeesCommand(INDEX_SECOND_EVENT);

        String expectedMessage = String.format(ViewAttendeesCommand.MESSAGE_SUCCESS, INDEX_SECOND_EVENT.getOneBased());

        assertCommandSuccess(viewAttendeesCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_eventHasNoAttendees_emptyPersonFilteredList() {
        Event eventChosen = model.getFilteredEventList().get(INDEX_THIRD_EVENT.getZeroBased());

        EventSingleDisplayPredicate eventPredicate = new EventSingleDisplayPredicate(eventChosen);
        PersonAttendingEventPredicate personPredicate = new PersonAttendingEventPredicate(eventChosen);
        expectedModel.updateFilteredEventList(eventPredicate);
        expectedModel.updateFilteredPersonList(personPredicate);

        ViewAttendeesCommand viewAttendeesCommand = new ViewAttendeesCommand(INDEX_THIRD_EVENT);

        String expectedMessage = String.format(ViewAttendeesCommand.MESSAGE_SUCCESS, INDEX_THIRD_EVENT.getOneBased());

        assertCommandSuccess(viewAttendeesCommand, model, commandHistory, expectedMessage, expectedModel);
    }
}
