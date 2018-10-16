package seedu.address.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_GROUPS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.testutil.TypicalAddGroups.getAddGroupWithGroupAndPerson;
import static seedu.address.testutil.TypicalGroups.CS1010;
import static seedu.address.testutil.TypicalGroups.TUT_1;
import static seedu.address.testutil.TypicalGroups.getTut1;
import static seedu.address.testutil.TypicalGroups.getTypicalGroupsWithPersons;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;

import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.group.GroupNameContainsKeywordsPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.testutil.AddressBookBuilder;

public class ModelManagerTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private ModelManager modelManager = new ModelManager();

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.hasPerson(null);
    }

    @Test
    public void hasPerson_personNotInAddressBook_returnsFalse() {
        assertFalse(modelManager.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personInAddressBook_returnsTrue() {
        modelManager.addPerson(ALICE);
        assertTrue(modelManager.hasPerson(ALICE));
    }

    @Test
    public void getFilteredPersonList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        modelManager.getFilteredPersonList().remove(0);
    }

    @Test
    public void hasGroup_nullGroup_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.hasGroup(null);
    }

    @Test
    public void hasGroup_groupNotInAddressBook_returnsFalse() {
        assertFalse(modelManager.hasGroup(getTut1()));
    }

    @Test
    public void hasGroup_groupInAddressBook_returnsTrue() {
        modelManager.createGroup(getTut1());
        assertTrue(modelManager.hasGroup(getTut1()));
    }

    @Test
    public void hasPersonInGroup_null_throwsNullPointerException(){
        thrown.expect(NullPointerException.class);
        modelManager.hasPersonInGroup(null);
    }

    @Test
    public void hasPersonInGroup_personIsAlreadyInGroup_returnTrue(){
        modelManager.createGroup(getTypicalGroupsWithPersons());
        assertTrue(modelManager.hasPersonInGroup(getAddGroupWithGroupAndPerson()));
    }

    @Test
    public void hasPersonInGroup_personIsNotAlreadyInGroup_returnFalse(){
        modelManager.createGroup(getTut1());
        assertFalse(modelManager.hasPersonInGroup(getAddGroupWithGroupAndPerson()));
    }

    @Test
    public void addGroup_personAlreadyInGroup_throwsDuplicatePersonsException(){
        modelManager.createGroup(getTypicalGroupsWithPersons());
        thrown.expect(DuplicatePersonException.class);
        modelManager.addGroup(getAddGroupWithGroupAndPerson());
    }

    @Test
    public void addGroup_personNotInGroup_addPersonToGroup(){
        modelManager.createGroup(getTut1());
        modelManager.addGroup(getAddGroupWithGroupAndPerson());
    }

    @Test
    public void getFilteredGroupList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        modelManager.getFilteredGroupList().remove(0);
    }

    @Test
    public void equals() {
        AddressBook addressBook = new AddressBookBuilder().withPerson(ALICE)
                .withPerson(BENSON).withGroup(getTut1())
                .withGroup(CS1010).build();
        AddressBook differentAddressBook = new AddressBook();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(addressBook, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(addressBook, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different addressBook -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentAddressBook, userPrefs)));

        // different filteredList -> returns false
        String[] keywords = ALICE.getName().fullName.split("\\s+");
        modelManager.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(addressBook, userPrefs)));

        String[] keywordsGroup = TUT_1.getGroupName().groupName.split("\\s+");
        modelManager.updateFilteredGroupList(new GroupNameContainsKeywordsPredicate(Arrays.asList(keywordsGroup)));
        assertFalse(modelManager.equals(new ModelManager(addressBook, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        modelManager.updateFilteredGroupList(PREDICATE_SHOW_ALL_GROUPS);

        // different userPrefs -> returns true
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setAddressBookFilePath(Paths.get("differentFilePath"));
        assertTrue(modelManager.equals(new ModelManager(addressBook, differentUserPrefs)));
    }
}
