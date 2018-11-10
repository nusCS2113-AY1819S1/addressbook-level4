package seedu.recruit.model.joboffer;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.recruit.testutil.Assert;

public class AgeRangeTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new AgeRange(null));
    }

    @Test
    public void constructor_invalidAgeRange_throwsIllegalArgumentException() {
        String invalidAgeRange = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new AgeRange(invalidAgeRange));
    }

    @Test
    public void isValidAgeRange() {
        // null age range
        Assert.assertThrows(NullPointerException.class, () -> AgeRange.isValidAgeRange(null));

        // invalid age range
        assertFalse(AgeRange.isValidAgeRange("")); // empty string
        assertFalse(AgeRange.isValidAgeRange(" ")); // spaces only
        assertFalse(AgeRange.isValidAgeRange("12")); //
        assertFalse(AgeRange.isValidAgeRange("12-12-13"));
        assertFalse(AgeRange.isValidAgeRange("100-1")); // exceed age limit

        // valid age range
        assertTrue(AgeRange.isValidAgeRange("12-13")); // alphabets only
        assertTrue(AgeRange.isValidAgeRange("99-12")); // numbers only
    }
}
