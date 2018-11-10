package systemtests;

import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_ALICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FROM;
import static seedu.address.testutil.TypicalEvents.EVENT_3;
import static seedu.address.testutil.TypicalEvents.EVENT_5;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EVENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_FOURTH_EVENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_EVENT;
import static seedu.address.testutil.TypicalPersons.KEYWORD_MATCHING_MEIER;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.RemoveCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.event.Event;
import seedu.address.model.person.Person;
import seedu.address.testutil.EventBuilder;


public class RemoveCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void remove() {
        Model model = getModel();

        /* ------------------------ Perform remove operations on the shown unfiltered list --------------------- */

        /* Case: remove a person to an event, command with leading spaces and trailing spaces
         * -> removed
         */
        Index indexPerson = INDEX_FIRST_PERSON;
        Index indexEvent = INDEX_THIRD_EVENT;

        String command = " " + RemoveCommand.COMMAND_WORD + "  " + indexPerson.getOneBased() + " "
                + PREFIX_FROM + " " + indexEvent.getOneBased();
        Event updatedEvent = new EventBuilder(EVENT_3).withAttendee().build();
        assertCommandSuccess(command, indexPerson, indexEvent, updatedEvent);

        /* Case: undo removing the last person in the list -> last event with attendee restored */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo removing the last person in the list -> last person removed again */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        model.updateEvent(getModel().getFilteredEventList().get(INDEX_THIRD_EVENT.getZeroBased()), updatedEvent);
        assertCommandSuccess(command, model, expectedResultMessage);


        /* ------------------ Performing remove operation while a filtered list is being shown ------------------ */
        /* Case: filtered person list, person and event index within bounds of person list and event list respectively
         * -> removed */
        showPersonsWithName(KEYWORD_MATCHING_MEIER);
        showEventsWithName("5");
        indexPerson = INDEX_FIRST_PERSON;
        indexEvent = INDEX_FIRST_EVENT;
        assertTrue(indexPerson.getZeroBased() < getModel().getFilteredPersonList().size());
        command = " " + RemoveCommand.COMMAND_WORD + "  " + indexPerson.getOneBased() + " "
                + PREFIX_FROM + " " + indexEvent.getOneBased();
        updatedEvent = new EventBuilder(EVENT_5).withAttendee(VALID_EMAIL_ALICE).build();
        assertCommandSuccess(command, indexPerson, indexEvent, updatedEvent);

        /* Case: filtered person list, event index within bounds of event list
         * but person index out of bounds of person list
         * -> rejected
         */
        showPersonsWithName(KEYWORD_MATCHING_MEIER);
        showAllEvents();
        int invalidIndex = getModel().getAddressBook().getPersonList().size();
        assertCommandFailure(RemoveCommand.COMMAND_WORD + " " + invalidIndex + " "
                        + PREFIX_FROM + indexEvent.getOneBased(),
                Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        /* Case: filtered event list, person index within bounds of person list
         * but event index out of bounds of event list
         * -> rejected
         */
        showAllPersons();
        showEventsWithName("1");
        invalidIndex = getModel().getEventList().getEventList().size();
        assertCommandFailure(RemoveCommand.COMMAND_WORD + " " + indexPerson.getOneBased() + " "
                        + PREFIX_FROM + invalidIndex,
                Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);

        /* ----------------------------- Performing invalid remove operation ---------------------------------- */

        showAllPersons();
        showAllEvents();
        /* Case: missing index -> rejected */
        assertCommandFailure(RemoveCommand.COMMAND_WORD + " " + PREFIX_FROM + indexEvent.getOneBased(),
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, RemoveCommand.MESSAGE_USAGE));
        assertCommandFailure(RemoveCommand.COMMAND_WORD + " " + indexPerson.getOneBased() + " " + PREFIX_FROM,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, RemoveCommand.MESSAGE_USAGE));

        /* Case: event has no attendees -> rejected */
        indexPerson = INDEX_FIRST_PERSON;
        indexEvent = INDEX_FIRST_EVENT;
        assertCommandFailure(RemoveCommand.COMMAND_WORD + " " + indexPerson.getOneBased()
                        + " " + PREFIX_FROM + indexEvent.getOneBased(), RemoveCommand.MESSAGE_ATTENDEE_EMPTY);

        /* Case: event does not have person -> rejected */
        Model expectedModel = getModel();
        indexPerson = INDEX_SECOND_PERSON;
        indexEvent = INDEX_FOURTH_EVENT;
        assertCommandFailure(RemoveCommand.COMMAND_WORD + " " + indexPerson.getOneBased()
                + " " + PREFIX_FROM + indexEvent.getOneBased(), RemoveCommand.MESSAGE_ABSENT_PERSON);
    }


    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String)} and in addition,<br>
     * 1. Asserts that result display box displays the success message of executing {@code RemoveCommand}.<br>
     *
     * @param indexPerson the index of the current model's filtered list
     * @param indexEvent the index of the current model's filtered list.
     * @see RemoveCommandSystemTest#assertCommandSuccess(String, Model, String)
     */
    private void assertCommandSuccess(String command, Index indexPerson, Index indexEvent, Event updatedEvent) {
        Model expectedModel = getModel();

        Person person = expectedModel.getFilteredPersonList().get(indexPerson.getZeroBased());
        Event event = expectedModel.getFilteredEventList().get(indexEvent.getZeroBased());

        String personName = person.getName().toString();
        String eventName = event.getEventName().toString();

        expectedModel.updateEvent(event, updatedEvent);

        assertCommandSuccess(command, expectedModel,
                String.format(RemoveCommand.MESSAGE_REMOVE_PERSON_SUCCESS, personName, eventName));
    }


    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays an empty string.<br>
     * 2. Asserts that the result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that selected cards remain unchanged.<br>
     * 4. Asserts that the command box has the default style class.<br>
     * Verifications 1 and 2 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     *
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     * @see AddressBookSystemTest#assertSelectedCardChanged(Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        assertSelectedCardUnchanged();
        assertSelectedEventCardUnchanged();
    }


    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays {@code command}.<br>
     * 2. Asserts that result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that selected card and status bar remain unchanged.<br>
     * 4. Asserts that the command box has the error style.<br>
     * Verifications 1 and 2 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     *
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
