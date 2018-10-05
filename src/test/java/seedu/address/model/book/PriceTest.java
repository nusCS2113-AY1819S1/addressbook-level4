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
        Assert.assertThrows(NullPointerException.class, () -> Price.isValidPhone(null));

        // blank email
        assertFalse(Price.isValidPhone("")); // empty string
        assertFalse(Price.isValidPhone(" ")); // spaces only

        // missing parts
        assertFalse(Price.isValidPhone("@example.com")); // missing local part
        assertFalse(Price.isValidPhone("peterjackexample.com")); // missing '@' symbol
        assertFalse(Price.isValidPhone("peterjack@")); // missing domain name

        // invalid parts
        assertFalse(Price.isValidPhone("peterjack@-")); // invalid domain name
        assertFalse(Price.isValidPhone("peterjack@exam_ple.com")); // underscore in domain name
        assertFalse(Price.isValidPhone("peter jack@example.com")); // spaces in local part
        assertFalse(Price.isValidPhone("peterjack@exam ple.com")); // spaces in domain name
        assertFalse(Price.isValidPhone(" peterjack@example.com")); // leading space
        assertFalse(Price.isValidPhone("peterjack@example.com ")); // trailing space
        assertFalse(Price.isValidPhone("peterjack@@example.com")); // double '@' symbol
        assertFalse(Price.isValidPhone("peter@jack@example.com")); // '@' symbol in local part
        assertFalse(Price.isValidPhone("peterjack@example@com")); // '@' symbol in domain name
        assertFalse(Price.isValidPhone("peterjack@.example.com")); // domain name starts with a period
        assertFalse(Price.isValidPhone("peterjack@example.com.")); // domain name ends with a period
        assertFalse(Price.isValidPhone("peterjack@-example.com")); // domain name starts with a hyphen
        assertFalse(Price.isValidPhone("peterjack@example.com-")); // domain name ends with a hyphen

        // valid email
        assertTrue(Price.isValidPhone("PeterJack_1190@example.com"));
        assertTrue(Price.isValidPhone("a@bc")); // minimal
        assertTrue(Price.isValidPhone("test@localhost")); // alphabets only
        assertTrue(Price.isValidPhone("!#$%&'*+/=?`{|}~^.-@example.org")); // special characters local part
        assertTrue(Price.isValidPhone("123@145")); // numeric local part and domain name
        assertTrue(Price.isValidPhone("a1+be!@example1.com")); // mixture of alphanumeric and special characters
        assertTrue(Price.isValidPhone("peter_jack@very-very-very-long-example.com")); // long domain name
        assertTrue(Price.isValidPhone("if.you.dream.it_you.can.do.it@example.com")); // long local part
    }
}
