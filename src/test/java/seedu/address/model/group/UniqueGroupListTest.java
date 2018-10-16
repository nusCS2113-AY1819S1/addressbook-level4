package seedu.address.model.group;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GROUP_LOCATION_TUT_1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GROUP_TAG_TUT_1;
import static seedu.address.testutil.TypicalAddGroups.getAddGroup1;
import static seedu.address.testutil.TypicalAddGroups.getAddGroupWithGroupAndPerson;
import static seedu.address.testutil.TypicalGroups.CS1010;
import static seedu.address.testutil.TypicalGroups.getTut1;
import static seedu.address.testutil.TypicalGroups.getTypicalGroupsWithPersons;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.group.exceptions.DuplicateGroupException;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.testutil.GroupBuilder;

public class UniqueGroupListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final UniqueGroupList uniqueGroupList = new UniqueGroupList();

    @Test
    public void contains_nullGroup_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueGroupList.contains((Group) null);
        uniqueGroupList.contains((AddGroup) null);
        uniqueGroupList.contains(null, null);
    }

    @Test
    public void contains_groupNotInList_returnsFalse() {

        assertFalse(uniqueGroupList.contains(getTut1()));
    }

    @Test
    public void contains_personNotInGroup_returnsFalse() {

        assertFalse(uniqueGroupList.contains(getAddGroup1()));
        assertFalse(uniqueGroupList.contains(getTut1(), getAddGroup1()));
    }

    @Test
    public void contains_groupInList_returnsTrue() {
        uniqueGroupList.createGroup(getTut1());
        assertTrue(uniqueGroupList.contains(getTut1()));
    }

    @Test
    public void contains_personInList_returnsTrue(){
        uniqueGroupList.createGroup(getTypicalGroupsWithPersons());
        assertTrue(uniqueGroupList.contains(getAddGroupWithGroupAndPerson()));
    }

    @Test
    public void contains_groupWithSameIdentityFieldsInList_returnsTrue() {
        uniqueGroupList.createGroup(getTypicalGroupsWithPersons());
        Group editedTut1 = new GroupBuilder(getTut1())
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
        uniqueGroupList.createGroup(getTut1());
        thrown.expect(DuplicateGroupException.class);
        uniqueGroupList.createGroup(getTut1());
    }

    @Test
    public void create_createGroup_addGroupToList(){
        uniqueGroupList.createGroup(getTut1());
        ObservableList<Group> expectedUniqueGroupList = FXCollections.observableArrayList();
        expectedUniqueGroupList.add(getTut1());
        assertEquals(expectedUniqueGroupList, uniqueGroupList.asUnmodifiableObservableList());
    }

    @Test
    public void addGroup_nullGroup_throwsNullPointerException(){
        thrown.expect(NullPointerException.class);
        uniqueGroupList.addGroup(null);
    }

    @Test
    public void addGroup_duplicatePersons_throwsDuplicatePersonException(){
        thrown.expect(DuplicatePersonException.class);
        uniqueGroupList.createGroup(getTypicalGroupsWithPersons());
        uniqueGroupList.addGroup(getAddGroupWithGroupAndPerson());
    }

    @Test
    public void addGroup_addPersonToGroup_addPersonToGroupInList(){
        uniqueGroupList.createGroup(getTut1());
        uniqueGroupList.addGroup(getAddGroupWithGroupAndPerson());
        ObservableList<Group> expectedUniqueGroupList = FXCollections.observableArrayList();
        expectedUniqueGroupList.add(getTypicalGroupsWithPersons());
        assertEquals(expectedUniqueGroupList, uniqueGroupList.asUnmodifiableObservableList());
    }

    @Test
    public void addPersons_nullParameter_throwsNullPointerException(){
        thrown.expect(NullPointerException.class);
        uniqueGroupList.addPersons(null);
    }

    @Test
    public void setGroups_nullList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueGroupList.setGroups((List<Group>) null);
    }

    @Test
    public void setGroups_list_replacesOwnListWithProvidedList() {
        uniqueGroupList.createGroup(getTut1());
        List<Group> groupList = Collections.singletonList(CS1010);
        uniqueGroupList.setGroups(groupList);
        UniqueGroupList expectedUniqueGroupList = new UniqueGroupList();
        expectedUniqueGroupList.createGroup(CS1010);
        assertEquals(expectedUniqueGroupList, uniqueGroupList);
    }

    @Test
    public void setGroups_listWithDuplicateGroups_throwsDuplicateGroupException() {
        List<Group> listWithDuplicateGroups = Arrays.asList(getTut1(), getTut1());
        thrown.expect(DuplicateGroupException.class);
        uniqueGroupList.setGroups(listWithDuplicateGroups);
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        uniqueGroupList.asUnmodifiableObservableList().remove(0);
    }

}
