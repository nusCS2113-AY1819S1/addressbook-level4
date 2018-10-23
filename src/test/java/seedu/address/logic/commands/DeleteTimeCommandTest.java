package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.DeleteTimeCommand.MESSAGE_DELETE_TIMESLOT_SUCCESS;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Collection;
import java.util.HashSet;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.TimeSlot;
import seedu.address.model.person.TimeTable;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.TypicalIndexes;
import seedu.address.testutil.TypicalTimeSlots;

public class DeleteTimeCommandTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullIndex_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new DeleteTimeCommand(null, TypicalTimeSlots.MON_8_TO_10);
    }

    @Test
    public void constructor_nullTimeSlot_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new DeleteTimeCommand(TypicalIndexes.INDEX_FIRST_PERSON, null);
    }

    @Test
    public void execute_timeSlotAccepted_deleteSuccessful() {
        TimeSlot toDelete = TypicalTimeSlots.MON_8_TO_10;
        Collection<TimeSlot> expectedTimeSlots = new HashSet<>();

        expectedTimeSlots.addAll(TypicalTimeSlots.getTypicalTimeSlots());
        expectedTimeSlots.remove(toDelete);

        TimeTable expectedTimeTable = new TimeTable(expectedTimeSlots);

        String expectedMessage = String.format(MESSAGE_DELETE_TIMESLOT_SUCCESS, toDelete);

        Person lastPerson = new PersonBuilder().withName("Alice Pauline")
                .withAddress("123, Jurong West Ave 6, #08-111").withEmail("alice@example.com")
                .withPhone("94351253")
                .withTags("friends")
                .withTimeTable(new TimeTable(TypicalTimeSlots.getTypicalTimeTable())).build();

        Person editedPerson = new PersonBuilder().withName("Alice Pauline")
                .withAddress("123, Jurong West Ave 6, #08-111").withEmail("alice@example.com")
                .withPhone("94351253")
                .withTags("friends")
                .withTimeTable(expectedTimeTable).build();

        DeleteTimeCommand command = new DeleteTimeCommand(Index.fromOneBased(1), toDelete);
        expectedModel.updatePerson(lastPerson, editedPerson);
        expectedModel.commitAddressBook();
        expectedModel.updateTimeTable(expectedTimeTable);

        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_timeSlotDoesNotExist_throwsCommandException() throws Exception {
        TimeSlot toDelete = TypicalTimeSlots.WED_10_TO_12;
        DeleteTimeCommand command = new DeleteTimeCommand(Index.fromOneBased(1), toDelete);

        thrown.expect(CommandException.class);
        thrown.expectMessage(DeleteTimeCommand.MESSAGE_TIMESLOT_DOES_NOT_EXIST);
        command.execute(model, commandHistory);
    }

    @Test
    public void equals() {
        DeleteTimeCommand deleteMon8To10ToFirstCommand =
                new DeleteTimeCommand(TypicalIndexes.INDEX_FIRST_PERSON, TypicalTimeSlots.MON_8_TO_10);
        DeleteTimeCommand deleteTue8To10ToFirstCommand =
                new DeleteTimeCommand(TypicalIndexes.INDEX_FIRST_PERSON, TypicalTimeSlots.TUE_10_TO_12);
        DeleteTimeCommand deleteTue8To10ToSecondCommand =
                new DeleteTimeCommand(TypicalIndexes.INDEX_SECOND_PERSON, TypicalTimeSlots.TUE_10_TO_12);

        // same object -> returns true
        assertTrue(deleteMon8To10ToFirstCommand.equals(deleteMon8To10ToFirstCommand));

        // same values -> returns true
        DeleteTimeCommand deleteMon8To10CommandCopy =
                new DeleteTimeCommand(TypicalIndexes.INDEX_FIRST_PERSON, TypicalTimeSlots.MON_8_TO_10);
        assertTrue(deleteMon8To10ToFirstCommand.equals(deleteMon8To10CommandCopy));

        // different types -> returns false
        assertFalse(deleteMon8To10ToFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteMon8To10ToFirstCommand.equals(null));

        // different timeslot -> returns false
        assertFalse(deleteMon8To10ToFirstCommand.equals(deleteTue8To10ToFirstCommand));

        // different index -> returns false
        assertFalse(deleteTue8To10ToFirstCommand.equals(deleteTue8To10ToSecondCommand));
    }
}
