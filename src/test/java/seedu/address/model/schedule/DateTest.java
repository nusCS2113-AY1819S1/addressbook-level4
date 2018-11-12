package seedu.address.model.schedule;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.model.schedule.Date.DATE_PATTERN;
import static seedu.address.model.schedule.Date.DATE_VALIDATION_REGEX;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

import org.junit.Test;

import seedu.address.testutil.Assert;


public class DateTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Date(null));
    }

    @Test
    public void constructor_invalidDate_throwsNumberFormatException() {
        String date = "";
        Assert.assertThrows(NumberFormatException.class, () -> new Date(date));
    }

    @Test
    public void isValidScheduleDate_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> Date.isValidScheduleDate(null));
    }

    @Test
    public void isValidScheduleDate_validDayNonFeb_validDate() {
        //Day boundaries 1 to 31 or 1 to 30
        assertTrue(Date.isValidScheduleDate("1/4/2050")); //day left tail boundary
        assertTrue(Date.isValidScheduleDate("30/4/2050")); //april, june, sep, nov have 30 days
        assertTrue(Date.isValidScheduleDate("31/5/2050")); //jan, mar, may, jul, aug, oct, dec have 31 days
    }

    @Test
    public void isValidScheduleDate_invalidDayNonFeb_invalidDate() {
        //Day boundaries
        assertFalse(Date.isValidScheduleDate("0/4/2050")); //day does not contain 0
        assertFalse(Date.isValidScheduleDate("31/4/2050")); //april, june, sep, nov no 31 days
        assertFalse(Date.isValidScheduleDate("32/5/2050")); //jan, mar, may, jul, aug, oct, dec no 32 days
        assertFalse(Date.isValidScheduleDate("99/5/2050")); //jan, mar, may, jul, aug, oct, dec no 32 days
    }

    @Test
    public void isValidScheduleDate_validFebDayOnNonLeapYear_validDate() {
        assertTrue(Date.isValidScheduleDate("28/2/2051")); //day right tail boundary for feb non leap
    }

    @Test
    public void isValidScheduleDate_invalidFebDayOnNonLeapYear_invalidDate() {
        assertFalse(Date.isValidScheduleDate("29/2/2051")); //day right tail boundary for feb non leap
    }


    @Test
    public void isValidScheduleDate_validFebDayOnLeapYear_validDate() {
        assertTrue(Date.isValidScheduleDate("29/2/2040")); //day right tail boundary for feb leap
    }

    @Test
    public void isValidScheduleDate_invalidFebDayOnLeapYear_invalidDate() {
        assertFalse(Date.isValidScheduleDate("30/2/2040")); //day right tail boundary for feb leap
    }

    @Test
    public void isValidScheduleDate_validMonth_validDate() {
        //Month boundaries 1 to 12
        assertTrue(Date.isValidScheduleDate("1/1/2050")); //month left tail boundary
        assertTrue(Date.isValidScheduleDate("1/12/2050")); //month right tail boundary
    }

    @Test
    public void isValidScheduleDate_invalidMonth_invalidDate() {
        //Month boundaries
        assertFalse(Date.isValidScheduleDate("1/0/2050")); //month does not contain 0
        assertFalse(Date.isValidScheduleDate("1/13/2050")); //month does not contain 13
        assertFalse(Date.isValidScheduleDate("1/99/2050")); //month does not contain 99 (Max right bound)
    }

    @Test
    public void isValidScheduleDate_validYear_validDate() {
        //Year boundaries valid from 2000 to 2099 only
        assertTrue(Date.isValidScheduleDate("1/1/2000")); //year left tail boundary
        assertTrue(Date.isValidScheduleDate("1/12/2099")); //year right tail boundary
    }

    @Test
    public void isValidScheduleDate_invalidYear_invalidDate() {
        //Year boundaries valid from 2000 to 2099 only
        assertFalse(Date.isValidScheduleDate("1/0/0000")); //year 0000 (Max left bound)
        assertFalse(Date.isValidScheduleDate("1/0/1999")); //year 1999 (Min left bound)
        assertFalse(Date.isValidScheduleDate("1/13/2100")); //year 2100 (Min right bound)
        assertFalse(Date.isValidScheduleDate("1/13/9999")); //year 9999 (Max right bound)
    }

    @Test
    public void isMatchedDateValidationRegex_validRegex_validDate() {
        assertTrue(("1/4/2050").matches(DATE_VALIDATION_REGEX)); //no leading 0 in day or month accepted
        assertTrue(("01/04/2050").matches(DATE_VALIDATION_REGEX)); //leading 0 in day or month also accepted
    }

    @Test
    public void isMatchedDateValidationRegex_invalidRegex_invalidDate() {
        assertFalse(("001/04/2050").matches(DATE_VALIDATION_REGEX)); //extra leading 0s in day
        assertFalse(("001/004/2050").matches(DATE_VALIDATION_REGEX)); //extra leading 0s in month
        assertFalse(("01/04/02050").matches(DATE_VALIDATION_REGEX)); //extra leading 0s in year
        assertFalse(("a1/4/2050").matches(DATE_VALIDATION_REGEX)); //contains alpha-numeric
        assertFalse(("1-4-2050").matches(DATE_VALIDATION_REGEX)); //Not DD/MM/YYYY format
    }

    @Test
    public void dateConstraintsErrorToString_validString_correctStringRepresentation() {
        Date expected = new Date("09/09/2099");
        expected.setDateConstraintsError("expected string");
        assertEquals("expected string", expected.getDateConstraintsError());
    }

    @Test
    public void formatDate_validString_correctStringRepresentation() {
        Date expected = new Date("09/09/2099");
        String actual = Date.formatDate("9/9/2099");
        assertEquals(expected.toString(), actual);
    }

    @Test
    public void isBeforeTodayDate_validTodayDate_notBeforeToday() {
        assertFalse(Date.isBeforeTodayDate(Date.todayDate()));
    }

    @Test
    public void isBeforeTodayDate_validYesterdayDate_isBeforeToday() {
        LocalDate today = LocalDate.now();
        LocalDate yesterday = today.minus(Period.ofDays(1));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_PATTERN);
        assertTrue(Date.isBeforeTodayDate(formatter.format(yesterday)));
    }

    @Test
    public void dateComparable_validDate_hashCodeIsCorrect() {
        Date expected = new Date("09/09/2099");
        assertEquals("09/09/2099".hashCode(), expected.hashCode());

    }

}
