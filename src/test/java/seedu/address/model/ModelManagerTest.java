package seedu.address.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_DISTRIBUTORS;
import static seedu.address.testutil.TypicalDistributors.AHBENG;
import static seedu.address.testutil.TypicalDistributors.AHHUAT;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;

import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.product.NameContainsKeywordsPredicate;
import seedu.address.model.distributor.DNameContainsKeywordsPredicate;
import seedu.address.testutil.AddressBookBuilder;
import seedu.address.testutil.DistributorBookBuilder;

public class ModelManagerTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private ModelManager modelManager = new ModelManager(new TestStorage());

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.hasPerson(null);
    }

    @Test
    public void hasDistributor_nullDistributor_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.hasDistributor(null);
    }

    @Test
    public void hasPerson_personNotInAddressBook_returnsFalse() {
        assertFalse(modelManager.hasPerson(ALICE));
    }

    @Test
    public void hasDistributor_distributorNotInAddressBook_returnsFalse() {
        assertFalse(modelManager.hasDistributor(AHBENG));
    }

    @Test
    public void hasPerson_personInAddressBook_returnsTrue() {
        modelManager.addPerson(ALICE);
        assertTrue(modelManager.hasPerson(ALICE));
    }

    @Test
    public void hasDistributor_distributorInDistributorBook_returnsTrue() {
        modelManager.addDistributor(AHBENG);
        assertTrue(modelManager.hasDistributor(AHBENG));
    }

    @Test
    public void getFilteredPersonList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        modelManager.getFilteredProductList().remove(0);
    }

    @Test
    public void equals() {
        ProductDatabase addressBook = new AddressBookBuilder().withPerson(ALICE).withPerson(BENSON).build();
        ProductDatabase differentAddressBook = new ProductDatabase();
        DistributorBook distributorBook = new DistributorBookBuilder().withPerson(AHBENG).withPerson(AHHUAT).build();
        DistributorBook differentDistributorBook = new DistributorBook();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(addressBook, distributorBook, userPrefs, new UserDatabase(), new TestStorage());
        ModelManager modelManagerCopy = new ModelManager(addressBook, distributorBook, userPrefs,
                new UserDatabase(), new TestStorage());
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different addressBook -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentAddressBook, distributorBook, userPrefs,
                new UserDatabase(), new TestStorage())));

        // different distributorBook -> returns false
        assertFalse(modelManager.equals(new ModelManager(addressBook, differentDistributorBook, userPrefs,
                new UserDatabase(), new TestStorage())));

        // different filteredProductsList -> returns false
        String[] keywords = ALICE.getName().fullName.split("\\s+");
        modelManager.updateFilteredProductList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(addressBook, distributorBook, userPrefs,
                new UserDatabase(), new TestStorage())));

        // different filteredDistributorsList -> returns false
        String[] distKeywords = AHBENG.getDistName().fullDistName.split("\\s+");
        modelManager.updateFilteredDistributorList(new DNameContainsKeywordsPredicate(Arrays.asList(distKeywords)));
        assertFalse(modelManager.equals(new ModelManager(addressBook, distributorBook, userPrefs,
                new UserDatabase(), new TestStorage())));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredProductList(PREDICATE_SHOW_ALL_PERSONS);

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredDistributorList(PREDICATE_SHOW_ALL_DISTRIBUTORS);

        // different userPrefs -> returns true
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setAddressBookFilePath(Paths.get("differentFilePath"));
        assertTrue(modelManager.equals(new ModelManager(addressBook, distributorBook, differentUserPrefs,
                new UserDatabase(), new TestStorage())));
    }
}
