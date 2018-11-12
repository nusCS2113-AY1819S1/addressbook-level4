package seedu.address.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_LEAVES;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.testutil.TypicalLeave.REQUEST_1;
import static seedu.address.testutil.TypicalLeave.REQUEST_2;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;

import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.leave.NricContainsKeywordsPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.testutil.AddressBookBuilder;
import seedu.address.testutil.LeaveListBuilder;

public class ModelManagerTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private ModelManager modelManager = new ModelManager();
    private ModelManager modelManager2 = new ModelManager();

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.hasPerson(null);
    }

    @Test
    public void hasLeave_nullLeave_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.hasLeave(null);
    }

    @Test
    public void hasPerson_personNotInAddressBook_returnsFalse() {
        assertFalse(modelManager.hasPerson(ALICE));
    }

    @Test
    public void hasLeave_leaveNotInLeaveList_returnsFalse() {
        assertFalse(modelManager.hasLeave(REQUEST_1));
    }

    @Test
    public void hasPerson_personInAddressBook_returnsTrue() {
        modelManager.addPerson(ALICE);
        assertTrue(modelManager.hasPerson(ALICE));
    }

    @Test
    public void hasLeave_leaveInLeaveList_returnsTrue() {
        modelManager.addLeave(REQUEST_1);
        assertTrue(modelManager.hasLeave(REQUEST_1));
    }

    @Test
    public void getFilteredPersonList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        modelManager.getFilteredPersonList().remove(0);
    }

    @Test
    public void getFilteredLeaveList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        modelManager.getFilteredLeaveList().remove(0);
    }

    @Test
    public void equals() {
        AddressBook addressBook = new AddressBookBuilder().withPerson(ALICE).withPerson(BENSON).build();
        AddressBook differentAddressBook = new AddressBook();
        LeaveList leaveList = new LeaveListBuilder().withLeave(REQUEST_1).withLeave(REQUEST_2).build();
        LeaveList differentLeaveList = new LeaveList();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(addressBook, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(addressBook, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        modelManager2 = new ModelManager(leaveList, userPrefs);
        ModelManager modelManagerCopy2 = new ModelManager(leaveList, userPrefs);
        assertTrue(modelManager2.equals(modelManagerCopy2));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));
        assertTrue(modelManager2.equals(modelManager2));

        // null -> returns false
        assertFalse(modelManager.equals(null));
        assertFalse(modelManager2.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));
        assertFalse(modelManager2.equals(5));


        // different addressBook -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentAddressBook, userPrefs)));
        assertFalse(modelManager.equals(new ModelManager(differentLeaveList, userPrefs)));

        // different filteredList -> returns false
        String[] keywords = ALICE.getName().fullName.split("\\s+");
        modelManager.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(addressBook, userPrefs)));

        String[] keywords2 = REQUEST_1.getEmployeeId().nric.split("\\s+");
        modelManager.updateFilteredLeaveList(new NricContainsKeywordsPredicate(Arrays.asList(keywords2)));
        assertFalse(modelManager.equals(new ModelManager(leaveList, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        modelManager.updateFilteredLeaveList(PREDICATE_SHOW_ALL_LEAVES);

        // different userPrefs -> returns true
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setAddressBookFilePath(Paths.get("differentFilePath"));
        assertTrue(modelManager.equals(new ModelManager(addressBook, differentUserPrefs)));

        differentUserPrefs.setLeaveListFilePath(Paths.get("differentFilePath"));
        assertTrue(modelManager.equals(new ModelManager(addressBook, differentUserPrefs)));
    }
}
