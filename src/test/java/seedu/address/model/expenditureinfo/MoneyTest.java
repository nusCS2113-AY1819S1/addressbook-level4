package seedu.address.model.expenditureinfo;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class MoneyTest {

    @Test
    public void constructorNullThrowsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Money(null));
    }

    @Test
    public void constructorInvalidMoneyThrowsIllegalArgumentException() {
        String invalidMoney = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Money(invalidMoney));
    }

    @Test
    public void isValidMoney() {
        // null money
        Assert.assertThrows(NullPointerException.class, () -> Money.isValidMoney(null));

        // invalid categories
        assertFalse(Money.isValidMoney("")); // empty string
        assertFalse(Money.isValidMoney(" ")); // spaces only
        assertFalse(Money.isValidMoney("32-")); // contains alphanumeric symbol
        assertFalse(Money.isValidMoney(".3")); // missing digits before '.'

        // valid categories
        assertTrue(Money.isValidMoney("12")); // integer
        assertTrue(Money.isValidMoney("+12")); // integer with '+' symbol in the front
        assertTrue(Money.isValidMoney("-12")); // integer with '-' symbol in the front
        assertTrue(Money.isValidMoney("4.")); // incomplete floating point number without digits after '.'
        assertTrue(Money.isValidMoney("+4.222")); // floating point number with '+' symbol in the front
        assertTrue(Money.isValidMoney("-4.222")); // floating point number with '-' symbol in the front
    }
}
