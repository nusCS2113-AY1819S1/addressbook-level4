package seedu.address.model.person;

import static org.junit.Assert.assertEquals;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.person.exceptions.TimeSlotDoesNotExistException;
import seedu.address.model.person.exceptions.TimeSlotOverlapException;

public class TimeTableTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final TimeSlot mon8To10 = new TimeSlot(DayOfWeek.MONDAY,
                                                   LocalTime.parse("08:00"),
                                                   LocalTime.parse("10:00"));

    private final TimeSlot mon9To11 = new TimeSlot(DayOfWeek.MONDAY,
                                                   LocalTime.parse("09:00"),
                                                   LocalTime.parse("11:00"));

    private final TimeSlot mon10To12 = new TimeSlot(DayOfWeek.MONDAY,
                                                    LocalTime.parse("10:00"),
                                                    LocalTime.parse("12:00"));

    private final TimeSlot tues10To12 = new TimeSlot(DayOfWeek.TUESDAY,
                                                    LocalTime.parse("10:00"),
                                                    LocalTime.parse("12:00"));

    private final TimeTable timeTableBlank = new TimeTable();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), timeTableBlank.getTimeSlots());
    }

    @Test
    public void findOverlapTimeSlots_hasOverlap_returnsOverlapTimeSlots() {
        Collection <TimeSlot> expected = new ArrayList<>();

        expected.add(mon8To10);
        expected.add(mon10To12);

        TimeTable toTest = new TimeTable(expected);
        assertEquals(toTest.findOverlapTimeSlots(mon9To11), expected);
    }

    @Test
    public void findOverlapTimeSlots_noOverlap_returnsEmpty() {
        Collection <TimeSlot> expected = new ArrayList<>();

        expected.add(mon8To10);
        expected.add(tues10To12);

        TimeTable toTest = new TimeTable(expected);
        assertEquals(toTest.findOverlapTimeSlots(mon10To12), Collections.emptyList());
    }

    @Test
    public void addTimeSlot_timeSlotNotInTimeTable_addsTimeSlot() {
        Collection <TimeSlot> expected = new ArrayList<>();

        expected.add(mon8To10);
        timeTableBlank.addTimeSlot(mon8To10);

        assertEquals(timeTableBlank.getTimeSlots(), expected);
    }

    @Test
    public void addTimeSlot_duplicateTimeSlot_throwsTimeSlotOverlapException() {
        thrown.expect(TimeSlotOverlapException.class);
        timeTableBlank.addTimeSlot(mon8To10);
        timeTableBlank.addTimeSlot(mon9To11);
    }

    @Test
    public void removeTimeSlot_timeSlotInTimeTable_removesTimeSlot() {
        timeTableBlank.addTimeSlot(mon8To10);
        timeTableBlank.removeTimeSlot(mon8To10);

        assertEquals(timeTableBlank.getTimeSlots(), Collections.EMPTY_LIST);
    }

    @Test
    public void removeTimeSlot_timeSlotNotInTimeTable_throwsTimeSlotDoesNotExistException() {
        thrown.expect(TimeSlotDoesNotExistException.class);
        timeTableBlank.removeTimeSlot(mon8To10);
    }
}
