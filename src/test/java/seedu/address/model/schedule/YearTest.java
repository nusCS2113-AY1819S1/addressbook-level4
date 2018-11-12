package seedu.address.model.schedule;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.model.schedule.Year.YEAR_VALIDATION_REGEX;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class YearTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Year(null));
    }

    @Test
    public void constructor_invalidYear_throwsNumberFormatException() {
        String year = "";
        Assert.assertThrows(NumberFormatException.class, () -> new Year(year));
    }

    @Test
    public void isValidScheduleYear_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> Year.isValidYear(null));
    }

    @Test
    public void isValidScheduleYear_validYear_validYearTrue() {
        //Year boundaries 2000 to 2099
        assertTrue(Year.isValidYear("2000")); //year left tail boundary
        assertTrue(Year.isValidYear("2050")); //middle
        assertTrue(Year.isValidYear("2099")); //year right tail boundary
    }

    @Test
    public void isValidScheduleYear_inValidYear_validYearFalse() {
        //Year boundaries invalid 0000-1999 and 2100 to 9999
        assertFalse(Year.isValidYear("0000")); //year left Max tail boundary
        assertFalse(Year.isValidYear("1999")); //year left Max tail boundary
        assertFalse(Year.isValidYear("2100")); //year right Min tail boundary
        assertFalse(Year.isValidYear("9999")); //year right Max tail boundary
    }

    @Test
    public void isMatchedYearValidationRegex_invalidRegex_invalidYear() {
        assertFalse(("02000").matches(YEAR_VALIDATION_REGEX)); //extra leading 0s in year
        assertFalse(("2000a").matches(YEAR_VALIDATION_REGEX)); //contains alpha-numeric
        assertFalse(("18").matches(YEAR_VALIDATION_REGEX)); //not 4 digits
    }

    @Test
    public void yearComparable_validYear_hashCodeIsCorrect() {
        Year expected = new Year("2099");
        assertEquals("2099".hashCode(), expected.hashCode());
    }

    @Test
    public void toString_validYear_correctStringRepresentation() {
        Year expected = new Year("2099");
        assertEquals("2099", expected.toString());
    }

}
