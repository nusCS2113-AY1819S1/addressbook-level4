package seedu.address.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class DateTimeTest {

    @Test
    public void isValidDateTime() {
        // null startdateTime
        Assert.assertThrows(NullPointerException.class, () -> DateTime.isValidStartDateTime(null));

        // invalid startDateTime
        assertFalse(DateTime.isValidStartDateTime("")); // empty string
        assertFalse(DateTime.isValidStartDateTime(" ")); // spaces only
        assertFalse(DateTime.isValidStartDateTime("30/2")); // not a valid date
        assertFalse(DateTime.isValidStartDateTime("04-12")); // not in DD/MM format
        assertFalse(DateTime.isValidStartDateTime("4/12_24:00")); // not a valid time

        // valid startDateTime
        assertTrue(DateTime.isValidStartDateTime("11/11")); // valid date
        assertTrue(DateTime.isValidStartDateTime("11/11_11:11")); // valid date and time
    }

    @Test
    public void isValidEndDateTime() {
        // null endDateTime
        Assert.assertThrows(NullPointerException.class, () -> DateTime.isValidEndDateTime(null));

        // invalid endDateTime
        assertFalse(DateTime.isValidEndDateTime("")); // empty string
        assertFalse(DateTime.isValidEndDateTime(" ")); // spaces only
        assertFalse(DateTime.isValidEndDateTime("04/12")); // without a valid time
        assertFalse(DateTime.isValidEndDateTime("30/2_14:00")); // not a valid date
        assertFalse(DateTime.isValidEndDateTime("04-12_14:00")); // not in DD/MM_HH:MM format
        assertFalse(DateTime.isValidEndDateTime("04/12_1400")); // not in DD/MM_HH:MM format
        assertFalse(DateTime.isValidEndDateTime("4/12_24:00")); // not a valid time

        // valid endDateTime
        assertTrue(DateTime.isValidEndDateTime("11/11_14:00")); // valid date and time
        assertTrue(DateTime.isValidEndDateTime("28/2_00:00")); // valid date and time
    }
}
