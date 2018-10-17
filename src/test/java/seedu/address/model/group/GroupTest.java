package seedu.address.model.group;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GROUP_LOCATION_CS1010;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GROUP_NAME_CS1010;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GROUP_TAG_CS1010;
import static seedu.address.testutil.TypicalGroups.CS1010;
import static seedu.address.testutil.TypicalGroups.getTut1;
import static seedu.address.testutil.TypicalGroups.getTypicalGroupsWithPersons;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.AMY;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.person.Person;
import seedu.address.testutil.GroupBuilder;

public class GroupTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Group group = getTut1();

        thrown.expect(UnsupportedOperationException.class);
        group.getTags().remove(0);

        thrown.expect(UnsupportedOperationException.class);
        group.getPersons().remove(0);
    }

    @Test
    public void constructor_nullParameters_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        Group group = new Group(null, null, null);
    }

    @Test
    public void addPersons_addPersonSetWithPerson_personSetModified() {
        Group expected = getTypicalGroupsWithPersons();

        Group actual = getTut1();
        actual.addPersons(ALICE);

        assertEquals(expected, actual);
    }

    @Test
    public void addPersons_addPersonSetWithPersonSet_personSetModified() {
        Group expected = getTypicalGroupsWithPersons();
        expected.addPersons(AMY);

        Group actual = getTut1();
        Set<Person> personSet = new HashSet<>(Arrays.asList(ALICE, AMY));
        actual.addPersons(personSet);

        assertEquals(expected, actual);
    }

    @Test
    public void isSameGroup() {

        Group group = getTut1();

        // same object -> returns true
        assertTrue(group.isSameGroup(group));

        // null -> returns false
        assertFalse(group.isSameGroup(null));

        // different group location -> returns false
        Group editedGroup = new GroupBuilder(group).withGroupLocation(VALID_GROUP_LOCATION_CS1010).build();
        assertFalse(group.isSameGroup(editedGroup));

        // different group name -> returns false
        editedGroup = new GroupBuilder(group).withGroupName(VALID_GROUP_NAME_CS1010).build();
        assertFalse(group.isSameGroup(editedGroup));

        // same group name, same location, different tags -> returns false
        editedGroup = new GroupBuilder(group).withTags(VALID_GROUP_TAG_CS1010).build();
        assertFalse(group.isSameGroup(editedGroup));
    }

    @Test
    public void equals() {

        Group group = getTut1();

        // same values -> returns true
        Group groupCopy = new GroupBuilder(group).build();
        assertTrue(group.equals(groupCopy));

        // same object -> returns true
        assertTrue(group.equals(group));

        // null -> returns false
        assertFalse(group.equals(null));

        // different type -> returns false
        assertFalse(group.equals(5));

        // different group -> returns false
        assertFalse(group.equals(CS1010));

        // different group name -> returns false
        Group editedGroup = new GroupBuilder(group).withGroupName(VALID_GROUP_NAME_CS1010).build();
        assertFalse(group.equals(editedGroup));

        // different group location -> returns false
        editedGroup = new GroupBuilder(group).withGroupLocation(VALID_GROUP_LOCATION_CS1010).build();
        assertFalse(group.equals(editedGroup));

        // different tags -> returns false
        editedGroup = new GroupBuilder(group).withTags(VALID_GROUP_TAG_CS1010).build();
        assertFalse(group.equals(editedGroup));
    }

    @Test
    public void toStringTest() {

        Group group = getTut1();

        final StringBuilder builder = new StringBuilder();
        builder.append(group.getGroupName())
                .append(" at location ")
                .append(group.getGroupLocation())
                .append(" with tags: ");
        group.getTags().forEach(builder::append);

        final String expected = builder.toString();
        final String actual = group.toString();
        final String invalid = "[6]";

        // same string (=)
        assertEquals(expected, actual);

        // different string (!=)
        assertNotEquals(expected, invalid);
    }

}
