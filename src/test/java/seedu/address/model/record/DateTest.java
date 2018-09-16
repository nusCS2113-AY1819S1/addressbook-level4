package seedu.address.model.record;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class DateTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Date(null));
    }

    @Test
    public void constructor_invalidDate_throwsIllegalArgumentException() {
        String invalidDate = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Date(invalidDate));
    }

    @Test
    public void isValidDate() {
        // null day parameter
        Assert.assertThrows(NullPointerException.class, () -> Date.isValidDate(null));

        // invalid day parameters
        assertFalse(Date.isValidDate("")); // empty string
        assertFalse(Date.isValidDate(" ")); // spaces only
        assertFalse(Date.isValidDate("91")); // less than 3 numbers
        assertFalse(Date.isValidDate("dates")); // non-numeric
        assertFalse(Date.isValidDate("9011p041")); // alphabets within digits
        assertFalse(Date.isValidDate("9312 1534")); // spaces within digits
        assertFalse(Date.isValidDate("93121534")); // Missing a "-" in the format of dd-mm-yyyy
        assertFalse(Date.isValidDate("931-21-34")); // 'dd' parameter has more than 2 digits
        assertFalse(Date.isValidDate("-21-34")); // 'dd' parameter has less than 1 digit
        assertFalse(Date.isValidDate("93-215-34")); // 'mm' parameter has more than 2 digits
        assertFalse(Date.isValidDate("93--34")); // 'mm' parameter has less than 1 digit
        assertFalse(Date.isValidDate("93-21-34")); // 'yyyy' does not have exactly 4 digits

        // valid day parameters
        assertTrue(Date.isValidDate("11-11-1911")); // exactly in the form dd-mm-yyyy
        assertTrue(Date.isValidDate("1-1-1911"));   // dd or mm can be 1 digit
    }
}
