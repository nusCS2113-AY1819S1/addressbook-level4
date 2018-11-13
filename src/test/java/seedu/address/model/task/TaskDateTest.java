//@@author arty9
package seedu.address.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class TaskDateTest {

    @Test
    public void constructorNullThrowsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new TaskDate(null));
    }

    @Test
    public void constructorInvalidDateThrowsIllegalArgumentException() {
        String invalidDate = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new TaskDate(invalidDate));
    }

    @Test
    public void isValidDate() {
        // null date
        Assert.assertThrows(NullPointerException.class, () -> TaskDate.isValidDate(null));

        // invalid date
        assertFalse(TaskDate.isValidDate("")); // empty string
        assertFalse(TaskDate.isValidDate(" ")); // spaces only
        assertFalse(TaskDate.isValidDate("^")); // only non-alphanumeric characters
        assertFalse(TaskDate.isValidDate("16-Sep")); // contains alphabetical characters
        assertFalse(TaskDate.isValidDate("10-0*")); // contains non-alphanumeric characters
        assertFalse(TaskDate.isValidDate("23-9")); // less than 2 numbers for month
        assertFalse(TaskDate.isValidDate("10-123")); // more than 2 numbers for month
        assertFalse(TaskDate.isValidDate("9-12")); // less than 2 numbers for day
        assertFalse(TaskDate.isValidDate("322-12")); // more than 2 numbers for day
        assertFalse(TaskDate.isValidDate("1407")); // missing '-' symbol
        assertFalse(TaskDate.isValidDate("32-09")); // day out of maximum logical limit
        assertFalse(TaskDate.isValidDate("00-09")); // day out of minimum logical limit
        assertFalse(TaskDate.isValidDate("15-13")); // month out of maximum logical limit
        assertFalse(TaskDate.isValidDate("15-00")); // month out of minimum logical limit
        assertFalse(TaskDate.isValidDate("31-04")); // day out of limit for month with 30 days
        assertFalse(TaskDate.isValidDate("30-02")); // day out of limit for February
        assertFalse(TaskDate.isValidDate("29-02")); // day out of limit for February on non-leap year

        // valid date
        assertTrue(TaskDate.isValidDate("01-07"));
        assertTrue(TaskDate.isValidDate("31-07"));
        assertTrue(TaskDate.isValidDate("23-01"));
        assertTrue(TaskDate.isValidDate("23-12"));
    }
}
