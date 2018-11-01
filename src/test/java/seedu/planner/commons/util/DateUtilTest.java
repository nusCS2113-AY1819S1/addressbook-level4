package seedu.planner.commons.util;

import static org.junit.Assert.assertEquals;
import static seedu.planner.commons.util.DateUtil.compareMonth;

import org.junit.Test;

import seedu.planner.model.Month;

public class DateUtilTest {

    private final int year2018 = 2018;
    private final int year2008 = 2008;
    private final int monthApr = 4;
    private final int monthNov = 11;

    @Test
    public void compareMonth_year1LessThanYear2_returnEarlier() {
        assertMonth1EarlierThanMonth2(compareMonth((new Month(monthNov, year2008)), new Month(monthApr, year2018)));
        assertMonth1EarlierThanMonth2(compareMonth((new Month(monthApr, year2008)), new Month(monthApr, year2018)));
    }

    @Test
    public void compareMonth_year1EqualYear2Month1LessThanMonth2_returnEarlier() {
        assertMonth1EarlierThanMonth2(compareMonth((new Month(monthApr, year2018)), new Month(monthNov, year2018)));
    }

    @Test
    public void compareMonth_year1EqualYear2Month1EqualMonth2_returnEqual() {
        assertMonth1EqualMonth2(compareMonth((new Month(monthNov, year2018)), new Month(monthNov, year2018)));
    }

    @Test
    public void compareMonth_year1MoreThanYear2_returnLater() {
        assertMonth1LaterThanMonth2(compareMonth((new Month(monthApr, year2018)), new Month(monthNov, year2008)));
        assertMonth1LaterThanMonth2(compareMonth((new Month(monthApr, year2018)), new Month(monthApr, year2008)));
    }

    @Test
    public void compareMonth_year1EqualYear2Month1MoreThanMonth2_returnLater() {
        assertMonth1LaterThanMonth2(compareMonth(new Month(monthNov, year2018), new Month(monthApr, year2018)));
    }

    private void assertMonth1EarlierThanMonth2(int value) {
        assertEquals(-1, value);
    }

    private void assertMonth1EqualMonth2(int value) {
        assertEquals(0, value);
    }

    private void assertMonth1LaterThanMonth2(int value) {
        assertEquals(1, value);
    }
}
