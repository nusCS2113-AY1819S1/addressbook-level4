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
        assertFalse(AgeRange.isValidAgeRange("61-30")); // exceed age limit
        assertFalse(AgeRange.isValidAgeRange("15-30")); // below age limit

        // valid age range
        assertTrue(AgeRange.isValidAgeRange("45-60"));
        assertTrue(AgeRange.isValidAgeRange("60-16")); // boundary test
    }
}
