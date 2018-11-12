package seedu.address.model.person;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class DateOfBirthTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new DateOfBirth(null));
    }

    @Test
    public void constructor_invalidDateOfBirth_throwsIllegalArgumentException() {
        String invalidDateOfBirth = "";
        Assert.assertThrows(NumberFormatException.class, () -> new DateOfBirth(invalidDateOfBirth));
    }

    @Test
    public void isValidDateOfBirth() {
        // null date of birth
        Assert.assertThrows(NullPointerException.class, () -> DateOfBirth.isValidDateOfBirth(null));

        // invalid dates of birth
        assertFalse(DateOfBirth.isValidDateOfBirth("")); // empty string
        assertFalse(DateOfBirth.isValidDateOfBirth(" ")); // spaces only
        assertFalse(DateOfBirth.isValidDateOfBirth("91")); // numbers only, no forward slash
        assertFalse(DateOfBirth.isValidDateOfBirth("phone")); // non-numeric
        assertFalse(DateOfBirth.isValidDateOfBirth("9011p041")); // alphabets within digits
        assertFalse(DateOfBirth.isValidDateOfBirth("9312 1534")); // spaces within digits
        assertFalse(DateOfBirth.isValidDateOfBirth("31/02/1992")); // february 30th is invalid
        assertFalse(DateOfBirth.isValidDateOfBirth("24/1/1800")); // year out of range 1900 - 2002
        assertFalse(DateOfBirth.isValidDateOfBirth("24/01/2012")); // year out of range 1900 - 2002
        assertFalse(DateOfBirth.isValidDateOfBirth("31/9/1993")); // september 31st is invalid
        assertFalse(DateOfBirth.isValidDateOfBirth("31/4/1993")); // april 31st is invalid
        assertFalse(DateOfBirth.isValidDateOfBirth("31/6/1993")); // june 31st is invalid
        assertFalse(DateOfBirth.isValidDateOfBirth("31/11/1993")); // november 31st is invalid
        assertFalse(DateOfBirth.isValidDateOfBirth("29/02/2001")); // non leap year so 29/2 is invalid

        // valid dates of birth
        assertTrue(DateOfBirth.isValidDateOfBirth("12/06/1990")); // exactly 10 characters with 2 forward slash
        assertTrue(DateOfBirth.isValidDateOfBirth("28/02/1993")); // exactly 10 characters with 2 forward slash
    }

    @Test
    public void hashCodeSameObject_equals() {
        DateOfBirth expectedHashCode = new DateOfBirth("12/05/1990");
        assertEquals("12/05/1990".hashCode(), expectedHashCode.hashCode());
    }

    @Test
    public void hashCodeDifferentValue_notEquals() {
        DateOfBirth expectedHashCode = new DateOfBirth("12/05/1990");
        assertNotEquals("15/05/1990".hashCode(), expectedHashCode.hashCode());
    }
}
