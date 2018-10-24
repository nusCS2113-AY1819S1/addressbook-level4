package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static seedu.address.storage.XmlAdaptedTask.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.TypicalTasks.CS2102_homework;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.task.Deadline;
import seedu.address.model.task.PriorityLevel;
import seedu.address.testutil.Assert;

//@@author chelseyong
public class XmlAdaptedTaskTest {
    private static final String INVALID_DEADLINE = "#$@(";
    private static final String INVALID_PRIORITY_LEVEL = "midhigh";
    private static final String INVALID_EXPECTED_NUM_OF_HOURS = "one";
    //private static final String INVALID_TAG = "#friend";

    private static final String VALID_DEADLINE = CS2102_homework.getDeadline().toString();
    private static final String VALID_TITLE = CS2102_homework.getTitle();
    private static final String VALID_DESCRIPTION = CS2102_homework.getDescription();
    private static final String VALID_PRIORITY_LEVEL = CS2102_homework.getPriorityLevel().toString();
    private static final String VALID_EXPECTED_NUM_OF_HOURS = Integer.toString(CS2102_homework.getExpectedNumOfHours());
    /*private static final List<XmlAdaptedTag> VALID_TAGS = BENSON.getTags().stream()
            .map(XmlAdaptedTag::new)
            .collect(Collectors.toList());
    */

    @Test
    public void toModelType_validTaskDetails_returnsTask() throws Exception {
        XmlAdaptedTask task = new XmlAdaptedTask(CS2102_homework);
        assertEquals(CS2102_homework, task.toModelType());
    }

    @Test
    public void toModelType_invalidDeadline_throwsIllegalValueException() {
        XmlAdaptedTask task =
                new XmlAdaptedTask(INVALID_DEADLINE, VALID_TITLE, VALID_DESCRIPTION, VALID_PRIORITY_LEVEL, VALID_EXPECTED_NUM_OF_HOURS, null, false);
        String expectedMessage = Deadline.MESSAGE_DEADLINE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, task::toModelType);
    }

    @Test
    public void toModelType_nullDeadline_throwsIllegalValueException() {
        XmlAdaptedTask task = new XmlAdaptedTask(null, VALID_TITLE, VALID_DESCRIPTION, VALID_PRIORITY_LEVEL, VALID_EXPECTED_NUM_OF_HOURS, null, false);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Deadline.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, task::toModelType);
    }

    @Test
    public void toModelType_invalidPriorityLevel_throwsIllegalValueException() {
        XmlAdaptedTask task =
                new XmlAdaptedTask(VALID_DEADLINE, VALID_TITLE, VALID_DESCRIPTION, INVALID_PRIORITY_LEVEL, VALID_EXPECTED_NUM_OF_HOURS, null, false);
        String expectedMessage = PriorityLevel.MESSAGE_PRIORITY_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, task::toModelType);
    }

    @Test
    public void toModelType_nullPriorityLevel_throwsIllegalValueException() {
        XmlAdaptedTask person = new XmlAdaptedTask(VALID_DEADLINE, VALID_TITLE, VALID_DESCRIPTION, null, VALID_EXPECTED_NUM_OF_HOURS, null, false);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, PriorityLevel.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidExpectedNumOfHours_throwsIllegalValueException() {
        XmlAdaptedTask task =
                new XmlAdaptedTask(VALID_DEADLINE, VALID_TITLE, VALID_DESCRIPTION, VALID_PRIORITY_LEVEL, INVALID_EXPECTED_NUM_OF_HOURS, null, false);
        String expectedMessage = "Expected number of hours have to be an integer";
        Assert.assertThrows(NumberFormatException.class, task::toModelType);
    }

    @Test
    public void toModelType_nullExpectedNumOfHours_throwsIllegalValueException() {
        XmlAdaptedTask person = new XmlAdaptedTask(VALID_DEADLINE, VALID_TITLE, VALID_DESCRIPTION, VALID_PRIORITY_LEVEL, null, null, false);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "Expected number of hours expected to complete");
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

     @Test
    public void toModelType_nullTitle_throwsIllegalValueException() {
        XmlAdaptedTask task = new XmlAdaptedTask(VALID_DEADLINE, null, VALID_DESCRIPTION, VALID_PRIORITY_LEVEL, VALID_EXPECTED_NUM_OF_HOURS, null, false);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "Title");
        Assert.assertThrows(IllegalValueException.class, expectedMessage, task::toModelType);
    }

    @Test
    public void toModelType_nullDescription_throwsIllegalValueException() {
        XmlAdaptedTask task = new XmlAdaptedTask(VALID_DEADLINE, VALID_TITLE, null, VALID_PRIORITY_LEVEL, VALID_EXPECTED_NUM_OF_HOURS, null, false);
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
