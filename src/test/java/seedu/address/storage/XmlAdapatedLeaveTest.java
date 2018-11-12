package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static seedu.address.storage.XmlAdaptedLeave.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.TypicalLeave.REQUEST_1;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.leave.Approval;
import seedu.address.model.leave.Date;
import seedu.address.model.leave.EmployeeId;
import seedu.address.model.prioritylevel.PriorityLevel;
import seedu.address.testutil.Assert;

//@@author Hafizuddin-NUS
public class XmlAdapatedLeaveTest {

    private static final String INVALID_NRIC = "S1212AA";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_APPROVAL = "pendings";
    private static final String INVALID_DATE = "20/01/20";
    private static final int INVALID_PRIORITY = 9;

    private static final String VALID_NRIC = REQUEST_1.getEmployeeId().toString();
    private static final String VALID_DATE = REQUEST_1.getDate().toString();
    private static final String VALID_APPROVAL = REQUEST_1.getApproval().toString();
    private static final int VALID_PRIORITY = REQUEST_1.getPriorityLevel().priorityLevelCode;

    @Test
    public void toModelType_validLeaveDetails_returnsLeave() throws Exception {
        XmlAdaptedLeave leave = new XmlAdaptedLeave(REQUEST_1);
        assertEquals(REQUEST_1, leave.toModelType());
    }

    @Test
    public void toModelType_invalidNric_throwsIllegalValueException() {
        XmlAdaptedLeave leave =
                new XmlAdaptedLeave(INVALID_NRIC, VALID_DATE, VALID_APPROVAL, VALID_PRIORITY);
        String expectedMessage = EmployeeId.MESSAGE_NRIC_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, leave::toModelType);
    }

    @Test
    public void toModelType_nullNric_throwsIllegalValueException() {
        XmlAdaptedLeave leave = new XmlAdaptedLeave(null, VALID_DATE, VALID_APPROVAL, VALID_PRIORITY);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, EmployeeId.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, leave::toModelType);
    }


    @Test
    public void toModelType_nullDate_throwsIllegalValueException() {
        XmlAdaptedLeave leave = new XmlAdaptedLeave(VALID_NRIC, null, VALID_APPROVAL, VALID_PRIORITY);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Date.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, leave::toModelType);
    }

    @Test
    public void toModelType_nullApproval_throwsIllegalValueException() {
        XmlAdaptedLeave leave = new XmlAdaptedLeave(VALID_NRIC, VALID_DATE , null, VALID_PRIORITY);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Approval.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, leave::toModelType);
    }

    @Test
    public void toModelType_invalidPriority_throwsIllegalValueException() {
        XmlAdaptedLeave leave = new XmlAdaptedLeave(VALID_NRIC, VALID_DATE , VALID_APPROVAL, INVALID_PRIORITY);
        String expectedMessage = PriorityLevel.MESSAGE_PRIORITY_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, leave::toModelType);
    }

    @Test
    public void toModelType_invalidDate_throwsIllegalValueException() {
        XmlAdaptedLeave leave = new XmlAdaptedLeave(VALID_NRIC, INVALID_DATE , VALID_APPROVAL, VALID_PRIORITY);
        String expectedMessage = Date.MESSAGE_DATE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, leave::toModelType);
    }

    @Test
    public void toModelType_invalidApproval_throwsIllegalValueException() {
        XmlAdaptedLeave leave = new XmlAdaptedLeave(VALID_NRIC, VALID_DATE , INVALID_APPROVAL, VALID_PRIORITY);
        String expectedMessage = Approval.MESSAGE_APPROVAL_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, leave::toModelType);
    }
}
