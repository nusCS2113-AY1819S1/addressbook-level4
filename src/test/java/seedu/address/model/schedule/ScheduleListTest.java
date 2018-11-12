package seedu.address.model.schedule;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.schedule.TypicalSchedules.ALICE_WORK;
import static seedu.address.testutil.schedule.TypicalSchedules.BENSON_WORK;
import static seedu.address.testutil.schedule.TypicalSchedules.getTypicalScheduleList;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.schedule.exceptions.DuplicateScheduleException;
import seedu.address.testutil.schedule.ScheduleBuilder;


public class ScheduleListTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final ScheduleList scheduleList = new ScheduleList();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), scheduleList.getScheduleList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        scheduleList.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyScheduleList_replacesData() {
        ScheduleList newData = getTypicalScheduleList();
        scheduleList.resetData(newData);
        assertEquals(newData, scheduleList);
    }

    @Test
    public void resetData_withDuplicateSchedules_throwsDuplicateScheduleException() {
        // Two schedules with the same identity fields
        Schedule editedAlice = new ScheduleBuilder(ALICE_WORK).build();
        List<Schedule> newSchedules = Arrays.asList(ALICE_WORK, editedAlice);
        ScheduleListStub newData = new ScheduleListStub(newSchedules);

        thrown.expect(DuplicateScheduleException.class);
        scheduleList.resetData(newData);
    }

    @Test
    public void hasSchedule_nullSchedule_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        scheduleList.hasSchedule(null);
    }

    @Test
    public void hasSchedule_scheduleNotInScheduleList_returnsFalse() {
        assertFalse(scheduleList.hasSchedule(ALICE_WORK));
    }

    @Test
    public void hasSchedule_scheduleInScheduleList_returnsTrue() {
        scheduleList.addSchedule(ALICE_WORK);
        assertTrue(scheduleList.hasSchedule(ALICE_WORK));
    }

    @Test
    public void hasSchedule_scheduleWithSameIdentityFieldsInScheduleList_returnsTrue() {
        scheduleList.addSchedule(ALICE_WORK);
        Schedule editedAlice = new ScheduleBuilder(ALICE_WORK).build();
        assertTrue(scheduleList.hasSchedule(editedAlice));
    }

    @Test
    public void getScheduleList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        scheduleList.getScheduleList().remove(0);
    }

    @Test
    public void toString_validSchedule_returnsOneScheduleSize() {
        scheduleList.addSchedule(ALICE_WORK);
        assertEquals(scheduleList.toString(), "1 schedules");
    }

    @Test
    public void hashCode_validSchedule_correctHashCodeRepresentation() {
        scheduleList.addSchedule(ALICE_WORK);
        Schedule sameAlice = new ScheduleBuilder(ALICE_WORK).build();
        List<Schedule> expected = Arrays.asList(sameAlice);
        assertEquals(scheduleList.hashCode(), expected.hashCode());
    }

    @Test
    public void updateSchedule_validSchedule_scheduleUpdated() {
        scheduleList.addSchedule(ALICE_WORK);
        scheduleList.updateSchedule(ALICE_WORK, BENSON_WORK);
        assertTrue(scheduleList.hasSchedule(BENSON_WORK));
    }

    /**
     * A stub ReadOnlySchedule whose schedule list can violate interface constraints.
     */
    private static class ScheduleListStub implements ReadOnlyScheduleList {
        private final ObservableList<Schedule> schedules = FXCollections.observableArrayList();

        ScheduleListStub(Collection<Schedule> schedules) {
            this.schedules.setAll(schedules);
        }

        @Override
        public ObservableList<Schedule> getScheduleList() {
            return schedules;
        }
    }
}
