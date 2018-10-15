package seedu.address.model.group;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GROUP_LOCATION_CS1010;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GROUP_LOCATION_TUT_1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GROUP_TAG_CS1010;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GROUP_TAG_TUT_1;
import static seedu.address.testutil.TypicalGroups.CS1010;
import static seedu.address.testutil.TypicalGroups.TUT_1;
import static seedu.address.testutil.TypicalGroups.TUT_2;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.group.exceptions.DuplicateGroupException;
import seedu.address.model.group.exceptions.GroupNotFoundException;
import seedu.address.testutil.GroupBuilder;

public class UniqueGroupListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final UniqueGroupList uniqueGroupList = new UniqueGroupList();

    @Test
    public void contains_nullGroup_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueGroupList.contains((Group) null);
    }

    @Test
    public void contains_groupNotInList_returnsFalse() {
        assertFalse(uniqueGroupList.contains(TUT_1));
    }

    @Test
    public void contains_groupInList_returnsTrue() {
        uniqueGroupList.createGroup(TUT_1);
        assertTrue(uniqueGroupList.contains(TUT_1));
    }

    @Test
    public void contains_groupWithSameIdentityFieldsInList_returnsTrue() {
        uniqueGroupList.createGroup(TUT_1);
        Group editedTut1 = new GroupBuilder(TUT_1)
                .withGroupLocation(VALID_GROUP_LOCATION_TUT_1)
                .withTags(VALID_GROUP_TAG_TUT_1)
                .build();
        assertTrue(uniqueGroupList.contains(editedTut1));
    }

    @Test
    public void add_nullGroup_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueGroupList.createGroup(null);
    }

    @Test
    public void create_duplicateGroup_throwsDuplicateGroupException() {
        uniqueGroupList.createGroup(TUT_1);
        thrown.expect(DuplicateGroupException.class);
        uniqueGroupList.createGroup(TUT_1);
    }

    @Test
    public void setGroups_nullList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueGroupList.setGroups((List<Group>) null);
    }

    @Test
    public void setGroups_list_replacesOwnListWithProvidedList() {
        uniqueGroupList.createGroup(TUT_1);
        List<Group> groupList = Collections.singletonList(CS1010);
        uniqueGroupList.setGroups(groupList);
        UniqueGroupList expectedUniqueGroupList = new UniqueGroupList();
        expectedUniqueGroupList.createGroup(CS1010);
        assertEquals(expectedUniqueGroupList, uniqueGroupList);
    }

    @Test
    public void setGroups_listWithDuplicateGroups_throwsDuplicateGroupException() {
        List<Group> listWithDuplicateGroups = Arrays.asList(TUT_1, TUT_1);
        thrown.expect(DuplicateGroupException.class);
        uniqueGroupList.setGroups(listWithDuplicateGroups);
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        uniqueGroupList.asUnmodifiableObservableList().remove(0);
    }

}
