package seedu.address.model.leave;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class DateTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Date(null));
    }

    @Test
    public void constructor_invalidDate_throwsNumberFormatException() {
        String invalidTimeEnd = "";
        Assert.assertThrows(NumberFormatException.class, () -> new Date(invalidTimeEnd));
    }

    @Test
    public void isValidDate() {
        // null date
        Assert.assertThrows(NullPointerException.class, () -> Date.isValidDate(null));

        // invalid date with less parameters
        Assert.assertThrows(ArrayIndexOutOfBoundsException.class, () -> Date.isValidDate("12/2019")); // missing day
        Assert.assertThrows(ArrayIndexOutOfBoundsException.class, () -> Date.isValidDate("31/2019")); // missing month
        Assert.assertThrows(ArrayIndexOutOfBoundsException.class, () -> Date.isValidDate("31/12")); // missing year

        // invalid date with more parameters
        assertFalse(Date.isValidDate("31/31/12/2019"));

        // invalid date with empty parameters
        Assert.assertThrows(NumberFormatException.class, () -> Date.isValidDate("/12/2019")); // empty day
        Assert.assertThrows(NumberFormatException.class, () -> Date.isValidDate("31//2019")); // empty month
        Assert.assertThrows(ArrayIndexOutOfBoundsException.class, () -> Date.isValidDate("31/12/")); // empty year

        //invalid date due to leap years
        assertFalse(Date.isValidDate("30/02/2020"));

        //invalid date in feb
        assertFalse(Date.isValidDate("30/02/2019"));

        // invalid date with negative parameters
        assertFalse(Date.isValidDate("-1/12/2019")); // negative day
        assertFalse(Date.isValidDate("31/-1/2019")); // negative month
        assertFalse(Date.isValidDate("31/12/-1")); // negative year

        // invalid date with exceeding parameters
        assertFalse(Date.isValidDate("32/12/2019")); // day exceed 31
        assertFalse(Date.isValidDate("31/13/2019")); // month exceed 12

        // invalid date with past date
        assertFalse(Date.isValidDate("31/12/1990"));

        // valid date
        assertTrue(Date.isValidDate("31/12/2020"));

        //invalid 31st date
        assertFalse(Date.isValidDate("31/04/2019"));
    }

    @Test
    public void hashCode_sameObject_equals() {
        Date date = new Date("31/12/2020");
        int expectedHash = date.hashCode();
        assertEquals(date.hashCode(), expectedHash);
    }

    @Test
    public void hashCode_differentObject_equals() {
        Date date = new Date("31/12/2020");
        int expectedHash = date.hashCode();
        Date sameDate = new Date("31/12/2020");
        assertEquals(sameDate.hashCode(), expectedHash);
    }

    @Test
    public void hashCode_differentValues_notEquals() {
        Date date = new Date("31/12/2020");
        int expectedHash = date.hashCode();
        Date sameDate = new Date("31/12/2021");
        assertNotEquals(sameDate.hashCode(), expectedHash);
    }
}
