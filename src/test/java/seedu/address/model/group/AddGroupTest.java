package seedu.address.model.group;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GROUP_LOCATION_CS1010;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GROUP_NAME_CS1010;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GROUP_TAG_CS1010;
import static seedu.address.testutil.TypicalAddGroups.ADD_GROUP_1;
import static seedu.address.testutil.TypicalAddGroups.ADD_GROUP_2;
import static seedu.address.testutil.TypicalAddGroups.ADD_GROUP_3;
import static seedu.address.testutil.TypicalGroups.CS1010;
import static seedu.address.testutil.TypicalGroups.TUT_1;
import static seedu.address.testutil.TypicalPersonIndexs.getSingleTypicalPersonIndexs;
import static seedu.address.testutil.TypicalPersonIndexs.getTypicalPersonIndexs;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.testutil.Assert;
import seedu.address.testutil.GroupBuilder;

public class AddGroupTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new AddGroup(null,null));
    }

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        AddGroup addGroup = ADD_GROUP_1;
        thrown.expect(UnsupportedOperationException.class);
        addGroup.getPersonIndexes().remove(0);
    }

    @Test
    public void constructor_invalidGroupName_throwsIllegalArgumentException() {
        String invalidGroupName = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new AddGroup(new GroupName(invalidGroupName),
                getSingleTypicalPersonIndexs()));
    }

    @Test
    public void equals() {
        // same values -> returns true
        AddGroup addGroup1Copy = new AddGroup(TUT_1.getGroupName(),getTypicalPersonIndexs());
        assertTrue(ADD_GROUP_1.equals(addGroup1Copy));

        // same object -> returns true
        assertTrue(ADD_GROUP_1.equals(ADD_GROUP_1));

        // null -> returns false
        assertFalse(ADD_GROUP_1.equals(null));

        // different type -> returns false
        assertFalse(ADD_GROUP_1.equals(5));

        // different addGroup -> returns false
        assertFalse(ADD_GROUP_1.equals(ADD_GROUP_3));

        // different group name -> returns false
        assertFalse(ADD_GROUP_1.equals(ADD_GROUP_2));

        // different person indexs -> returns false
        assertFalse(ADD_GROUP_1.equals(ADD_GROUP_2));
    }


}
