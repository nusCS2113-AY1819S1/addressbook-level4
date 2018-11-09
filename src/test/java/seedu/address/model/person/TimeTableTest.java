package seedu.address.model.person;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalTime;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.person.exceptions.TimeSlotDoesNotExistException;
import seedu.address.model.person.exceptions.TimeSlotOverlapException;
import seedu.address.model.person.exceptions.TimeTableEmptyException;
import seedu.address.testutil.TypicalTimeSlots;

public class TimeTableTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final TimeTable timeTableBlank = new TimeTable();
    private final TimeTable timeTableFromParams = new TimeTable(TypicalTimeSlots.getTypicalTimeSlots());
    private final TimeTable timeTableTypical = TypicalTimeSlots.getTypicalTimeTable();

    @Test
    public void constructor() {
        assertEquals(Collections.emptySet(), timeTableBlank.getTimeSlots());
    }

    @Test
    public void constructorWithParams() {
        assertEquals(TypicalTimeSlots.getTypicalTimeSlots(), timeTableFromParams.getTimeSlots());
    }

    @Test
    public void hasOverlap_returnsTrue() {
        assertTrue(timeTableTypical.hasOverlap(TypicalTimeSlots.MON_9_TO_11));
    }

    @Test
    public void noOverlap_returnsFalse() {
        assertFalse(timeTableTypical.hasOverlap(TypicalTimeSlots.WED_10_TO_12));
        assertFalse(timeTableTypical.hasOverlap(TypicalTimeSlots.TUE_12_TO_14));
    }

    @Test
    public void addTimeSlot_timeSlotNotInTimeTable_addsTimeSlot() {
        Collection <TimeSlot> expected = new HashSet<>();

        expected.add(TypicalTimeSlots.MON_8_TO_10);
        timeTableBlank.addTimeSlot(TypicalTimeSlots.MON_8_TO_10);

        assertEquals(timeTableBlank.getTimeSlots(), expected);
    }

    @Test
    public void addTimeSlot_duplicateTimeSlot_throwsTimeSlotOverlapException() {
        thrown.expect(TimeSlotOverlapException.class);
        timeTableTypical.addTimeSlot(TypicalTimeSlots.MON_8_TO_10);
    }

    @Test
    public void removeTimeSlot_timeSlotInTimeTable_removesTimeSlot() {
        timeTableTypical.removeTimeSlot(TypicalTimeSlots.MON_8_TO_10);
        timeTableTypical.removeTimeSlot(TypicalTimeSlots.MON_10_TO_12);
        timeTableTypical.removeTimeSlot(TypicalTimeSlots.TUE_10_TO_12);

        assertEquals(timeTableBlank.getTimeSlots(), Collections.EMPTY_SET);
    }

    @Test
    public void removeTimeSlot_timeSlotNotInTimeTable_throwsTimeSlotDoesNotExistException() {
        thrown.expect(TimeSlotDoesNotExistException.class);
        timeTableBlank.removeTimeSlot(TypicalTimeSlots.MON_8_TO_10);
    }

    @Test
    public void getEarliest_emptyTimeTable_throwsTimeTableEmptyException() {
        thrown.expect(TimeTableEmptyException.class);
        timeTableBlank.getEarliest();
    }

    @Test
    public void getLatest_emptyTimeTable_throwsTimeTableEmptyException() {
        thrown.expect(TimeTableEmptyException.class);
        timeTableBlank.getLatest();
    }

    @Test
    public void getEarliestLatest_nonEmptyTimeTable_returnsEarliestLatest() {
        timeTableBlank.addTimeSlot(TypicalTimeSlots.MON_10_TO_12);
        assertEquals(timeTableBlank.getEarliest(), LocalTime.parse("10:00"));
        assertEquals(timeTableBlank.getLatest(), LocalTime.parse("12:00"));

        timeTableBlank.addTimeSlot(TypicalTimeSlots.TUE_10_TO_12);
        assertEquals(timeTableBlank.getEarliest(), LocalTime.parse("10:00"));
        assertEquals(timeTableBlank.getLatest(), LocalTime.parse("12:00"));

        timeTableBlank.addTimeSlot(TypicalTimeSlots.MON_8_TO_10);
        assertEquals(timeTableBlank.getEarliest(), LocalTime.parse("08:00"));
        assertEquals(timeTableBlank.getLatest(), LocalTime.parse("12:00"));

        timeTableBlank.addTimeSlot(TypicalTimeSlots.TUE_12_TO_14);
        assertEquals(timeTableBlank.getEarliest(), LocalTime.parse("08:00"));
        assertEquals(timeTableBlank.getLatest(), LocalTime.parse("14:00"));

        timeTableBlank.addTimeSlot(TypicalTimeSlots.WED_10_TO_12);
        assertEquals(timeTableBlank.getEarliest(), LocalTime.parse("08:00"));
        assertEquals(timeTableBlank.getLatest(), LocalTime.parse("14:00"));

        timeTableBlank.removeTimeSlot(TypicalTimeSlots.TUE_10_TO_12);
        assertEquals(timeTableBlank.getEarliest(), LocalTime.parse("08:00"));
        assertEquals(timeTableBlank.getLatest(), LocalTime.parse("14:00"));

        timeTableBlank.removeTimeSlot(TypicalTimeSlots.TUE_12_TO_14);
        assertEquals(timeTableBlank.getEarliest(), LocalTime.parse("08:00"));
        assertEquals(timeTableBlank.getLatest(), LocalTime.parse("12:00"));

        timeTableBlank.removeTimeSlot(TypicalTimeSlots.WED_10_TO_12);
        assertEquals(timeTableBlank.getEarliest(), LocalTime.parse("08:00"));
        assertEquals(timeTableBlank.getLatest(), LocalTime.parse("12:00"));

        timeTableBlank.removeTimeSlot(TypicalTimeSlots.MON_10_TO_12);
        assertEquals(timeTableBlank.getEarliest(), LocalTime.parse("08:00"));
        assertEquals(timeTableBlank.getLatest(), LocalTime.parse("10:00"));

    }
}
