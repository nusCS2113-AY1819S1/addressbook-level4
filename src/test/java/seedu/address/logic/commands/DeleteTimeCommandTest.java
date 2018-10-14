package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.testutil.TypicalIndexes;
import seedu.address.testutil.TypicalTimeSlots;

public class DeleteTimeCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

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
    public void equals() {
        DeleteTimeCommand deleteMon8To10ToFirstCommand = new DeleteTimeCommand(TypicalIndexes.INDEX_FIRST_PERSON, TypicalTimeSlots.MON_8_TO_10);
        DeleteTimeCommand deleteTue8To10ToFirstCommand = new DeleteTimeCommand(TypicalIndexes.INDEX_FIRST_PERSON, TypicalTimeSlots.TUE_10_TO_12);
        DeleteTimeCommand deleteTue8To10ToSecondCommand = new DeleteTimeCommand(TypicalIndexes.INDEX_SECOND_PERSON, TypicalTimeSlots.TUE_10_TO_12);

        // same object -> returns true
        assertTrue(deleteMon8To10ToFirstCommand.equals(deleteMon8To10ToFirstCommand));

        // same values -> returns true
        DeleteTimeCommand deleteMon8To10CommandCopy = new DeleteTimeCommand(TypicalIndexes.INDEX_FIRST_PERSON, TypicalTimeSlots.MON_8_TO_10);
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

    // TODO: Add execution tests
}
