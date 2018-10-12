package seedu.address.model.expenditureinfo;

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
        // null date
        Assert.assertThrows(NullPointerException.class, () -> Date.isValidDate(null));

        // invalid categories
        assertFalse(Date.isValidDate("")); // empty string
        assertFalse(Date.isValidDate(" ")); // spaces only
        assertFalse(Date.isValidDate("1-1-2018")); // only one digit for both date and month
        assertFalse(Date.isValidDate("24-1-2018")); // only one digit for month
        assertFalse(Date.isValidDate("14-Dec-2017")); // contains alphabetical characters
        assertFalse(Date.isValidDate("24-01-18")); // only two digits for year
        assertFalse(Date.isValidDate("24012018")); // missing '-' symbol

        // valid categories
        assertTrue(Date.isValidDate("24-01-2018"));
        assertTrue(Date.isValidDate("17-09-2018"));
        assertTrue(Date.isValidDate("14-12-2018"));
    }
}
