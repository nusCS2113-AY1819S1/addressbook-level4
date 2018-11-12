package seedu.address.model.schedule;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static seedu.address.testutil.TypicalSchedules.ACCOUTING;
import static seedu.address.testutil.TypicalSchedules.CLEANING;
import static seedu.address.testutil.TypicalSchedules.OFFICE;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.testutil.Assert;
import seedu.address.testutil.ScheduleBuilder;

public class ScheduleTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructor_nullValues_throwsNullException() {
        Assert.assertThrows(NullPointerException.class, () -> new Schedule(null, null, null));
    }

    @Test
    public void constructor_nullValue_throwsNullException() {
        // null TimeStart
        Assert.assertThrows(NullPointerException.class, () -> new Schedule(new TimeStart("0900"), null,
                null));

        // null TimeEnd
        Assert.assertThrows(NullPointerException.class, () -> new Schedule(null, new TimeEnd("1500"),
                null));

        // null Venue
        Assert.assertThrows(NullPointerException.class, () -> new Schedule(null, null,
                new Venue("Counter")));
    }

    @Test
    public void constructor_sameTime_throwsCommandException() {
        Assert.assertThrows(CommandException.class, () -> new Schedule(new TimeStart("0900"), new TimeEnd("0900"),
                new Venue("Counter")));
    }

    @Test
    public void equals() {
        // same values -> return true
        Schedule cleaningCopy = new ScheduleBuilder(CLEANING).build();
        assertEquals(CLEANING, cleaningCopy);

        // same object -> returns true
        assertEquals(CLEANING, CLEANING);

        // null -> returns false
        assertNotEquals(CLEANING, null);

        // different type -> returns false
        assertNotEquals(CLEANING, 5);

        // different schedule -> returns false
        assertNotEquals(CLEANING, OFFICE);

        // different time start -> returns false
        Schedule editedSchedule = new ScheduleBuilder(CLEANING).withTimeStart("2300").build();
        assertNotEquals(CLEANING, editedSchedule);

        // different time end -> returns false
        editedSchedule = new ScheduleBuilder(CLEANING).withTimeEnd("2300").build();
        assertNotEquals(CLEANING, editedSchedule);

        // different venue -> returns false
        editedSchedule = new ScheduleBuilder(CLEANING).withVenue("Pantry").build();
        assertNotEquals(CLEANING, editedSchedule);
    }

    @Test
    public void hashCode_sameObject_equals() {
        Schedule schedule = CLEANING;
        int expectedHash = schedule.hashCode();
        assertEquals(schedule.hashCode(), expectedHash);
    }

    @Test
    public void hashCode_differentObject_equals() {
        Schedule schedule = CLEANING;
        int expectedHash = schedule.hashCode();
        Schedule sameSchedule = CLEANING;
        assertEquals(sameSchedule.hashCode(), expectedHash);
    }

    @Test
    public void hashCode_differentValues_notEquals() {
        Schedule schedule = CLEANING;
        int expectedHash = schedule.hashCode();
        Schedule differentSchedule = ACCOUTING;
        assertNotEquals(differentSchedule.hashCode(), expectedHash);
    }
}
