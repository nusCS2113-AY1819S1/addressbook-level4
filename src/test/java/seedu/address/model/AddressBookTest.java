package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GROUP_LOCATION_TUT_1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GROUP_TAG_TUT_1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.TypicalAddGroups.getAddGroupWithGroupAndPerson;
import static seedu.address.testutil.TypicalGroups.TUT_1;
import static seedu.address.testutil.TypicalGroups.getTut1;
import static seedu.address.testutil.TypicalGroups.getTypicalGroupsWithPersons;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.group.Group;
import seedu.address.model.group.exceptions.DuplicateGroupException;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.testutil.GroupBuilder;
import seedu.address.testutil.PersonBuilder;

public class AddressBookTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final AddressBook addressBook = new AddressBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), addressBook.getPersonList());
        assertEquals(Collections.emptyList(), addressBook.getGroupList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        addressBook.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        AddressBook newData = getTypicalAddressBook();
        addressBook.resetData(newData);
        assertEquals(newData, addressBook);
    }

    @Test
    public void resetData_withDuplicatePersons_throwsDuplicatePersonException() {
        // Two persons with the same identity fields
        Person editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        List<Person> newPersons = Arrays.asList(ALICE, editedAlice);
        List<Group> newGroups = Arrays.asList(TUT_1);
        AddressBookStub newData = new AddressBookStub(newPersons, newGroups);

        thrown.expect(DuplicatePersonException.class);
        addressBook.resetData(newData);
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        addressBook.hasPerson(null);
    }

    @Test
    public void hasPerson_personNotInAddressBook_returnsFalse() {
        assertFalse(addressBook.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personInAddressBook_returnsTrue() {
        addressBook.addPerson(ALICE);
        assertTrue(addressBook.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personWithSameIdentityFieldsInAddressBook_returnsTrue() {
        addressBook.addPerson(ALICE);
        Person editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(addressBook.hasPerson(editedAlice));
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        addressBook.getPersonList().remove(0);
    }

    @Test
    public void resetData_withDuplicateGroups_throwsDuplicateGroupException() {
        // Two groups with the same identity fields
        Group editedTut1 = new GroupBuilder(TUT_1).withGroupLocation(VALID_GROUP_LOCATION_TUT_1)
                .withTags(VALID_GROUP_TAG_TUT_1)
                .build();
        List<Person> newPersons = Arrays.asList(ALICE);
        List<Group> newGroups = Arrays.asList(TUT_1, editedTut1);
        AddressBookStub newData = new AddressBookStub(newPersons, newGroups);

        thrown.expect(DuplicateGroupException.class);
        addressBook.resetData(newData);
    }

    @Test
    public void hasGroup_nullGroup_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        addressBook.hasGroup(null);
    }

    @Test
    public void hasGroup_groupNotInAddressBook_returnsFalse() {
        assertFalse(addressBook.hasGroup(TUT_1));
    }

    @Test
    public void hasGroup_groupInAddressBook_returnsTrue() {
        addressBook.createGroup(TUT_1);
        assertTrue(addressBook.hasGroup(TUT_1));
    }

    @Test
    public void hasGroup_groupWithSameIdentityFieldsInAddressBook_returnsTrue() {
        addressBook.createGroup(TUT_1);
        Group editedTut1 = new GroupBuilder(TUT_1)
                .withGroupLocation(VALID_GROUP_LOCATION_TUT_1)
                .withTags(VALID_GROUP_TAG_TUT_1)
                .build();
        assertTrue(addressBook.hasGroup(editedTut1));
    }

    @Test
    public void hasPersonInGroup_null_throwsNullPointerException(){
        addressBook.createGroup(getTypicalGroupsWithPersons());
        thrown.expect(NullPointerException.class);
        assertTrue(addressBook.hasPersonInGroup(null));
    }

    @Test
    public void hasPersonInGroup_groupAlreadyHasPerson_returnsTrue(){
        addressBook.createGroup(getTypicalGroupsWithPersons());
        assertTrue(addressBook.hasPersonInGroup(getAddGroupWithGroupAndPerson()));
    }

    @Test
    public void hasPersonInGroup_groupDoesNotHavePerson_returnsFalse(){
        addressBook.createGroup(getTut1());
        assertFalse(addressBook.hasPersonInGroup(getAddGroupWithGroupAndPerson()));
    }

    @Test
    public void addGroup_null_throwsNullPointerException(){
        addressBook.createGroup(getTypicalGroupsWithPersons());
        thrown.expect(NullPointerException.class);
        addressBook.addGroup(null);
    }

    @Test
    public void addGroup_personAlreadyInGroup_throwsDuplicatePersonException(){
        addressBook.createGroup(getTypicalGroupsWithPersons());
        thrown.expect(DuplicatePersonException.class);
        addressBook.addGroup(getAddGroupWithGroupAndPerson());
    }

    @Test
    public void addGroup_personNotAlreadyInGroup_addsPersonToGroup(){
        addressBook.createGroup(getTut1());
        addressBook.addGroup(getAddGroupWithGroupAndPerson());
    }

    @Test
    public void getGroupList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        addressBook.getGroupList().remove(0);
    }

    /**
     * A stub ReadOnlyAddressBook whose persons list can violate interface constraints.
     */
    private static class AddressBookStub implements ReadOnlyAddressBook {
        private final ObservableList<Person> persons = FXCollections.observableArrayList();
        private final ObservableList<Group> groups = FXCollections.observableArrayList();

        AddressBookStub(Collection<Person> persons, Collection<Group> groups) {
            this.persons.setAll(persons);
            this.groups.setAll(groups);
        }

        @Override
        public ObservableList<Person> getPersonList() {
            return persons;
        }

        @Override
        public ObservableList<Group> getGroupList() {
            return groups;
        }

    }

}
