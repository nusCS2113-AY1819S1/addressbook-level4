package systemtests;

import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BENSON;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_CARL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TO;
import static seedu.address.testutil.TestUtil.getEvent;
import static seedu.address.testutil.TestUtil.getPerson;
import static seedu.address.testutil.TypicalEvents.EVENT_1;
import static seedu.address.testutil.TypicalEvents.EVENT_4;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EVENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_EVENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;
import static seedu.address.testutil.TypicalPersons.KEYWORD_MATCHING_MEIER;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.InviteCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.event.Event;
import seedu.address.model.person.Person;
import seedu.address.testutil.EventBuilder;


public class InviteCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void invite() {
        Model model = getModel();

        /* ------------------------ Perform invite operations on the shown unfiltered list --------------------- */

        /* Case: invite a person to an event, command with leading spaces and trailing spaces
         * -> invited
         */
        Index indexPerson = INDEX_SECOND_PERSON;
        Index indexEvent = INDEX_FIRST_EVENT;

        String command = " " + InviteCommand.COMMAND_WORD + "  " + indexPerson.getOneBased() + " "
                + PREFIX_TO + " " + indexEvent.getOneBased();
        Event updatedEvent = new EventBuilder(EVENT_1).withAttendee(VALID_EMAIL_BENSON).build();
        assertCommandSuccess(command, indexPerson, indexEvent, updatedEvent);

        /* Case: undo inviting the last person in the list -> last event with uninvited person restored */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo inviting the last person in the list -> last person invited again */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        model.updateEvent(getModel().getFilteredEventList().get(INDEX_FIRST_EVENT.getZeroBased()), updatedEvent);
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: invite another person to same event -> invited */
        indexPerson = INDEX_THIRD_PERSON;
        command = InviteCommand.COMMAND_WORD + "  " + indexPerson.getOneBased() + " "
                + PREFIX_TO + indexEvent.getOneBased();
        updatedEvent = new EventBuilder(EVENT_1).withAttendee(VALID_EMAIL_BENSON, VALID_EMAIL_CARL).build();
        assertCommandSuccess(command, indexPerson, indexEvent, updatedEvent);


        /* ------------------ Performing invite operation while a filtered list is being shown ------------------ */
        /* Case: filtered person list, person and event index within bounds of person list and event list respectively
         * -> invited */
        showPersonsWithName(KEYWORD_MATCHING_MEIER);
        showEventsWithName("4");
        indexPerson = INDEX_FIRST_PERSON;
        indexEvent = INDEX_FIRST_EVENT;
        assertTrue(indexPerson.getZeroBased() < getModel().getFilteredPersonList().size());
        command = " " + InviteCommand.COMMAND_WORD + "  " + indexPerson.getOneBased() + " "
                + PREFIX_TO + " " + indexEvent.getOneBased();
        updatedEvent = new EventBuilder(EVENT_4).withAttendee(VALID_EMAIL_BENSON, "lydia@example.com").build();
        assertCommandSuccess(command, indexPerson, indexEvent, updatedEvent);

        /* Case: filtered person list, event index within bounds of event list
         * but person index out of bounds of person list
         * -> rejected
         */
        showPersonsWithName(KEYWORD_MATCHING_MEIER);
        showAllEvents();
        int invalidIndex = getModel().getAddressBook().getPersonList().size();
        assertCommandFailure(InviteCommand.COMMAND_WORD + " " + invalidIndex + " "
                        + PREFIX_TO + indexEvent.getOneBased(),
                Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        /* Case: filtered event list, person index within bounds of person list
         * but event index out of bounds of event list
         * -> rejected
         */
        showAllPersons();
        showEventsWithName("1");
        invalidIndex = getModel().getEventList().getEventList().size();
        assertCommandFailure(InviteCommand.COMMAND_WORD + " " + indexPerson.getOneBased() + " "
                        + PREFIX_TO + invalidIndex,
                Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);

        /* ------------------------------ Performing invalid invite operation ----------------------------------- */

        showAllPersons();
        showAllEvents();
        /* Case: invalid index (0) -> rejected */
        assertCommandFailure(InviteCommand.COMMAND_WORD + " 0 "
                        + PREFIX_TO + indexEvent.getOneBased(),
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, InviteCommand.MESSAGE_USAGE));
        assertCommandFailure(InviteCommand.COMMAND_WORD + " " + indexPerson.getOneBased() + " "
                        + PREFIX_TO + "0",
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, InviteCommand.MESSAGE_USAGE));

        /* Case: invalid index (-1) -> rejected */
        assertCommandFailure(InviteCommand.COMMAND_WORD + " -1 "
                        + PREFIX_TO + indexEvent.getOneBased(),
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, InviteCommand.MESSAGE_USAGE));
        assertCommandFailure(InviteCommand.COMMAND_WORD + " " + indexPerson.getOneBased() + " "
                        + PREFIX_TO + "-1",
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, InviteCommand.MESSAGE_USAGE));

        /* Case: missing index -> rejected */
        assertCommandFailure(InviteCommand.COMMAND_WORD + " " + PREFIX_TO + indexEvent.getOneBased(),
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, InviteCommand.MESSAGE_USAGE));
        assertCommandFailure(InviteCommand.COMMAND_WORD + " " + indexPerson.getOneBased() + " " + PREFIX_TO,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, InviteCommand.MESSAGE_USAGE));

        /* Case: already invited -> rejected */
        indexPerson = INDEX_FIRST_PERSON;
        indexEvent = INDEX_THIRD_EVENT;
        assertCommandFailure(InviteCommand.COMMAND_WORD + " " + indexPerson.getOneBased()
                        + " " + PREFIX_TO + indexEvent.getOneBased(),
                String.format(InviteCommand.MESSAGE_DUPLICATE_PERSON));

        /* Case: event clashes with schedule -> rejected */
        Model expectedModel = getModel();
        indexPerson = INDEX_FIRST_PERSON;
        indexEvent = INDEX_FIRST_EVENT;

        Person targetPerson = getPerson(expectedModel, indexPerson);
        Event targetEvent = getEvent(expectedModel, indexEvent);

        assertCommandFailure(InviteCommand.COMMAND_WORD + " " + indexPerson.getOneBased()
                        + " " + PREFIX_TO + indexEvent.getOneBased(),
                String.format(InviteCommand.MESSAGE_CLASH_EVENT, targetEvent.getEventName(), targetPerson.getName()));
    }


    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String)} and in addition,<br>
     * 1. Asserts that result display box displays the success message of executing {@code InviteCommand}.<br>
     *
     * @param indexPerson the index of the current model's filtered person list
     * @param indexEvent the index of the current model's filtered event list.
     * @see InviteCommandSystemTest#assertCommandSuccess(String, Model, String)
     */
    private void assertCommandSuccess(String command, Index indexPerson, Index indexEvent, Event updatedEvent) {
        Model expectedModel = getModel();

        Person person = expectedModel.getFilteredPersonList().get(indexPerson.getZeroBased());
        Event event = expectedModel.getFilteredEventList().get(indexEvent.getZeroBased());

        String personName = person.getName().toString();
        String eventName = event.getEventName().toString();

        expectedModel.updateEvent(event, updatedEvent);

        assertCommandSuccess(command, expectedModel,
                String.format(InviteCommand.MESSAGE_INVITE_PERSON_SUCCESS, personName, eventName));
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
