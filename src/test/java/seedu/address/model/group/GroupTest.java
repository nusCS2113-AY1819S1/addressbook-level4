package seedu.address.model.group;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GROUP_LOCATION_CS1010;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GROUP_NAME_CS1010;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GROUP_TAG_CS1010;
import static seedu.address.testutil.TypicalGroups.CS1010;
import static seedu.address.testutil.TypicalGroups.TUT_1;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.rules.ExpectedException;

import seedu.address.testutil.GroupBuilder;

public class GroupTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Group group = new GroupBuilder().build();
        thrown.expect(UnsupportedOperationException.class);
        group.getTags().remove(0);
    }

    @Test
    public void isSameGroup() {
        // same object -> returns true
        assertTrue(TUT_1.isSameGroup(TUT_1));

        // null -> returns false
        assertFalse(TUT_1.isSameGroup(null));

        // different group location -> returns false
        Group editedTut1 = new GroupBuilder(TUT_1).withGroupLocation(VALID_GROUP_LOCATION_CS1010).build();
        assertFalse(TUT_1.isSameGroup(editedTut1));

        // different group name -> returns false
        editedTut1 = new GroupBuilder(TUT_1).withGroupName(VALID_GROUP_NAME_CS1010).build();
        assertFalse(TUT_1.isSameGroup(editedTut1));

        // same group name, same location, different tags -> returns false
        editedTut1 = new GroupBuilder(TUT_1).withTags(VALID_GROUP_TAG_CS1010).build();
        assertFalse(TUT_1.isSameGroup(editedTut1));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Group tut1Copy = new GroupBuilder(TUT_1).build();
        assertTrue(TUT_1.equals(tut1Copy));

        // same object -> returns true
        assertTrue(TUT_1.equals(TUT_1));

        // null -> returns false
        assertFalse(TUT_1.equals(null));

        // different type -> returns false
        assertFalse(TUT_1.equals(5));

        // different group -> returns false
        assertFalse(TUT_1.equals(CS1010));

        // different group name -> returns false
        Group editedTut1 = new GroupBuilder(TUT_1).withGroupName(VALID_GROUP_NAME_CS1010).build();
        assertFalse(TUT_1.equals(editedTut1));

        // different group location -> returns false
        editedTut1 = new GroupBuilder(TUT_1).withGroupLocation(VALID_GROUP_LOCATION_CS1010).build();
        assertFalse(TUT_1.equals(editedTut1));

        // different tags -> returns false
        editedTut1 = new GroupBuilder(TUT_1).withTags(VALID_GROUP_TAG_CS1010).build();
        assertFalse(TUT_1.equals(editedTut1));
    }

}
