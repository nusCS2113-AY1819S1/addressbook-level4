//@@author Lunastryke
package seedu.address.model.drink;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.format.DateTimeParseException;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.Assert;

class BatchDateTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new BatchDate(null));
    }

    @Test
    public void constructor_invalidDate_throwsIllegalArgumentException() {
        String invalidDate = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new BatchDate(invalidDate));
    }

    @Test
    public void constructor_nonExistingDate_throwsDateTimeParseException() {
        String invalidDate = "30/2/2018";
        Assert.assertThrows(DateTimeParseException.class, () -> new BatchDate(invalidDate));
    }

    @Test
    void isValidDate() {
        // null date
        Assert.assertThrows(NullPointerException.class, () -> BatchDate.isValidDate(null));

        // invalid date
        assertFalse(BatchDate.isValidDate("")); // empty string
        assertFalse(BatchDate.isValidDate(" ")); // spaces only
        assertFalse(BatchDate.isValidDate("^")); // only non-alphanumeric characters
        assertFalse(BatchDate.isValidDate("*1/10/2018")); // contains non-alphanumeric characters
        assertFalse(BatchDate.isValidDate("2018/10/15")); // date format YYYY/MM/DD
        assertFalse(BatchDate.isValidDate("2018/15/10")); // date format YYYY/DD/MM
        assertFalse(BatchDate.isValidDate("10/15/2018")); // date format MM/DD/YYYY
        assertFalse(BatchDate.isValidDate("15/10/18")); // date format DD/MM/YY
        assertFalse(BatchDate.isValidDate("15-10-2018")); // other date separators

        // valid date
        assertTrue(BatchDate.isValidDate("15/10/2018")); // date format DD/MM/YYYY
        assertTrue(BatchDate.isValidDate("15/1/2018")); // date format DD/M/YYYY
        assertTrue(BatchDate.isValidDate("1/10/2018")); // date format D/MM/YYYY
        assertTrue(BatchDate.isValidDate("1/1/2018")); // date format D/M/YYYY
    }
}
