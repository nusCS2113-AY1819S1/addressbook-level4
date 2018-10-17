package seedu.address.model.group;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalAddGroups.getAddGroup1;
import static seedu.address.testutil.TypicalAddGroups.getAddGroup2;
import static seedu.address.testutil.TypicalAddGroups.getAddGroup3;
import static seedu.address.testutil.TypicalAddGroups.getAddGroupWithGroupAndPerson;
import static seedu.address.testutil.TypicalGroups.getTut1;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_GROUP;
import static seedu.address.testutil.TypicalIndexes.getSingleTypicalPersonIndicesSet;
import static seedu.address.testutil.TypicalIndexes.getTypicalPersonIndicesSet;
import static seedu.address.testutil.TypicalPersons.ALICE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.core.index.Index;
import seedu.address.model.person.Person;
import seedu.address.testutil.Assert;

public class AddGroupTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new AddGroup(null, null));
    }

    @Test
    public void constructor_invalidIndex_throwsIndexOutOfBoundsException() {
        String invalidGroupIndex = "0";
        Assert.assertThrows(IndexOutOfBoundsException.class, () ->
                new AddGroup(Index.fromOneBased(Integer.parseInt(invalidGroupIndex)),
                getSingleTypicalPersonIndicesSet()));
    }

    @Test
    public void constructor_invalidIndex_throwsNumberFormatException() {
        String invalidGroupIndex = "e";
        Assert.assertThrows(NumberFormatException.class, () ->
                new AddGroup(Index.fromOneBased(Integer.parseInt(invalidGroupIndex)),
                getSingleTypicalPersonIndicesSet()));
    }

    @Test
    public void test_personSetIsSet_isSet() {
        AddGroup actual = getAddGroup3();
        List<Person> personList = new ArrayList<>(Arrays.asList(ALICE));
        actual.setPersonSet(personList);
        assertEquals(getAddGroupWithGroupAndPerson().getPersonSet(), actual.getPersonSet());
    }

    @Test
    public void test_groupSetIsSet_isSet() {
        AddGroup actual = getAddGroup3();
        List<Group> groupList = new ArrayList<>(Arrays.asList(getTut1()));
        actual.setGroupSet(groupList);
        assertEquals(getAddGroupWithGroupAndPerson().getGroup(), actual.getGroup());
    }

    @Test
    public void test_validPersonIndexSet_returnsTrue() {

        assertTrue(getAddGroup3().validPersonIndexSet(1));
    }

    @Test
    public void test_invalidPersonIndexSet_returnsFalse() {

        assertFalse(getAddGroup3().validPersonIndexSet(0));
    }

    @Test
    public void test_validGroupIndex_returnsTrue() {

        assertTrue(getAddGroup3().validGroupIndex(1));
    }

    @Test
    public void test_invalidGroupIndex_returnsFalse() {

        assertFalse(getAddGroup3().validGroupIndex(0));
    }

    @Test
    public void equals() {
        // same values -> returns true
        AddGroup addGroup = getAddGroup1();
        AddGroup addGroupCopy = new AddGroup(INDEX_FIRST_GROUP, getTypicalPersonIndicesSet());

        assertTrue(addGroup.equals(addGroupCopy));

        // same object -> returns true
        assertTrue(addGroup.equals(addGroup));

        // null -> returns false
        assertFalse(addGroup.equals(null));

        // different type -> returns false
        assertFalse(addGroup.equals(5));

        // different addGroup -> returns false
        assertFalse(addGroup.equals(getAddGroup3()));

        // different group index -> returns false
        assertFalse(addGroup.equals(getAddGroup2()));

        // different person indices -> returns false
        assertFalse(addGroup.equals(getAddGroup3()));
    }

    @Test
    public void toStringTest() {
        final StringBuilder builder = new StringBuilder();
        builder.append(INDEX_FIRST_GROUP.toString())
                .append(" : ")
                .append(INDEX_FIRST_GROUP.toString());

        String expected = builder.toString();
        String actual = getAddGroup3().toString();

        assertEquals(expected, actual);
    }

}
