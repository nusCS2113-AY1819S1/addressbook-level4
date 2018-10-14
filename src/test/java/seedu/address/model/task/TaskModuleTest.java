package seedu.address.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class TaskModuleTest {

    @Test
    public void constructorNullThrowsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new TaskModule(null));
    }

    @Test
    public void constructorInvalidModuleThrowsIllegalArgumentException() {
        String invalidModule = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new TaskModule(invalidModule));
    }

    @Test
    public void isValidModule() {
        // null module code
        Assert.assertThrows(NullPointerException.class, () -> TaskModule.isValidModule(null));

        // invalid module code
        assertFalse(TaskModule.isValidModule("")); // empty string
        assertFalse(TaskModule.isValidModule(" ")); // spaces only
        assertFalse(TaskModule.isValidModule("^")); // only non-alphanumeric characters
        assertFalse(TaskModule.isValidModule("CS*207")); // contains non-alphanumeric characters
        assertFalse(TaskModule.isValidModule("CS101")); // less than 4 numbers
        assertFalse(TaskModule.isValidModule("CS12313")); // more than 4 numbers
        assertFalse(TaskModule.isValidModule("C1112")); // less than 2 alphabetical characters
        assertFalse(TaskModule.isValidModule("CEG1112")); // more than 2 alphabetical characters
        assertFalse(TaskModule.isValidModule("IS")); // missing alphabetical characters
        assertFalse(TaskModule.isValidModule("1112")); // missing 2 alphabetical characters

        // valid module code
        assertTrue(TaskModule.isValidModule("CS2102")); // all capital letters
        assertTrue(TaskModule.isValidModule("Cs2113")); // start with capital letter
        assertTrue(TaskModule.isValidModule("cS2101")); // mix of capital and non capital letters
        assertTrue(TaskModule.isValidModule("cs1231")); // no capital letters
    }
}
