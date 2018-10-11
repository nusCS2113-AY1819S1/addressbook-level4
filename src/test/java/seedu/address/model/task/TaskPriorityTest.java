package seedu.address.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class TaskPriorityTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new TaskPriority(null));
    }

    @Test
    public void constructor_invalidPriority_throwsIllegalArgumentException() {
        String invalidPriority = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new TaskPriority(invalidPriority));
    }

    @Test
    public void isValidPriority() {
        // null name
        Assert.assertThrows(NullPointerException.class, () -> TaskPriority.isValidPriority(null));

        // invalid name
        assertFalse(TaskPriority.isValidPriority("")); // empty string
        assertFalse(TaskPriority.isValidPriority(" ")); // spaces only
        assertFalse(TaskPriority.isValidPriority("^")); // only non-alphanumeric characters
        assertFalse(TaskPriority.isValidPriority("4")); // contains numbers not in range
        assertFalse(TaskPriority.isValidPriority("12")); // contains numbers not in range

        // valid name
        assertTrue(TaskPriority.isValidPriority("1"));
        assertTrue(TaskPriority.isValidPriority("2"));
    }
}
