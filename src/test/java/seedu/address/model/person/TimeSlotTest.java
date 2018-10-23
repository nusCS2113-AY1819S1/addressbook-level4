package seedu.address.model.person;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.DayOfWeek;
import java.time.LocalTime;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.person.exceptions.TimeSlotNotOverlapException;
import seedu.address.testutil.Assert;
import seedu.address.testutil.TypicalTimeSlots;

public class TimeSlotTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, ()
            -> new TimeSlot(null, LocalTime.MIDNIGHT, LocalTime.NOON));

        Assert.assertThrows(NullPointerException.class, ()
            -> new TimeSlot(DayOfWeek.MONDAY, null, LocalTime.NOON));

        Assert.assertThrows(NullPointerException.class, ()
            -> new TimeSlot(DayOfWeek.MONDAY, LocalTime.MIDNIGHT, null));
    }

    @Test
    public void constructor_invalidTimeSlot_throwsIllegalArgumentException() {
        Assert.assertThrows(IllegalArgumentException.class, ()
            -> new TimeSlot(DayOfWeek.MONDAY, LocalTime.NOON, LocalTime.NOON));
    }

    @Test
    public void equals() {
        TimeSlot toTest = new TimeSlot(DayOfWeek.MONDAY, LocalTime.parse("08:00"), LocalTime.parse("10:00"));
        assertEquals(toTest, TypicalTimeSlots.MON_8_TO_10);
    }

    @Test
    public void isValidTimeSlot() {
        // invalid TimeSlots
        assertFalse(TimeSlot.isValidTimeSlot(LocalTime.NOON, LocalTime.NOON));
        assertFalse(TimeSlot.isValidTimeSlot(LocalTime.NOON, LocalTime.MIDNIGHT));

        // valid TimeSlots
        assertTrue(TimeSlot.isValidTimeSlot(LocalTime.MIDNIGHT, LocalTime.NOON));
        assertTrue(TimeSlot.isValidTimeSlot(LocalTime.MIDNIGHT, LocalTime.MAX));
    }

    @Test
    public void isOverlap() {
        TimeSlot timeSlot = new TimeSlot(DayOfWeek.MONDAY, LocalTime.parse("01:00"), LocalTime.NOON);

        // Overlapping, subset with same start time
        assertTrue(timeSlot.isOverlap(new TimeSlot(DayOfWeek.MONDAY, LocalTime.parse("01:00"), LocalTime.MAX)));

        // Overlapping, subset with same end time
        assertTrue(timeSlot.isOverlap(new TimeSlot(DayOfWeek.MONDAY, LocalTime.parse("06:00"), LocalTime.NOON)));

        // Overlapping, not subset, is after
        assertTrue(timeSlot.isOverlap(new TimeSlot(DayOfWeek.MONDAY, LocalTime.parse("06:00"), LocalTime.MAX)));

        // Overlapping, not subset, is before
        assertTrue(timeSlot.isOverlap(new TimeSlot(DayOfWeek.MONDAY, LocalTime.MIDNIGHT, LocalTime.parse("02:00"))));

        // Not overlapping, different day
        assertFalse(timeSlot.isOverlap(new TimeSlot(DayOfWeek.TUESDAY, LocalTime.MIDNIGHT, LocalTime.MAX)));

        // Not overlapping, end time of first = start time of second
        assertFalse(timeSlot.isOverlap(new TimeSlot(DayOfWeek.MONDAY, LocalTime.NOON, LocalTime.MAX)));

        // Not overlapping, general case
        assertFalse(timeSlot.isOverlap(new TimeSlot(DayOfWeek.MONDAY, LocalTime.parse("13:00"), LocalTime.MAX)));
    }

    @Test
    public void merge_invalidInput_throwsException() {
        thrown.expect(TimeSlotNotOverlapException.class);
        TypicalTimeSlots.MON_8_TO_10.merge(TypicalTimeSlots.TUE_10_TO_12);
    }

    @Test
    public void merge_validInput_returnsMerged() {
        assertEquals(TypicalTimeSlots.MON_8_TO_10.merge(TypicalTimeSlots.MON_10_TO_12), TypicalTimeSlots.MON_8_TO_12);
        assertEquals(TypicalTimeSlots.MON_8_TO_10.merge(TypicalTimeSlots.MON_9_TO_11), TypicalTimeSlots.MON_8_TO_11);
    }
}
