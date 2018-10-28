//@@author Lunastryke
package seedu.address.model.drink;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import org.junit.jupiter.api.Test;

import seedu.address.model.testutil.Assert;

class DateTest {

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
    public void constructor_nonExistingDate_throwsDateTimeParseException() {
        String invalidDate = "30/2/2018";
        Assert.assertThrows(DateTimeParseException.class, () -> new Date(invalidDate));
    }

    @Test
    public void constructor_emptyField_accepted() {
        Date defaultDate = new Date();
        LocalDate localDate = LocalDate.now();
        assertTrue(defaultDate.getDate().equals(localDate));
    }

    @Test
    void isValidDate() {
        // null date
        Assert.assertThrows(NullPointerException.class, () -> Date.isValidDate(null));

        // invalid date
        assertFalse(Date.isValidDate("")); // empty string
        assertFalse(Date.isValidDate(" ")); // spaces only
        assertFalse(Date.isValidDate("^")); // only non-alphanumeric characters
        assertFalse(Date.isValidDate("*1/10/2018")); // contains non-alphanumeric characters
        assertFalse(Date.isValidDate("2018/10/15")); // date format YYYY/MM/DD
        assertFalse(Date.isValidDate("2018/15/10")); // date format YYYY/DD/MM
        assertFalse(Date.isValidDate("10/15/2018")); // date format MM/DD/YYYY
        assertFalse(Date.isValidDate("15/10/18")); // date format DD/MM/YY
        assertFalse(Date.isValidDate("15-10-2018")); // other date separators

        // valid date
        assertTrue(Date.isValidDate("15/10/2018")); // date format DD/MM/YYYY
        assertTrue(Date.isValidDate("15/1/2018")); // date format DD/M/YYYY
        assertTrue(Date.isValidDate("1/10/2018")); // date format D/MM/YYYY
        assertTrue(Date.isValidDate("1/1/2018")); // date format D/M/YYYY
    }

    @Test
    void isBefore() {
        String validDate = "22/10/2018";
        Date date = new Date(validDate);
        Date dateBefore = new Date("21/10/2018");
        Date dateAfter = new Date("23/10/2018");
        Date dateEquals = new Date(validDate);

        // not before
        assertFalse(date.isBefore(dateBefore));
        assertFalse(date.isBefore(dateEquals));

        // is before
        assertTrue(date.isBefore(dateAfter));
    }

    @Test
    void isAfter() {
        String validDate = "22/10/2018";
        Date date = new Date(validDate);
        Date dateBefore = new Date("21/10/2018");
        Date dateAfter = new Date("23/10/2018");
        Date dateEquals = new Date(validDate);

        // not after
        assertFalse(date.isAfter(dateAfter));
        assertFalse(date.isAfter(dateEquals));

        // is after
        assertTrue(date.isAfter(dateBefore));
    }
}
