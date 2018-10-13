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

    private final TimeTable timeTable = new TimeTable();

    private final TimeSlot mon8To10 = new TimeSlot(DayOfWeek.MONDAY,
                                                   LocalTime.parse("08:00"),
                                                   LocalTime.parse("10:00"));

    private final TimeSlot mon9To11 = new TimeSlot(DayOfWeek.MONDAY,
                                                   LocalTime.parse("09:00"),
                                                   LocalTime.parse("11:00"));

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), timeTable.getTimeSlots());
    }

    @Test
    public void addTimeSlot_timeSlotNotInTimeTable_addsTimeSlot() {
        Collection <TimeSlot> toCheck = new ArrayList<>();
        TimeSlot toAdd = mon8To10;

        toCheck.add(toAdd);
        timeTable.addTimeSlot(toAdd);

        assertEquals(timeTable.getTimeSlots(), toCheck);
    }

    @Test
    public void addTimeSlot_duplicateTimeSlot_throwsTimeSlotOverlapException() {
        thrown.expect(TimeSlotOverlapException.class);
        timeTable.addTimeSlot(mon8To10);
        timeTable.addTimeSlot(mon9To11);
    }

    @Test
    public void removeTimeSlot_timeSlotNotInTimeTable_throwsTimeSlotDoesNotExistException() {
        thrown.expect(TimeSlotDoesNotExistException.class);
        timeTable.removeTimeSlot(mon8To10);
    }

    @Test
    public void removeTimeSlot_timeSlotInTimeTable_removesTimeSlot() {
        timeTable.addTimeSlot(mon8To10);
        timeTable.removeTimeSlot(mon8To10);

        assertEquals(timeTable.getTimeSlots(), Collections.EMPTY_LIST);
    }
}
