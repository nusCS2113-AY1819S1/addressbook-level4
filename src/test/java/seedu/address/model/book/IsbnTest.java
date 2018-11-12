package seedu.address.model.book;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class IsbnTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Price(null));
    }

    @Test
    public void constructor_invalidIsbn_throwsIllegalArgumentException() {
        String invalidIsbn = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Isbn (invalidIsbn));
    }

    @Test
    public void isValidIsbn () {
        // null isbn
        Assert.assertThrows(NullPointerException.class, () -> Isbn.isValidIsbn(null));

        // blank isbn
        assertFalse(Isbn.isValidIsbn("")); // empty string
        assertFalse(Isbn.isValidIsbn(" ")); // spaces only

        // not 10 or 13 numbers
        assertFalse(Isbn.isValidIsbn("987654321")); // 9 numbers
        assertFalse(Isbn.isValidIsbn("98765432101")); // 11 numbers
        assertFalse(Isbn.isValidIsbn("98765432110123")); // 14 numbers

        // invalid parts
        assertFalse(Isbn.isValidIsbn("978!3!16!148410!0")); // special characters
        assertFalse(Isbn.isValidIsbn("978d3c16b148410a0")); // alphabet present

        // invalid isbn numbers with 10 and 13 numbers
        assertFalse(Isbn.isValidIsbn("1234567890")); // invalid 10 digit isbn
        assertFalse(Isbn.isValidIsbn("1234567890123")); // invalid 13 digit isbn

        // valid isbn numbers
        assertTrue(Isbn.isValidIsbn("99921-58-10-7")); // valid 10 digit isbn
        assertTrue(Isbn.isValidIsbn("978-3-16-148410-0")); // valid 13 digit isbn
        assertTrue(Isbn.isValidIsbn("9783161484100")); // valid 13 digit isbn without hyphens
    }
}
