package seedu.address.model.schedule;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_CARL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMPLOYEEID_CARL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TYPE_CARL;
import static seedu.address.testutil.schedule.TypicalSchedules.ALICE_WORK;
import static seedu.address.testutil.schedule.TypicalSchedules.VALID_DATE_ALICE;
import static seedu.address.testutil.schedule.TypicalSchedules.VALID_EMPLOYEEID_ALICE;
import static seedu.address.testutil.schedule.TypicalSchedules.VALID_TYPE_ALICE;
import static seedu.address.testutil.schedule.TypicalSchedules.VALID_YEAR_ALICE;

import java.util.Objects;

import org.junit.Rule;
import org.junit.Test;

import org.junit.rules.ExpectedException;

import seedu.address.testutil.Assert;
import seedu.address.testutil.schedule.ScheduleBuilder;

public class ScheduleTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Schedule(null, null, null));
    }

    @Test
    public void isSameSchedule_sameSchedule_true() {
        // same object -> returns true
        assertTrue(ALICE_WORK.isSameSchedule(ALICE_WORK));
    }

    @Test
    public void isSameSchedule_null_false() {
        // null object -> returns false
        assertFalse(ALICE_WORK.isSameSchedule(null));
    }

    @Test
    public void isSameSchedule_differentEmployeeId_false() {
        // different employee id -> returns false
        Schedule editedAlice = new ScheduleBuilder().withEmployeeId(VALID_EMPLOYEEID_CARL)
                .withType(VALID_TYPE_ALICE).withDate(VALID_DATE_ALICE).build();
        assertFalse(ALICE_WORK.isSameSchedule(editedAlice));
    }

    @Test
    public void isSameSchedule_differentType_false() {
        // different type -> returns false
        Schedule editedAlice = new ScheduleBuilder().withEmployeeId(VALID_EMPLOYEEID_ALICE)
                .withDate(VALID_DATE_ALICE).withType(VALID_TYPE_CARL).build();
        assertFalse(ALICE_WORK.isSameSchedule(editedAlice));
    }

    @Test
    public void isSameSchedule_differentDate_false() {
        // different date -> returns false
        Schedule editedAlice = new ScheduleBuilder().withEmployeeId(VALID_EMPLOYEEID_ALICE)
                .withType(VALID_TYPE_CARL).withDate(VALID_DATE_CARL).build();
        assertFalse(ALICE_WORK.isSameSchedule(editedAlice));
    }

    @Test
    public void isSameSchedule_sameAttributes_true() {
        // same employee id, type, date -> returns true
        Schedule editedAlice = new ScheduleBuilder().withDate(VALID_DATE_ALICE)
                .withEmployeeId(VALID_EMPLOYEEID_ALICE).withType(VALID_TYPE_ALICE).build();
        assertTrue(ALICE_WORK.isSameSchedule(editedAlice));
    }

    @Test
    public void getScheduleYear_validSchedule_correctYearRepresentation() {
        Schedule editedAlice = new ScheduleBuilder(ALICE_WORK).withDate(VALID_DATE_ALICE)
                .withEmployeeId(VALID_EMPLOYEEID_ALICE).withType(VALID_TYPE_ALICE).build();
        assertEquals(editedAlice.getScheduleYear(), VALID_YEAR_ALICE);
    }

    @Test
    public void hashCode_validSchedule_correctHashCodeRepresentation() {
        Schedule editedAlice = new ScheduleBuilder(ALICE_WORK).withDate(VALID_DATE_ALICE)
                .withEmployeeId(VALID_EMPLOYEEID_ALICE).withType(VALID_TYPE_ALICE).build();
        assertEquals(editedAlice.hashCode(), Objects.hash(VALID_TYPE_ALICE, VALID_DATE_ALICE));
    }


}
