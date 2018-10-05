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
        Assert.assertThrows(NullPointerException.class, () -> Price.isValidEmail(null));

        // blank email
        assertFalse(Price.isValidEmail("")); // empty string
        assertFalse(Price.isValidEmail(" ")); // spaces only

        // missing parts
        assertFalse(Price.isValidEmail("@example.com")); // missing local part
        assertFalse(Price.isValidEmail("peterjackexample.com")); // missing '@' symbol
        assertFalse(Price.isValidEmail("peterjack@")); // missing domain name

        // invalid parts
        assertFalse(Price.isValidEmail("peterjack@-")); // invalid domain name
        assertFalse(Price.isValidEmail("peterjack@exam_ple.com")); // underscore in domain name
        assertFalse(Price.isValidEmail("peter jack@example.com")); // spaces in local part
        assertFalse(Price.isValidEmail("peterjack@exam ple.com")); // spaces in domain name
        assertFalse(Price.isValidEmail(" peterjack@example.com")); // leading space
        assertFalse(Price.isValidEmail("peterjack@example.com ")); // trailing space
        assertFalse(Price.isValidEmail("peterjack@@example.com")); // double '@' symbol
        assertFalse(Price.isValidEmail("peter@jack@example.com")); // '@' symbol in local part
        assertFalse(Price.isValidEmail("peterjack@example@com")); // '@' symbol in domain name
        assertFalse(Price.isValidEmail("peterjack@.example.com")); // domain name starts with a period
        assertFalse(Price.isValidEmail("peterjack@example.com.")); // domain name ends with a period
        assertFalse(Price.isValidEmail("peterjack@-example.com")); // domain name starts with a hyphen
        assertFalse(Price.isValidEmail("peterjack@example.com-")); // domain name ends with a hyphen

        // valid email
        assertTrue(Price.isValidEmail("PeterJack_1190@example.com"));
        assertTrue(Price.isValidEmail("a@bc")); // minimal
        assertTrue(Price.isValidEmail("test@localhost")); // alphabets only
        assertTrue(Price.isValidEmail("!#$%&'*+/=?`{|}~^.-@example.org")); // special characters local part
        assertTrue(Price.isValidEmail("123@145")); // numeric local part and domain name
        assertTrue(Price.isValidEmail("a1+be!@example1.com")); // mixture of alphanumeric and special characters
        assertTrue(Price.isValidEmail("peter_jack@very-very-very-long-example.com")); // long domain name
        assertTrue(Price.isValidEmail("if.you.dream.it_you.can.do.it@example.com")); // long local part
    }
}
