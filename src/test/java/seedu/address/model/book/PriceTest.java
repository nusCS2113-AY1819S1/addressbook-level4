package seedu.address.model.book;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class PriceTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Price(null));
    }

    @Test
    public void constructor_invalidPrice_throwsIllegalArgumentException() {
        String invalidPrice = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Price(invalidPrice));
    }

    @Test
    public void isValidPrice() {
        // null price
        Assert.assertThrows(NullPointerException.class, () -> Price.isValidPrice(null));

        // blank price
        assertFalse(Price.isValidPrice("")); // empty string
        assertFalse(Price.isValidPrice(" ")); // spaces only

        // missing parts
        assertFalse(Price.isValidPrice("5.5")); // One decimal, no sign
        assertFalse(Price.isValidPrice("$6.6")); // One decimal, with sign
        assertFalse(Price.isValidPrice("$")); // missing domain

        // invalid parts
        assertFalse(Price.isValidPrice("6.012")); // too many decimals
        assertFalse(Price.isValidPrice("-3")); // no negative integers
        assertFalse(Price.isValidPrice("a1.2")); // alphabet in string

        // valid email
        assertTrue(Price.isValidPrice("0.10")); // Omit dollar sign
        assertTrue(Price.isValidPrice("$0.00")); // Dollar sign included
        assertTrue(Price.isValidPrice("5")); // No decimals
        assertTrue(Price.isValidPrice("$0.10")); // Only cents. No dollar.
    }
}
