package seedu.address.model.group;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GROUP_NAME_TUT_1;
import static seedu.address.testutil.TypicalGroups.CS1010;
import static seedu.address.testutil.TypicalGroups.TUT_1;
import static seedu.address.testutil.TypicalGroups.getTut1;

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

    @Test
    public void equals() {
        Group group = getTut1();
        GroupName expected = group.getGroupName();

        // same object -> returns true
        assertTrue(expected.equals(expected));

        // same values -> returns true
        GroupName actual = new GroupName(VALID_GROUP_NAME_TUT_1);
        assertTrue(expected.equals(actual));

        // different types -> returns false
        assertFalse(expected.equals(1));

        // null -> returns false
        assertFalse(expected.equals(null));

        // different group name -> returns false
        GroupName actual2 = new GroupName("stub");
        assertFalse(expected.equals(actual2));
    }

    @Test
    public void toStringTest(){
        String expected = VALID_GROUP_NAME_TUT_1;

        Group group = getTut1();
        String actual = group.getGroupName().toString();

        assertEquals(expected, actual);
        assertNotEquals(expected, " ");
    }

}
