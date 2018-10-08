package seedu.address.model.group;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.model.person.Name;
import seedu.address.testutil.Assert;

public class GroupNameTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Name(null));
    }

    @Test
    public void constructor_invalidGroupName_throwsIllegalArgumentException() {
        String invalidGroupName = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new GroupName(invalidGroupName));
    }

    @Test
    public void isValidGroupName() {
        // null group name
        Assert.assertThrows(NullPointerException.class, () -> GroupName.isValidGroupName(null));

        // invalid group name
        assertFalse(GroupName.isValidGroupName("")); // empty string
        assertFalse(GroupName.isValidGroupName(" ")); // spaces only
        assertFalse(GroupName.isValidGroupName("/")); // only non-alphanumeric characters
        assertFalse(GroupName.isValidGroupName("TUT[1]*")); // contains non-alphanumeric characters

        // valid group name
        assertTrue(GroupName.isValidGroupName("computing")); // alphabets only
        assertTrue(GroupName.isValidGroupName("12345")); // numbers only
        assertTrue(GroupName.isValidGroupName("cs1010")); // alphanumeric characters
        assertTrue(GroupName.isValidGroupName("MathsTutorial")); // with capital letters
        assertTrue(GroupName.isValidGroupName("MathsTutorialForWeakStudents")); // long names
    }

}
