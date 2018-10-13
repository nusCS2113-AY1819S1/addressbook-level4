package seedu.address.model.group;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GROUP_LOCATION_TUT_1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GROUP_NAME_TUT_1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GROUP_TAG_TUT_1;
import static seedu.address.testutil.TypicalGroups.CS1010;
import static seedu.address.testutil.TypicalGroups.TUT_1;
import static seedu.address.testutil.TypicalPersonIndexs.PERSON_INDEX_1;
import static seedu.address.testutil.TypicalPersonIndexs.PERSON_INDEX_2;

import org.junit.Test;

import seedu.address.model.person.Name;
import seedu.address.model.person.PersonIndex;
import seedu.address.testutil.Assert;
import seedu.address.testutil.GroupBuilder;

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
        Group group = TUT_1;
        // same object -> returns true
        assertTrue(group.equals(group));
        // same values -> returns true
        Group groupCopy = new GroupBuilder()
                .withGroupName(VALID_GROUP_NAME_TUT_1)
                .withGroupLocation(VALID_GROUP_LOCATION_TUT_1)
                .withTags(VALID_GROUP_TAG_TUT_1).build();
        assertTrue(group.equals(groupCopy));
        // different types -> returns false
        assertFalse(group.equals(1));
        // null -> returns false
        assertFalse(group.equals(null));
        // different group -> returns false
        Group differentGroup = CS1010;
        assertFalse(group.equals(differentGroup));
    }


}
