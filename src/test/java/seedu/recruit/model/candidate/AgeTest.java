package seedu.recruit.model.candidate;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.recruit.testutil.Assert;

public class AgeTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Age(null));
    }

    @Test
    public void constructor_invalidAge_throwsIllegalArgumentException() {
        String invalidAge = "a1";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Age(invalidAge));
    }

    @Test
    public void isValidAge() {
        // null age
        Assert.assertThrows(NullPointerException.class, () -> Age.isValidAge(null));

        // invalid age
        assertFalse(Age.isValidAge("")); // empty string
        assertFalse(Age.isValidAge("-2")); // negative age
        assertFalse(Age.isValidAge("99")); // out of range
        assertFalse(Age.isValidAge("61")); // boundary testing
        assertFalse(Age.isValidAge(" 15"));

        // valid age
        assertTrue(Age.isValidAge("45"));
        assertTrue(Age.isValidAge("16")); // boundary tests
        assertTrue(Age.isValidAge("60"));
    }
}
