package seedu.address.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PRODUCTS;
import static seedu.address.testutil.TypicalDistributors.getTypicalDistributorBook;
import static seedu.address.testutil.TypicalProducts.APPLE;
import static seedu.address.testutil.TypicalProducts.GRAPE;
import static seedu.address.testutil.TypicalProducts.ORANGE;
import static seedu.address.testutil.TypicalProducts.getTypicalAddressBook;

import java.util.Arrays;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.product.NameContainsKeywordsPredicate;
import seedu.address.testutil.ProductDatabaseBuilder;

public class ModelManagerTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private ModelManager modelManager = new ModelManager(getTypicalAddressBook(), getTypicalDistributorBook(),
            new UserPrefs(), new UserDatabase(), new TestStorage());

    @Test
    public void hasProduct_nullProduct_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.hasProduct(null);
    }

    @Test
    public void hasProduct_personNotInProductDatabase_returnsFalse() {
        assertFalse(modelManager.hasProduct(APPLE));
    }

    @Test
    public void hasProduct_personInProductDatabase_returnsTrue() {
        modelManager.addProduct(APPLE);
        assertTrue(modelManager.hasProduct(APPLE));
    }

    @Test
    public void getFilteredProductList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        modelManager.getFilteredProductList().remove(0);
    }

    @Test
    public void equals() {
        ProductDatabase productDatabase = new ProductDatabaseBuilder().withProduct(GRAPE).withProduct(ORANGE).build();
        ProductDatabase differentProductDatabase = new ProductDatabase();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(productDatabase, getTypicalDistributorBook(),
                new UserPrefs(), new UserDatabase(), new TestStorage());
        ModelManager modelManagerCopy = new ModelManager(productDatabase, getTypicalDistributorBook(),
                new UserPrefs(), new UserDatabase(), new TestStorage());
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different productDatabase -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentProductDatabase, getTypicalDistributorBook(),
                new UserPrefs(), new UserDatabase(), new TestStorage())));

        // different filteredList -> returns false
        String[] keywords = GRAPE.getName().fullName.split("\\s+");
        modelManager.updateFilteredProductList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(productDatabase, getTypicalDistributorBook(),
                new UserPrefs(), new UserDatabase(), new TestStorage())));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredProductList(PREDICATE_SHOW_ALL_PRODUCTS);
    }
}
