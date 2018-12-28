package seedu.address.model.person.addinfo;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.format.DateTimeParseException;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class DateOfBirthTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new DateOfBirth(null));
    }

    @Test
    public void constructor_invalidDateOfBirth_throwsDateTimeParseException() {
        Assert.assertThrows(DateTimeParseException.class, () -> new DateOfBirth("50-01-1970")); // invalid date
        Assert.assertThrows(DateTimeParseException.class, () -> new DateOfBirth("01-20-1970")); // invalid month
    }

    @Test
    public void isValidDate() {
        // null date
        Assert.assertThrows(NullPointerException.class, () -> DateOfBirth.isValidDate(null));

        // invalid date
        assertFalse(DateOfBirth.isValidDate("")); // empty string
        assertFalse(DateOfBirth.isValidDate(" ")); // space only
        assertFalse(DateOfBirth.isValidDate("01/01/1970")); // wrong format

        // valid date
        assertTrue(DateOfBirth.isValidDate("01-01-1970"));
        assertTrue(DateOfBirth.isValidDate("10-10-2010"));
    }
}
