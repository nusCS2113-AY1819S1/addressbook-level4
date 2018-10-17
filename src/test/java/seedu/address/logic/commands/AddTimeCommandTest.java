package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.testutil.TypicalIndexes;
import seedu.address.testutil.TypicalTimeSlots;

public class AddTimeCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

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

    // TODO: Add execution tests
}
