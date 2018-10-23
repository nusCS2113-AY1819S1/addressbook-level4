package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.AddTimeCommand.MESSAGE_ADD_TIMESLOT_SUCCESS;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
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

public class AddTimeCommandTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullIndex_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddTimeCommand(null, TypicalTimeSlots.MON_8_TO_10);
    }

    @Test
    public void constructor_nullTimeSlot_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddTimeCommand(TypicalIndexes.INDEX_FIRST_PERSON, null);
    }

    @Test
    public void execute_timeSlotAccepted_addSuccessful() {
        TimeSlot toAdd = TypicalTimeSlots.WED_10_TO_12;
        Collection<TimeSlot> expectedTimeSlots = new HashSet<>();

        expectedTimeSlots.addAll(TypicalTimeSlots.getTypicalTimeSlots());
        expectedTimeSlots.add(toAdd);

        TimeTable expectedTimeTable = new TimeTable(expectedTimeSlots);

        String expectedMessage = String.format(MESSAGE_ADD_TIMESLOT_SUCCESS, toAdd);

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

        AddTimeCommand command = new AddTimeCommand(Index.fromOneBased(1), toAdd);
        expectedModel.updatePerson(lastPerson, editedPerson);
        expectedModel.commitAddressBook();
        expectedModel.updateTimeTable(expectedTimeTable);

        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_timeSlotOverlaps_throwsCommandException() throws Exception {
        TimeSlot toAdd = TypicalTimeSlots.MON_8_TO_10;
        AddTimeCommand command = new AddTimeCommand(Index.fromOneBased(1), toAdd);

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddTimeCommand.MESSAGE_OVERLAP_TIMESLOT);
        command.execute(model, commandHistory);
    }

    @Test
    public void equals() {
        AddTimeCommand addMon8To10ToFirstCommand =
                new AddTimeCommand(TypicalIndexes.INDEX_FIRST_PERSON, TypicalTimeSlots.MON_8_TO_10);
        AddTimeCommand addTue8To10ToFirstCommand =
                new AddTimeCommand(TypicalIndexes.INDEX_FIRST_PERSON, TypicalTimeSlots.TUE_10_TO_12);
        AddTimeCommand addTue8To10ToSecondCommand =
                new AddTimeCommand(TypicalIndexes.INDEX_SECOND_PERSON, TypicalTimeSlots.TUE_10_TO_12);

        // same object -> returns true
        assertTrue(addMon8To10ToFirstCommand.equals(addMon8To10ToFirstCommand));

        // same values -> returns true
        AddTimeCommand addMon8To10CommandCopy =
                new AddTimeCommand(TypicalIndexes.INDEX_FIRST_PERSON, TypicalTimeSlots.MON_8_TO_10);
        assertTrue(addMon8To10ToFirstCommand.equals(addMon8To10CommandCopy));

        // different types -> returns false
        assertFalse(addMon8To10ToFirstCommand.equals(1));

        // null -> returns false
        assertFalse(addMon8To10ToFirstCommand.equals(null));

        // different timeslot -> returns false
        assertFalse(addMon8To10ToFirstCommand.equals(addTue8To10ToFirstCommand));

        // different index -> returns false
        assertFalse(addTue8To10ToFirstCommand.equals(addTue8To10ToSecondCommand));
    }
}
