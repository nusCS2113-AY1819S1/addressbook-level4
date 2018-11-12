//@@author arty9
package seedu.address.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class TaskNameTest {

    @Test
    public void constructorNullThrowsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new TaskName(null));
    }

    @Test
    public void constructorInvalidNameThrowsIllegalArgumentException() {
        String invalidName = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new TaskName(invalidName));
    }

    @Test
    public void isValidName() {
        // null name
        Assert.assertThrows(NullPointerException.class, () -> TaskName.isValidName(null));

        // invalid name
        assertFalse(TaskName.isValidName("")); // empty string
        assertFalse(TaskName.isValidName(" ")); // spaces only
        assertFalse(TaskName.isValidName(" Quiz 3")); // starts with whitespace

        // valid name
        assertTrue(TaskName.isValidName("tutorial")); // alphabets only
        assertTrue(TaskName.isValidName("12345")); // numbers only
        assertTrue(TaskName.isValidName("^$(*@&")); // non-alphanumeric characters only
        assertTrue(TaskName.isValidName("lab assignment 2")); // alphanumeric characters
        assertTrue(TaskName.isValidName("Assignment One")); // with capital letters
        assertTrue(TaskName.isValidName("Milestone v1.3")); // with non-alphanumeric characters
        assertTrue(TaskName.isValidName("#7 Quiz")); // starts with non-alphanumeric characters
        assertTrue(TaskName.isValidName("CG2028 Lab Assignment 3 for Week 4")); // long names
    }
}
