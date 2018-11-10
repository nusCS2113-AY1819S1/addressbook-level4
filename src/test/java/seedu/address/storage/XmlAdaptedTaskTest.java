package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.OVERFLOW_INT;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_HOURS;
import static seedu.address.storage.XmlAdaptedTask.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.TypicalTasks.CS2102_HOMEWORK;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.task.Deadline;
import seedu.address.model.task.ModuleCode;
import seedu.address.model.task.PriorityLevel;
import seedu.address.testutil.Assert;

//@@author chelseyong
public class XmlAdaptedTaskTest {
    private static final String INVALID_DEADLINE = "#$@(";
    private static final String INVALID_PRIORITY_LEVEL = "midhigh";
    private static final String INVALID_EXPECTED_NUM_OF_HOURS = "one";
    private static final String ZERO_EXPECTED_NUM_OF_HOURS = "0";
    private static final String OVERFLOW_EXPECTED_NUM_OF_HOURS = Long.toString((long) OVERFLOW_INT + 1);
    private static final String INVALID_MODULE_CODE = "2113CS";
    //private static final String INVALID_TAG = "#friend";

    private static final String VALID_DEADLINE = CS2102_HOMEWORK.getDeadline().toString();
    private static final String VALID_MODULECODE = CS2102_HOMEWORK.getModuleCode().toString();
    private static final String VALID_TITLE = CS2102_HOMEWORK.getTitle();
    private static final String VALID_DESCRIPTION = CS2102_HOMEWORK.getDescription();
    private static final String VALID_PRIORITY_LEVEL = CS2102_HOMEWORK.getPriorityLevel().toString();
    private static final String VALID_EXPECTED_NUM_OF_HOURS = Integer.toString(CS2102_HOMEWORK.getExpectedNumOfHours());
    /*private static final List<XmlAdaptedTag> VALID_TAGS = BENSON.getTags().stream()
            .map(XmlAdaptedTag::new)
            .collect(Collectors.toList());
    */

    @Test
    public void toModelType_validTaskDetails_returnsTask() throws Exception {
        XmlAdaptedTask task = new XmlAdaptedTask(CS2102_HOMEWORK);
        assertEquals(CS2102_HOMEWORK, task.toModelType());
    }

    @Test
    public void toModelType_invalidDeadline_throwsIllegalValueException() {
        XmlAdaptedTask task =
                new XmlAdaptedTask(INVALID_DEADLINE, VALID_MODULECODE, VALID_TITLE, VALID_DESCRIPTION,
                        VALID_PRIORITY_LEVEL, VALID_EXPECTED_NUM_OF_HOURS);
        String expectedMessage = Deadline.MESSAGE_DEADLINE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, task::toModelType);
    }

    @Test
    public void toModelType_nullDeadline_throwsIllegalValueException() {
        XmlAdaptedTask task = new XmlAdaptedTask(null, VALID_MODULECODE, VALID_TITLE, VALID_DESCRIPTION,
                VALID_PRIORITY_LEVEL, VALID_EXPECTED_NUM_OF_HOURS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Deadline.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, task::toModelType);
    }

    @Test
    public void toModelType_invalidPriorityLevel_throwsIllegalValueException() {
        XmlAdaptedTask task =
                new XmlAdaptedTask(VALID_DEADLINE, VALID_MODULECODE, VALID_TITLE, VALID_DESCRIPTION,
                        INVALID_PRIORITY_LEVEL, VALID_EXPECTED_NUM_OF_HOURS);
        String expectedMessage = PriorityLevel.MESSAGE_PRIORITY_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, task::toModelType);
    }

    @Test
    public void toModelType_nullPriorityLevel_throwsIllegalValueException() {
        XmlAdaptedTask person = new XmlAdaptedTask(VALID_DEADLINE, VALID_MODULECODE, VALID_TITLE,
                VALID_DESCRIPTION, null, VALID_EXPECTED_NUM_OF_HOURS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, PriorityLevel.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidModuleCode_throwsIllegalValueException() {
        XmlAdaptedTask task =
                new XmlAdaptedTask(VALID_DEADLINE, INVALID_MODULE_CODE, VALID_TITLE, VALID_DESCRIPTION,
                        VALID_PRIORITY_LEVEL, VALID_EXPECTED_NUM_OF_HOURS);
        String expectedMessage = ModuleCode.MESSAGE_MODULE_CODE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, task::toModelType);
    }

    @Test
    public void toModelType_invalidExpectedNumOfHours_throwsIllegalValueException() {
        XmlAdaptedTask task =
                new XmlAdaptedTask(VALID_DEADLINE, VALID_MODULECODE, VALID_TITLE, VALID_DESCRIPTION,
                        VALID_PRIORITY_LEVEL, INVALID_EXPECTED_NUM_OF_HOURS);
        Assert.assertThrows(IllegalValueException.class, MESSAGE_INVALID_HOURS, task::toModelType);
    }

    @Test
    public void toModelType_zeroExpectedNumOfHours_throwsIllegalValueException() {
        XmlAdaptedTask task =
                new XmlAdaptedTask(VALID_DEADLINE, VALID_MODULECODE, VALID_TITLE, VALID_DESCRIPTION,
                        VALID_PRIORITY_LEVEL, ZERO_EXPECTED_NUM_OF_HOURS);
        Assert.assertThrows(IllegalValueException.class, MESSAGE_INVALID_HOURS, task::toModelType);
    }

    @Test
    public void toModelType_overflowExpectedNumOfHours_throwsIllegalValueException() {
        XmlAdaptedTask task =
                new XmlAdaptedTask(VALID_DEADLINE, VALID_MODULECODE, VALID_TITLE, VALID_DESCRIPTION,
                        VALID_PRIORITY_LEVEL, OVERFLOW_EXPECTED_NUM_OF_HOURS);
        Assert.assertThrows(IllegalValueException.class, MESSAGE_INVALID_HOURS, task::toModelType);
    }

    @Test
    public void toModelType_nullExpectedNumOfHours_throwsIllegalValueException() {
        XmlAdaptedTask person = new XmlAdaptedTask(VALID_DEADLINE, VALID_MODULECODE, VALID_TITLE, VALID_DESCRIPTION,
                VALID_PRIORITY_LEVEL, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT,
                "Expected number of hours expected to complete");
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullTitle_throwsIllegalValueException() {
        XmlAdaptedTask task = new XmlAdaptedTask(VALID_DEADLINE, VALID_MODULECODE, null, VALID_DESCRIPTION,
                VALID_PRIORITY_LEVEL, VALID_EXPECTED_NUM_OF_HOURS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "Title");
        Assert.assertThrows(IllegalValueException.class, expectedMessage, task::toModelType);
    }

    @Test
    public void toModelType_nullDescription_throwsIllegalValueException() {
        XmlAdaptedTask task = new XmlAdaptedTask(VALID_DEADLINE, VALID_MODULECODE, VALID_TITLE, null,
                VALID_PRIORITY_LEVEL, VALID_EXPECTED_NUM_OF_HOURS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "Description");
        Assert.assertThrows(IllegalValueException.class, expectedMessage, task::toModelType);
    }

    /*@Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<XmlAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new XmlAdaptedTag(INVALID_TAG));
        XmlAdaptedTask person =
                new XmlAdaptedTask(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, invalidTags);
        Assert.assertThrows(IllegalValueException.class, person::toModelType);
    }*/

}
