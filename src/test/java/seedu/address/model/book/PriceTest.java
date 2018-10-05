package seedu.address.model.book;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class PriceTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Price(null));
    }

    @Test
    public void constructor_invalidEmail_throwsIllegalArgumentException() {
        String invalidEmail = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Price(invalidEmail));
    }

    @Test
    public void isValidEmail() {
        // null email
        Assert.assertThrows(NullPointerException.class, () -> Price.isValidPrice(null));

        // blank email
        assertFalse(Price.isValidPrice("")); // empty string
        assertFalse(Price.isValidPrice(" ")); // spaces only

        // missing parts
        assertFalse(Price.isValidPrice("@example.com")); // missing local part
        assertFalse(Price.isValidPrice("peterjackexample.com")); // missing '@' symbol
        assertFalse(Price.isValidPrice("peterjack@")); // missing domain name

        // invalid parts
        assertFalse(Price.isValidPrice("peterjack@-")); // invalid domain name
        assertFalse(Price.isValidPrice("peterjack@exam_ple.com")); // underscore in domain name
        assertFalse(Price.isValidPrice("peter jack@example.com")); // spaces in local part
        assertFalse(Price.isValidPrice("peterjack@exam ple.com")); // spaces in domain name
        assertFalse(Price.isValidPrice(" peterjack@example.com")); // leading space
        assertFalse(Price.isValidPrice("peterjack@example.com ")); // trailing space
        assertFalse(Price.isValidPrice("peterjack@@example.com")); // double '@' symbol
        assertFalse(Price.isValidPrice("peter@jack@example.com")); // '@' symbol in local part
        assertFalse(Price.isValidPrice("peterjack@example@com")); // '@' symbol in domain name
        assertFalse(Price.isValidPrice("peterjack@.example.com")); // domain name starts with a period
        assertFalse(Price.isValidPrice("peterjack@example.com.")); // domain name ends with a period
        assertFalse(Price.isValidPrice("peterjack@-example.com")); // domain name starts with a hyphen
        assertFalse(Price.isValidPrice("peterjack@example.com-")); // domain name ends with a hyphen

        // valid email
        assertTrue(Price.isValidPrice("PeterJack_1190@example.com"));
        assertTrue(Price.isValidPrice("a@bc")); // minimal
        assertTrue(Price.isValidPrice("test@localhost")); // alphabets only
        assertTrue(Price.isValidPrice("!#$%&'*+/=?`{|}~^.-@example.org")); // special characters local part
        assertTrue(Price.isValidPrice("123@145")); // numeric local part and domain name
        assertTrue(Price.isValidPrice("a1+be!@example1.com")); // mixture of alphanumeric and special characters
        assertTrue(Price.isValidPrice("peter_jack@very-very-very-long-example.com")); // long domain name
        assertTrue(Price.isValidPrice("if.you.dream.it_you.can.do.it@example.com")); // long local part
    }
}
