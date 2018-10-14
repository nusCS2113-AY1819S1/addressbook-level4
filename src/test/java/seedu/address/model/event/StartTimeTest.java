package seedu.address.model.event;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class StartTimeTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Description(null));
    }

    @Test
    public void constructor_invalidStartTime_throwsIllegalArgumentException() {
        String invalidTime = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new StartTime(invalidTime));
    }

    @Test
    public void isValidTime() {
        // null name
        Assert.assertThrows(NullPointerException.class, () -> StartTime.isValidTime(null));

        // invalid name
        assertFalse(StartTime.isValidTime("")); // empty string
        assertFalse(StartTime.isValidTime(" ")); // spaces only
        assertFalse(StartTime.isValidTime("1200")); //invalid time
        assertFalse(StartTime.isValidTime("25:00")); //invalid time
        assertFalse(StartTime.isValidTime("12:61")); //invalid time
        assertFalse(StartTime.isValidTime("2pm")); //invalid time with characters

        // valid name
        assertTrue(StartTime.isValidTime("00:00")); // with 4 digits
        assertTrue(StartTime.isValidTime("12:30")); // with 4 digits
        assertTrue(StartTime.isValidTime("23:59")); // with 4 digits

    }
}
