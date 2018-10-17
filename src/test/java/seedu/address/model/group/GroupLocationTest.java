package seedu.address.model.group;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GROUP_LOCATION_TUT_1;
import static seedu.address.testutil.TypicalGroups.getTut1;

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

    @Test
    public void equals() {
        Group group = getTut1();
        GroupLocation expected = group.getGroupLocation();

        // same object -> returns true
        assertTrue(expected.equals(expected));

        // same values -> returns true
        GroupLocation actual = new GroupLocation(VALID_GROUP_LOCATION_TUT_1);
        assertTrue(expected.equals(actual));

        // different types -> returns false
        assertFalse(expected.equals(1));

        // null -> returns false
        assertFalse(expected.equals(null));

        // different group location -> returns false
        GroupLocation actual2 = new GroupLocation("stub");
        assertFalse(expected.equals(actual2));
    }

    @Test
    public void toStringTest() {
        String expected = VALID_GROUP_LOCATION_TUT_1;

        Group group = getTut1();
        String actual = group.getGroupLocation().toString();

        assertEquals(expected, actual);
        assertNotEquals(expected, " ");
    }

}
