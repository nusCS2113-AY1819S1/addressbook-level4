package seedu.address.model.schedule;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class DateComparatorTest {
    private final DateComparator dateCompare = new DateComparator();

    @Test
    public void compare_validLaterToEarlierDate_laterDateGreaterThanEarlierDateTrue() {
        Date earlierDate = new Date("02/02/2019");
        Date laterDate = new Date("03/03/2019");
        int result = dateCompare.compare(laterDate, earlierDate);
        assertEquals(1, result);
    }

    @Test
    public void compare_validEarlierToLaterDate_earlierDateGreaterThanLaterDateFalse() {
        Date earlierDate = new Date("02/02/2019");
        Date laterDate = new Date("03/03/2019");
        int result = dateCompare.compare(earlierDate, laterDate);
        assertEquals(-1, result);
    }

    @Test
    public void compare_validSameDate_equal() {
        Date date = new Date("03/03/2019");
        int result = dateCompare.compare(date, date);
        assertEquals(0, result);
    }

}
