package seedu.planner.model.summary;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.planner.model.Month.STANDARD_MONTHS;

import org.junit.Test;

import seedu.planner.model.Month;
import seedu.planner.testutil.Assert;

public class MonthTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Month(null));
    }

    @Test
    public void constructor_invalidMonth_throwsIllegalArgumentException() {
        String invalidMonth = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Month(invalidMonth));
    }

    @Test
    public void constructor_diffRepresentations_sameMonth() {
        assertEquals(new Month("fEb-2018"), new Month("FEB-2018"));
        assertEquals(new Month("feb-2018"), new Month("FEB-2018"));
    }

    /**
     * Checks the mapping between standard representation and the month number
     */
    @Test
    public void constructor_valid_success() {
        String yearString = "2018";
        int monthNum = 1;
        for (String m : STANDARD_MONTHS) {
            String testInput = m + "-" + yearString;
            int year = Integer.parseInt(yearString);
            assertEquals(new Month(testInput), new Month(monthNum, year));
            monthNum++;
        }
    }

    @Test
    public void isValidMonthFormat() {
        // null day parameter
        Assert.assertThrows(NullPointerException.class, () -> Month.isValidMonth(null));

        // invalid month format
        assertFalse(Month.isValidMonth("")); // empty string
        assertFalse(Month.isValidMonth(" ")); // spaces only
        assertFalse(Month.isValidMonth("apr-18")); // 2-digit year
        assertFalse(Month.isValidMonth("dates-2018")); // not 3-letter word
        assertFalse(Month.isValidMonth("-2018")); // no word present before year
        assertFalse(Month.isValidMonth("apr-")); // no number present before month
        assertFalse(Month.isValidMonth("apr -2018")); // spaces in between month and year
        assertFalse(Month.isValidMonth("apr2018")); // Missing a "-" in the format of mmm-yyyy
        assertFalse(Month.isValidMonth("apr/2018")); // slashes are not allowed
        assertFalse(Month.isValidMonth("april-2018")); // word is more than 3 letters
        assertFalse(Month.isValidMonth("ap-2018")); // word is less than 3 letters
        assertFalse(Month.isValidMonth("apr--2018")); // only 1 '-' is needed

        // valid month format
        assertTrue(Month.isValidMonth("apr-2018")); // exactly in the form mmm-yyyy
    }

    /**
     * Test whether a given month is logical
     */
    @Test
    public void isValidMonth() {
        // invalid months
        assertFalse(Month.isLogicalMonth("tem")); // There is no such month

        // valid dates
        assertTrue(Month.isLogicalMonth("apr")); // APR exists
        assertTrue(Month.isLogicalMonth("Apr")); // argument is non case-sensitive
    }
}
