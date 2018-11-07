package seedu.address.model.timeidentifiedclass;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class TimeIdentifiedClassTest {

    private static final String CORRECT_DATETIME_FORMAT = "\\d{4}/\\d{2}/\\d{2} \\d{2}:\\d{2}:\\d{2}";
    private static final String CORRECT_DATE_FORMAT = "\\d{4}/\\d{2}/\\d{2}";

    @Test
    public void getCurrentDateAndTime() {
        assertTrue(TimeIdentifiedClass.getCurrentDateAndTime().matches(CORRECT_DATETIME_FORMAT));
        assertTrue(TimeIdentifiedClass.isValidDateAndTime(TimeIdentifiedClass.getCurrentDateAndTime()));
    }

    @Test
    public void getCurrentDate() {
        assertTrue(TimeIdentifiedClass.getCurrentDate().matches(CORRECT_DATE_FORMAT));
        assertTrue(TimeIdentifiedClass.isValidDate(TimeIdentifiedClass.getCurrentDate()));
    }

    @Test
    public void isValidMonth_invalidMonths() {
        // null, empty, and whitespaces-only months
        assertFalse(TimeIdentifiedClass.isValidMonth(null));
        assertFalse(TimeIdentifiedClass.isValidMonth(""));
        assertFalse(TimeIdentifiedClass.isValidMonth(" "));
        // non-numeric
        assertFalse(TimeIdentifiedClass.isValidMonth("t3"));
        // single digit
        assertFalse(TimeIdentifiedClass.isValidMonth("2"));
        // three digits
        assertFalse(TimeIdentifiedClass.isValidMonth("002"));
        // 0 month
        assertFalse(TimeIdentifiedClass.isValidMonth("00"));
        // 13 month
        assertFalse(TimeIdentifiedClass.isValidMonth("13"));
    }

    @Test
    public void isValidMonth_validMonths() {
        // 1st month
        assertTrue(TimeIdentifiedClass.isValidMonth("01"));
        // 12th month
        assertTrue(TimeIdentifiedClass.isValidMonth("12"));
        // 6th month
        assertTrue(TimeIdentifiedClass.isValidMonth("06"));
    }

    @Test
    public void isValidDay_invalidDays() {
        // null, empty, and whitespace-only days
        assertFalse(TimeIdentifiedClass.isValidDay(null));
        assertFalse(TimeIdentifiedClass.isValidDay(""));
        assertFalse(TimeIdentifiedClass.isValidDay(" "));
        // non-numeric day
        assertFalse(TimeIdentifiedClass.isValidDay("d3"));
        // single digit day
        assertFalse(TimeIdentifiedClass.isValidDay("2"));
        // three digit day
        assertFalse(TimeIdentifiedClass.isValidDay("002"));
        // 0 day
        assertFalse(TimeIdentifiedClass.isValidDay("00"));
        // 32 day
        assertFalse(TimeIdentifiedClass.isValidDay("32"));
    }

    @Test
    public void isValidDay_validDays() {
        // day 1
        assertTrue(TimeIdentifiedClass.isValidDay("01"));
        // day 31
        assertTrue(TimeIdentifiedClass.isValidDay("31"));
        // day 15
        assertTrue(TimeIdentifiedClass.isValidDay("15"));
    }

    @Test
    public void isValidYear_invalidYears() {
        // null, empty and whitespace-only years
        assertFalse(TimeIdentifiedClass.isValidYear(null));
        assertFalse(TimeIdentifiedClass.isValidYear(""));
        assertFalse(TimeIdentifiedClass.isValidYear(" "));
        // non-numeric year
        assertFalse(TimeIdentifiedClass.isValidYear("y123"));
        // 3 digit year
        assertFalse(TimeIdentifiedClass.isValidYear("103"));
        // 5 digit year
        assertFalse(TimeIdentifiedClass.isValidYear("12345"));
    }

    @Test
    public void isValidYear_validYears() {
        // year 0000
        assertTrue(TimeIdentifiedClass.isValidYear("0000"));
        // year 9999
        assertTrue(TimeIdentifiedClass.isValidYear("9999"));
        // year 2018
        assertTrue(TimeIdentifiedClass.isValidYear("2018"));
    }

    @Test
    public void isValidDate_invalid() {
        // null, empty, whitespace-only dates
        assertFalse(TimeIdentifiedClass.isValidDate(null));
        assertFalse(TimeIdentifiedClass.isValidDate(""));
        assertFalse(TimeIdentifiedClass.isValidDate(" "));
        // not even a date
        assertFalse(TimeIdentifiedClass.isValidDate("lalala"));
        // wrong format
        assertFalse(TimeIdentifiedClass.isValidDate("12/12/2012"));
        // non-leap year february 29
        assertFalse(TimeIdentifiedClass.isValidDate("2018/02/29"));
        // leap year february 30
        assertFalse(TimeIdentifiedClass.isValidDate("2016/02/30"));
        // 0 month
        assertFalse(TimeIdentifiedClass.isValidDate("2018/00/01"));
        // 13 month
        assertFalse(TimeIdentifiedClass.isValidDate("2018/13/01"));
        // 0 day
        assertFalse(TimeIdentifiedClass.isValidDate("2018/01/00"));
        // 32 day
        assertFalse(TimeIdentifiedClass.isValidDate("2018/01/32"));
        // invalid june 31st
        assertFalse(TimeIdentifiedClass.isValidDate("2010/06/31"));
    }

    @Test
    public void isValidDate_valid() {
        // 1st jan
        assertTrue(TimeIdentifiedClass.isValidDate("2018/01/01"));
        // 31st december
        assertTrue(TimeIdentifiedClass.isValidDate("2018/12/31"));
        // leap year february 29th
        assertTrue(TimeIdentifiedClass.isValidDate("2016/02/29"));
        // non-leap year february 28th
        assertTrue(TimeIdentifiedClass.isValidDate("2018/02/28"));
        // 30th september
        assertTrue(TimeIdentifiedClass.isValidDate("2018/09/30"));
    }

    @Test
    public void isValidDateAndTime_invalid() {
        // null, empty, and whitespace only date and time
        assertFalse(TimeIdentifiedClass.isValidDateAndTime(null));
        assertFalse(TimeIdentifiedClass.isValidDateAndTime(""));
        assertFalse(TimeIdentifiedClass.isValidDateAndTime(" "));
        // non-leap year february 29
        assertFalse(TimeIdentifiedClass.isValidDate("2018/02/29 12:00:00"));
        // leap year february 30
        assertFalse(TimeIdentifiedClass.isValidDate("2016/02/30 12:00:00"));
        //invalid april 31st
        assertFalse(TimeIdentifiedClass.isValidDate("2010/04/31 12:00:00"));
        // invalid hour
        assertFalse(TimeIdentifiedClass.isValidDate("2016/06/16 24:00:00"));
        // invalid minute
        assertFalse(TimeIdentifiedClass.isValidDate("2016/06/16 23:60:00"));
        // invalid second
        assertFalse(TimeIdentifiedClass.isValidDate("2016/06/16 23:00:60"));
    }

    @Test
    public void isValidDateAndTime_valid() {
        // typical date and time
        assertTrue(TimeIdentifiedClass.isValidDateAndTime("2018/07/23 12:53:54"));
        // new year's eve
        assertTrue(TimeIdentifiedClass.isValidDateAndTime("2018/12/31 23:59:59"));
        // new year
        assertTrue(TimeIdentifiedClass.isValidDateAndTime("2019/01/01 00:00:00"));
        // february 28th on non-leap year
        assertTrue(TimeIdentifiedClass.isValidDateAndTime("2018/02/28 12:54:32"));
        // february 29th on leap year
        assertTrue(TimeIdentifiedClass.isValidDateAndTime("2020/02/29 12:32:30"));
    }
}
