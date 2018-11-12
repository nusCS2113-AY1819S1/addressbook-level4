package seedu.address.model.budgetelements;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class ClubNameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new ClubName(null));
    }

    @Test
    public void constructor_invalidClubName_throwsIllegalArgumentException() {
        String invalidClubName = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new ClubName(invalidClubName));
    }

    @Test
    public void isValidClubName() {
        // null clubName
        Assert.assertThrows(NullPointerException.class, () -> ClubName.isValidClubName(null));

        // invalid clubName
        assertFalse(ClubName.isValidClubName("")); // empty string
        assertFalse(ClubName.isValidClubName(" ")); // spaces only
        assertFalse(ClubName.isValidClubName("^")); // only non-alphanumeric characters
        assertFalse(ClubName.isValidClubName("computing*")); // contains non-alphanumeric characters

        // valid clubName
        assertTrue(ClubName.isValidClubName("computing club")); // alphabets only
        assertTrue(ClubName.isValidClubName("12345")); // numbers only
        assertTrue(ClubName.isValidClubName("computing the 2nd")); // alphanumeric characters
        assertTrue(ClubName.isValidClubName("Computing Club")); // with capital letters
        assertTrue(ClubName.isValidClubName("Computing Club SOC the 2nd")); // long names
    }
}
