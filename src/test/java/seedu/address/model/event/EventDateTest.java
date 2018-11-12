package seedu.address.model.event;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class EventDateTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new EventDate(null));
    }

    @Test
    public void constructor_invalidStartTime_throwsIllegalArgumentException() {
        String invalidDate = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new EventDate(invalidDate));
    }

    @Test
    public void isValidDate() {
        // null name
        Assert.assertThrows(NullPointerException.class, () -> EventDate.isValidDate(null));

        // contain alphabets and phrases
        assertFalse(EventDate.isValidDate("2018 Sep 10"));
        assertFalse(EventDate.isValidDate("National Day"));

        // incorrect ordering
        assertFalse(EventDate.isValidDate("12-03-2018"));
        assertFalse(EventDate.isValidDate("03-12-2018"));

        // incorrect date
        assertFalse(EventDate.isValidDate("2018-03-32"));
        assertFalse(EventDate.isValidDate("2018-03-00"));
        assertFalse(EventDate.isValidDate("2018-03-4"));

        // incorrect month
        assertFalse(EventDate.isValidDate("2018-00-10"));
        assertFalse(EventDate.isValidDate("2018-13-10"));
        assertFalse(EventDate.isValidDate("2018-3-30"));

        // incorrect year
        assertFalse(EventDate.isValidDate("18-10-30"));
        assertFalse(EventDate.isValidDate("00-10-30"));

        // incorrect format
        assertFalse(EventDate.isValidDate("2018/10/30"));
        assertFalse(EventDate.isValidDate("2018,10,30"));

        // correct date
        assertTrue(EventDate.isValidDate("2018-10-30"));
        assertTrue(EventDate.isValidDate("2018-11-01"));

    }
}
