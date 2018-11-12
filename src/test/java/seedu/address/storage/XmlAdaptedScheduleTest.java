package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_CARL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMPLOYEEID_CARL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TYPE_CARL;
import static seedu.address.storage.schedule.XmlAdaptedSchedule.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.schedule.TypicalSchedules.BENSON_WORK;
import static seedu.address.testutil.schedule.TypicalSchedules.BENSON_WORK_COPY;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.EmployeeId;
import seedu.address.model.schedule.Date;
import seedu.address.model.schedule.Schedule;
import seedu.address.model.schedule.Type;
import seedu.address.storage.schedule.XmlAdaptedSchedule;
import seedu.address.testutil.Assert;
import seedu.address.testutil.schedule.ScheduleBuilder;

public class XmlAdaptedScheduleTest {

    private static final String INVALID_TYPE = "R@chel";
    private static final String INVALID_DATE = "40/13/9999";
    private static final String INVALID_EMPLOYEEID = " ";

    private static final String VALID_EMPLOYEEID = BENSON_WORK.getEmployeeId().toString();
    private static final String VALID_DATE = BENSON_WORK.getScheduleDate().toString();
    private static final String VALID_TYPE = BENSON_WORK.getType().toString();

    private XmlAdaptedSchedule bensonSchedule = new XmlAdaptedSchedule(BENSON_WORK);

    @Test
    public void toModelType_validScheduleDetails_returnsSchedule() throws Exception {
        XmlAdaptedSchedule schedule = new XmlAdaptedSchedule(BENSON_WORK);
        assertEquals(BENSON_WORK, schedule.toModelType());
    }

    //-------------------------------------------//
    @Test
    public void toModelType_invalidType_throwsIllegalValueException() {
        XmlAdaptedSchedule schedule =
                new XmlAdaptedSchedule (VALID_EMPLOYEEID, INVALID_TYPE, VALID_DATE);
        String expectedMessage = Type.MESSAGE_TYPE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, schedule::toModelType);
    }

    @Test
    public void toModelType_nullType_throwsIllegalValueException() {
        XmlAdaptedSchedule schedule =
                new XmlAdaptedSchedule (VALID_EMPLOYEEID, null, VALID_DATE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Type.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, schedule::toModelType);
    }

    //-------------------------------------------//
    @Test
    public void toModelType_invalidDate_throwsIllegalValueException() {
        XmlAdaptedSchedule schedule =
                new XmlAdaptedSchedule (VALID_EMPLOYEEID, VALID_TYPE, INVALID_DATE);
        String expectedMessage = Date.MESSAGE_DATE_CONSTRAINTS_DEFAULT;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, schedule::toModelType);
    }

    @Test
    public void toModelType_nullDate_throwsIllegalValueException() {
        XmlAdaptedSchedule schedule =
                new XmlAdaptedSchedule (VALID_EMPLOYEEID, VALID_TYPE, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Date.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, schedule::toModelType);
    }

    //-------------------------------------------//

    @Test
    public void toModelType_invalidEmployeeId_throwsIllegalValueException() {
        XmlAdaptedSchedule schedule =
                new XmlAdaptedSchedule(INVALID_EMPLOYEEID, VALID_TYPE, VALID_DATE);
        String expectedMessage = EmployeeId.MESSAGE_EMPLOYEEID_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, schedule::toModelType);
    }

    @Test
    public void toModelType_nullEmployeeId_throwsIllegalValueException() {
        XmlAdaptedSchedule schedule =
                new XmlAdaptedSchedule(null, VALID_TYPE, VALID_DATE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, EmployeeId.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, schedule::toModelType);
    }

    @Test
    public void equals_sameSchedule_true() {
        // same object -> returns true
        assertTrue(bensonSchedule.equals(bensonSchedule));
    }

    @Test
    public void equals_null_false() {
        // null object -> returns false
        XmlAdaptedSchedule schedule = new XmlAdaptedSchedule(BENSON_WORK);
        assertFalse(bensonSchedule.equals(null));
    }

    @Test
    public void equals_differentEmployeeId_false() {
        // different employee id -> returns false
        Schedule editedBenson = new ScheduleBuilder(BENSON_WORK).withEmployeeId(VALID_EMPLOYEEID_CARL).build();
        XmlAdaptedSchedule editedBensonSchedule = new XmlAdaptedSchedule(editedBenson);
        assertFalse(bensonSchedule.equals(editedBensonSchedule));
    }

    @Test
    public void equals_differentType_false() {
        // different type -> returns false
        Schedule editedBenson = new ScheduleBuilder(BENSON_WORK).withType(VALID_TYPE_CARL).build();
        XmlAdaptedSchedule editedBensonSchedule = new XmlAdaptedSchedule(editedBenson);
        assertFalse(bensonSchedule.equals(editedBensonSchedule));
    }

    @Test
    public void equals_differentDate_false() {
        // different date -> returns false
        Schedule editedBenson = new ScheduleBuilder(BENSON_WORK).withDate(VALID_DATE_CARL).build();
        XmlAdaptedSchedule editedBensonSchedule = new XmlAdaptedSchedule(editedBenson);
        assertFalse(bensonSchedule.equals(editedBensonSchedule));
    }

    @Test
    public void equals_sameAttributes_true() {
        // same employee id, type, date -> returns true
        Schedule benson = new ScheduleBuilder(BENSON_WORK_COPY).build();
        XmlAdaptedSchedule bensonScheduleCopy = new XmlAdaptedSchedule(benson);
        assertTrue(bensonSchedule.equals(bensonScheduleCopy));
    }

}
