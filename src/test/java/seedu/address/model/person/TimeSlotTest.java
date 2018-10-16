package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.DayOfWeek;
import java.time.LocalTime;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class TimeSlotTest {

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
}
