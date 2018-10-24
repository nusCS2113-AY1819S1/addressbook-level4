package seedu.address.logic.commands;

//@@author jieliangang
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalEvents.getTypicalEventList;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EVENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_EVENT;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.event.Event;
import seedu.address.model.person.Person;

public class RemoveCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalEventList(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Person personChosen = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Event eventChosen = model.getFilteredEventList().get(INDEX_THIRD_EVENT.getZeroBased());
        String personName = personChosen.getName().toString();

        RemoveCommand removeCommand = new RemoveCommand(INDEX_FIRST_PERSON, INDEX_THIRD_EVENT);

        String expectedMessage = String.format(RemoveCommand.MESSAGE_REMOVE_PERSON_SUCCESS,
                personChosen.getName(), eventChosen.getEventName());

        Event eventUpdated = eventChosen;
        eventUpdated.getAttendees().removeName(personName);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), model.getEventList(), new UserPrefs());
        expectedModel.updateEvent(eventUpdated, eventChosen);
        expectedModel.commitAddressBook();

        assertCommandSuccess(removeCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        RemoveCommand removeCommand = new RemoveCommand(outOfBoundIndex, INDEX_FIRST_EVENT);

        assertCommandFailure(removeCommand, model, commandHistory, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidEventIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredEventList().size() + 1);
        RemoveCommand removeCommand = new RemoveCommand(INDEX_FIRST_PERSON, outOfBoundIndex);

        assertCommandFailure(removeCommand, model, commandHistory, Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidBothIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndexPerson = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        Index outOfBoundIndexEvent = Index.fromOneBased(model.getFilteredEventList().size() + 1);
        RemoveCommand removeCommand = new RemoveCommand(outOfBoundIndexPerson, outOfBoundIndexEvent);

        assertCommandFailure(removeCommand, model, commandHistory, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_personNotInAttendee_throwsCommandException() {
        RemoveCommand removeCommand = new RemoveCommand(INDEX_SECOND_PERSON, INDEX_THIRD_EVENT);
        assertCommandFailure(removeCommand, model, commandHistory, RemoveCommand.MESSAGE_ABSENT_PERSON);
    }

    @Test
    public void execute_emptyAttendeeList_throwsCommandException() {
        RemoveCommand removeCommand = new RemoveCommand(INDEX_SECOND_PERSON, INDEX_FIRST_EVENT);
        assertCommandFailure(removeCommand, model, commandHistory, RemoveCommand.MESSAGE_ATTENDEE_EMPTY);
    }

    @Test
    public void equals() {

        RemoveCommand command1 = new RemoveCommand(INDEX_FIRST_PERSON, INDEX_FIRST_EVENT);
        RemoveCommand command2 = new RemoveCommand(INDEX_FIRST_PERSON, INDEX_THIRD_EVENT);

        // same object -> returns true
        assertTrue(command1.equals(command1));

        // same values -> returns true
        RemoveCommand command2Copy = new RemoveCommand(INDEX_FIRST_PERSON, INDEX_THIRD_EVENT);
        assertTrue(command2.equals(command2Copy));

        // different types -> returns false
        assertFalse(command1.equals(1));

        // null -> returns false
        assertFalse(command1.equals(null));

        // different event -> returns false
        assertFalse(command1.equals(command2));
    }


}
