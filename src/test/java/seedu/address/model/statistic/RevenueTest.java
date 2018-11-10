package seedu.address.model.statistic;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class RevenueTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Revenue(null));
    }

    @Test
    public void constructor_invalidRevenue_throwsIllegalArgumentException() {
        String invalidRevenue = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Revenue(invalidRevenue));
    }

    @Test
    public void isValidRevenue() {
        // null price
        Assert.assertThrows(NullPointerException.class, () -> Revenue.isValidRevenue(null));

        // blank price
        assertFalse(Revenue.isValidRevenue("")); // empty string
        assertFalse(Revenue.isValidRevenue(" ")); // spaces only


        // invalid parts
        assertFalse(Revenue.isValidRevenue("6.012")); // too many decimals
        assertFalse(Revenue.isValidRevenue("-3")); // no negative integers
        assertFalse(Revenue.isValidRevenue("a1.2")); // alphabet in string

        // valid revenue
        assertTrue(Revenue.isValidRevenue("5")); // No decimals
        assertTrue(Revenue.isValidRevenue("0.12")); //2 decimal place
        assertTrue(Revenue.isValidRevenue("1123.10")); // Only decimals
    }
}
