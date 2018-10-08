package seedu.address.model.group;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class GroupLocationTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new GroupLocation(null));
    }

    @Test
    public void constructor_invalidGroupLocation_throwsIllegalArgumentException() {
        String invalidGroupLocation = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new GroupLocation(invalidGroupLocation));
    }

    @Test
    public void isValidGroupLocation() {
        // null group location
        Assert.assertThrows(NullPointerException.class, () -> GroupLocation.isValidGroupLocation(null));

        // invalid group location
        assertFalse(GroupLocation.isValidGroupLocation("")); // empty string
        assertFalse(GroupLocation.isValidGroupLocation(" ")); // spaces only

        // valid group location
        assertTrue(GroupLocation.isValidGroupLocation("E1-01-01"));
        assertTrue(GroupLocation.isValidGroupLocation("-")); // one character
    }

}
