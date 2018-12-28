package seedu.address.model.medhistory;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

class PrevCountryTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new PrevCountry(null));
    }

    @Test
    public void constructor_invalidPrevCountry_throwsIllegalArgumentException() {
        String invalidPrevCountry = "%^&";
        Assert.assertThrows(IllegalArgumentException.class, () -> new PrevCountry(invalidPrevCountry));
    }

    @Test
    public void isValidPrevCountry() {
        // null prevCountry
        Assert.assertThrows(NullPointerException.class, () -> PrevCountry.isValidPrevCountry(null));

        // invalid prevCountry
        assertFalse(PrevCountry.isValidPrevCountry("^")); // only non-alphanumeric characters
        assertFalse(PrevCountry.isValidPrevCountry("peter*")); // contains non-alphanumeric characters

        // valid prevCountry
        assertTrue(PrevCountry.isValidPrevCountry("hungary")); // alphabets only
        assertTrue(PrevCountry.isValidPrevCountry("94892834")); // numbers only
        assertTrue(PrevCountry.isValidPrevCountry("china numbah 1")); // alphanumeric characters
        assertTrue(PrevCountry.isValidPrevCountry("CHINA")); // with capital letters
        assertTrue(PrevCountry.isValidPrevCountry("china the rival of usa")); // long names
    }
}
