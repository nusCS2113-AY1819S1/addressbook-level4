package seedu.address.model.drink;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DRINK_COST_PRICE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DRINK_RETAIL_PRICE;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.testutil.Assert;

public class PriceTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Price(null));
    }

    @Test
    public void constructor_invalidPrice_throwsIllegalArgumentException() {
        String invalidPrice = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Price(invalidPrice));
        Assert.assertThrows(IllegalArgumentException.class, () -> new Price("bla"));
    }

    @Test
    public void setValue_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        Price price = new Price(VALID_DRINK_COST_PRICE);
        price.setValue(null);
    }

    @Test
    public void setValue_invalidPrice_throwsIllegalArgumentException() {
        thrown.expect(IllegalArgumentException.class);
        Price price = new Price(VALID_DRINK_COST_PRICE);
        price.setValue("bla");
    }

    @Test
    public void setValue_validPrice_checkValueSet() {
        Price price = new Price(VALID_DRINK_COST_PRICE);
        price.setValue(VALID_DRINK_RETAIL_PRICE);
        Price otherPrice = new Price(VALID_DRINK_RETAIL_PRICE);
        assertEquals(price, otherPrice);

    }

    @Test
    public void isValidPrice() {
        // null price
        Assert.assertThrows(NullPointerException.class, () -> Price.isValidPrice(null));

        // invalid prices
        assertFalse(Price.isValidPrice("")); // empty string
        assertFalse(Price.isValidPrice(" ")); // spaces only
        assertFalse(Price.isValidPrice("9.1234395893")); // more than 2 decimal digits
        assertFalse(Price.isValidPrice(".25")); // only decimal digits
        assertFalse(Price.isValidPrice(".")); // only decimal dot
        assertFalse(Price.isValidPrice("12.")); // nothing after decimal dot
        assertFalse(Price.isValidPrice("price")); // non-numeric
        assertFalse(Price.isValidPrice("9011p041")); // alphabets within digits
        assertFalse(Price.isValidPrice("9312 1534")); // spaces within digits
        assertFalse(Price.isValidPrice("123456789023456789012345678901234567890123456789012345678901234567890"));
        // very large prices, more than Float.MAX_VALUE
        assertFalse(Price.isValidPrice("-1.25")); // negative values

        // valid prices
        assertTrue(Price.isValidPrice("0")); // 0
        assertTrue(Price.isValidPrice("9")); // 1 digit
        assertTrue(Price.isValidPrice("9.2")); // 1 digit with 1 decimal digit
        assertTrue(Price.isValidPrice("9.31")); // 1 digit with 2 decimal digits
        assertTrue(Price.isValidPrice("92231.23")); // reasonably large prices, less than Float.MAX_VALUE
    }

}
