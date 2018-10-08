package seedu.address.model.group;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GROUP_LOCATION_CS1010;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GROUP_LOCATION_TUT_1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GROUP_TAG_CS1010;
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
        uniqueGroupList.contains(null);
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
                .withTags(VALID_GROUP_TAG_CS1010)
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
    public void setGroup_nullTargetGroup_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueGroupList.setGroup(null, TUT_1);
    }

    @Test
    public void setGroup_nullEditedGroup_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueGroupList.setGroup(TUT_1, null);
    }

    @Test
    public void setGroup_targetGroupNotInList_throwsGroupNotFoundException() {
        thrown.expect(GroupNotFoundException.class);
        uniqueGroupList.setGroup(TUT_1, TUT_1);
    }

    @Test
    public void setGroup_editedGroupIsSameGroup_success() {
        uniqueGroupList.createGroup(TUT_1);
        uniqueGroupList.setGroup(TUT_1, TUT_1);
        UniqueGroupList expectedUniqueGroupList = new UniqueGroupList();
        expectedUniqueGroupList.createGroup(TUT_1);
        assertEquals(expectedUniqueGroupList, uniqueGroupList);
    }

    @Test
    public void setGroup_editedGroupHasSameIdentity_success() {
        uniqueGroupList.createGroup(TUT_1);
        Group editedTut1 = new GroupBuilder(TUT_1)
                .withGroupLocation(VALID_GROUP_LOCATION_CS1010)
                .withTags(VALID_GROUP_TAG_CS1010)
                .build();
        uniqueGroupList.setGroup(TUT_1, editedTut1);
        UniqueGroupList expectedUniqueGroupList = new UniqueGroupList();
        expectedUniqueGroupList.createGroup(editedTut1);
        assertEquals(expectedUniqueGroupList, uniqueGroupList);
    }

    @Test
    public void setGroup_editedGroupHasDifferentIdentity_success() {
        uniqueGroupList.createGroup(TUT_1);
        uniqueGroupList.setGroup(TUT_1, CS1010);
        UniqueGroupList expectedUniqueGroupList = new UniqueGroupList();
        expectedUniqueGroupList.createGroup(CS1010);
        assertEquals(expectedUniqueGroupList, uniqueGroupList);
    }

    @Test
    public void setGroup_editedGroupHasNonUniqueIdentity_throwsDuplicateGroupException() {
        uniqueGroupList.createGroup(TUT_1);
        uniqueGroupList.createGroup(CS1010);
        thrown.expect(DuplicateGroupException.class);
        uniqueGroupList.setGroup(TUT_1, CS1010);
    }

    @Test
    public void remove_nullGroup_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueGroupList.remove(null);
    }

    @Test
    public void remove_groupDoesNotExist_throwsGroupNotFoundException() {
        thrown.expect(GroupNotFoundException.class);
        uniqueGroupList.remove(TUT_2);
    }

    @Test
    public void remove_existingGroup_removesGroup() {
        uniqueGroupList.createGroup(TUT_1);
        uniqueGroupList.remove(TUT_1);
        UniqueGroupList expectedUniqueGroupList = new UniqueGroupList();
        assertEquals(expectedUniqueGroupList, uniqueGroupList);
    }

    @Test
    public void setGroups_nullUniqueGroupList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueGroupList.setGroups((UniqueGroupList) null);
    }

    @Test
    public void setGroups_uniqueGroupList_replacesOwnListWithProvidedUniqueGroupList() {
        uniqueGroupList.createGroup(TUT_1);
        UniqueGroupList expectedUniqueGroupList = new UniqueGroupList();
        expectedUniqueGroupList.createGroup(CS1010);
        uniqueGroupList.setGroups(expectedUniqueGroupList);
        assertEquals(expectedUniqueGroupList, uniqueGroupList);
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
