package seedu.address.testutil;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import seedu.address.model.person.TimeSlot;
import seedu.address.model.person.TimeTable;

/**
 * A utility class containing a list of {@code TimeSlot} objects to be used in tests.
 */
public class TypicalTimeSlots {
    // In typical timetable
    public static final TimeSlot MON_8_TO_10 = new TimeSlot(DayOfWeek.MONDAY,
            LocalTime.parse("08:00"),
            LocalTime.parse("10:00"));

    public static final TimeSlot MON_10_TO_12 = new TimeSlot(DayOfWeek.MONDAY,
            LocalTime.parse("10:00"),
            LocalTime.parse("12:00"));

    public static final TimeSlot TUES_10_TO_12 = new TimeSlot(DayOfWeek.TUESDAY,
            LocalTime.parse("10:00"),
            LocalTime.parse("12:00"));

    // Not in typical timetable, will overlap
    public static final TimeSlot MON_9_TO_11 = new TimeSlot(DayOfWeek.MONDAY,
            LocalTime.parse("09:00"),
            LocalTime.parse("11:00"));

    // Not in typical timetable, will not overlap
    public static final TimeSlot WED_10_TO_12 = new TimeSlot(DayOfWeek.WEDNESDAY,
            LocalTime.parse("10:00"),
            LocalTime.parse("12:00"));

    public static TimeTable getTypicalTimeTable() {
        TimeTable t = new TimeTable();
        for (TimeSlot timeSlot : getTypicalTimeSlots()) {
            t.addTimeSlot(timeSlot);
        }
        return t;
    }

    public static Collection<TimeSlot> getTypicalTimeSlots() {
        return new ArrayList<>(Arrays.asList(MON_8_TO_10, MON_10_TO_12, TUES_10_TO_12));
    }
}
