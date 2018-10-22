package seedu.address.model.person;

import static org.junit.Assert.assertEquals;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

import org.junit.Test;

import seedu.address.testutil.TypicalTimeSlots;

public class DeconflictTimeTableTest {

    public final DeconflictTimeTable timeTableTypical = new DeconflictTimeTable(TypicalTimeSlots.getTypicalTimeTable());

    @Test
    public void equals() {
        DeconflictTimeTable toTest = new DeconflictTimeTable();

        toTest.addTimeSlot(TypicalTimeSlots.MON_8_TO_10);
        toTest.addTimeSlot(TypicalTimeSlots.TUE_10_TO_12);
        toTest.addTimeSlot(TypicalTimeSlots.MON_10_TO_12);

        assertEquals(timeTableTypical, toTest);
    }

    @Test
    public void findOverlapTimeSlots_hasOverlap_returnsOverlapTimeSlots() {
        Collection<TimeSlot> expected = new HashSet<>();

        expected.add(TypicalTimeSlots.MON_8_TO_10);
        expected.add(TypicalTimeSlots.MON_10_TO_12);

        assertEquals(timeTableTypical.findOverlapOrAdjacent(TypicalTimeSlots.MON_9_TO_11), expected);
    }

    @Test
    public void findOverlapTimeSlots_noOverlap_returnsEmpty() {
        assertEquals(timeTableTypical.findOverlapOrAdjacent(TypicalTimeSlots.WED_10_TO_12), Collections.emptySet());
    }

    @Test
    public void mergeOverlap() {
        DeconflictTimeTable expected = new DeconflictTimeTable();

        expected.addTimeSlot(TypicalTimeSlots.MON_8_TO_12);
        expected.addTimeSlot(TypicalTimeSlots.TUE_10_TO_12);

        timeTableTypical.mergeOverlap(TypicalTimeSlots.MON_9_TO_11);

        assertEquals(expected, timeTableTypical);
    }

}
