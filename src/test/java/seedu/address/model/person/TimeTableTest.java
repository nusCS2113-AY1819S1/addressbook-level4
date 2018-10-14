package seedu.address.model.person;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.person.exceptions.TimeSlotDoesNotExistException;
import seedu.address.model.person.exceptions.TimeSlotOverlapException;
import seedu.address.testutil.TypicalTimeSlots;

public class TimeTableTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final TimeTable timeTableBlank = new TimeTable();
    private final TimeTable timeTableFromParams = new TimeTable(TypicalTimeSlots.getTypicalTimeSlots());
    private final TimeTable timeTableTypical = TypicalTimeSlots.getTypicalTimeTable();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), timeTableBlank.getTimeSlots());
    }

    @Test
    public void constructorWithParams() {
        assertEquals(TypicalTimeSlots.getTypicalTimeSlots(), timeTableFromParams.getTimeSlots());
    }

    @Test
    public void findOverlapTimeSlots_hasOverlap_returnsOverlapTimeSlots() {
        Collection <TimeSlot> expected = new ArrayList<>();

        expected.add(TypicalTimeSlots.MON_8_TO_10);
        expected.add(TypicalTimeSlots.MON_10_TO_12);

        assertEquals(timeTableTypical.findOverlapTimeSlots(TypicalTimeSlots.MON_9_TO_11), expected);
    }

    @Test
    public void findOverlapTimeSlots_noOverlap_returnsEmpty() {
        assertEquals(timeTableTypical.findOverlapTimeSlots(TypicalTimeSlots.WED_10_TO_12), Collections.emptyList());
    }

    @Test
    public void addTimeSlot_timeSlotNotInTimeTable_addsTimeSlot() {
        Collection <TimeSlot> expected = new ArrayList<>();

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
        timeTableTypical.removeTimeSlot(TypicalTimeSlots.TUES_10_TO_12);

        assertEquals(timeTableBlank.getTimeSlots(), Collections.EMPTY_LIST);
    }

    @Test
    public void removeTimeSlot_timeSlotNotInTimeTable_throwsTimeSlotDoesNotExistException() {
        thrown.expect(TimeSlotDoesNotExistException.class);
        timeTableBlank.removeTimeSlot(TypicalTimeSlots.MON_8_TO_10);
    }
}
