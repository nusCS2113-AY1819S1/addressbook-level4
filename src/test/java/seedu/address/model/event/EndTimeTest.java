package seedu.address.model.event;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class EndTimeTest {

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
        Assert.assertThrows(NullPointerException.class, () -> EndTime.isValidTime(null));

        // invalid name
        assertFalse(EndTime.isValidTime("")); // empty string
        assertFalse(EndTime.isValidTime(" ")); // spaces only
        assertFalse(EndTime.isValidTime("1200")); //invalid time
        assertFalse(EndTime.isValidTime("25:00")); //invalid time
        assertFalse(EndTime.isValidTime("12:61")); //invalid time
        assertFalse(EndTime.isValidTime("2pm")); //invalid time with characters

        // valid name
        assertTrue(EndTime.isValidTime("00:00")); // with 4 digits
        assertTrue(EndTime.isValidTime("12:30")); // with 4 digits
        assertTrue(EndTime.isValidTime("23:59")); // with 4 digits

    }
}
