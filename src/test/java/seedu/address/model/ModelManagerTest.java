package seedu.address.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.testutil.AddressBookBuilder;

public class ModelManagerTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

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
    public void importPersonsFromAddressBook_nullImportFilePath_throwsNullPointerException()
            throws IOException, DataConversionException {
        thrown.expect(NullPointerException.class);
        modelManager.importPersonsFromAddressBook(null);
    }

    @Test
    public void addPersonsToAddressBook() {
        AddressBook addressBookwithMorePerson = new AddressBookBuilder().withPerson(ALICE).withPerson(BENSON).build();
        AddressBook addressBook = new AddressBook();
        UserPrefs userPrefs = new UserPrefs();

        ModelManager expectedModel = new ModelManager(addressBook, userPrefs);
        expectedModel.addPerson(ALICE);
        expectedModel.addPerson(BENSON);

        modelManager = new ModelManager(addressBook, userPrefs);
        modelManager.addPersonsToAddressBook(addressBookwithMorePerson);

        assertTrue(modelManager.equals(expectedModel));
    }

    @Test
    public void exportFilteredAddressBook_nullExportFilePath_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        modelManager.exportFilteredAddressBook(null);
    }

    @Test
    public void exportFilteredAddressBook_emptyAddressBook_throwsIllegalValueException() throws Exception {
        ModelManager modelManager = new ModelManager(new AddressBook(), new UserPrefs());
        modelManager.resetData(new AddressBook());

        thrown.expect(IllegalValueException.class);
        modelManager.exportFilteredAddressBook(Paths.get("notGonnaExport.xml"));
    }

    @Test
    public void equals() {
        AddressBook addressBook = new AddressBookBuilder().withPerson(ALICE).withPerson(BENSON).build();
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

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        // different userPrefs -> returns true
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setAddressBookFilePath(Paths.get("differentFilePath"));
        assertTrue(modelManager.equals(new ModelManager(addressBook, differentUserPrefs)));
    }
}
