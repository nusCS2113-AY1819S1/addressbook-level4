package seedu.address.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_ITEMS;
import static seedu.address.testutil.TypicalItems.ARDUINO;
import static seedu.address.testutil.TypicalItems.RPLIDAR;

import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.item.NameContainsKeywordsPredicate;
import seedu.address.testutil.StockListBuilder;

public class ModelManagerTest { @Rule
    public ExpectedException thrown = ExpectedException.none();

    private ModelManager modelManager = new ModelManager();

    @Test
    public void hasItem_nullItem_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.hasItem(null);
    }

    @Test
    public void hasItem_itemNotInStockList_returnsFalse() {
        assertFalse(modelManager.hasItem(ARDUINO));
    }

    @Test
    public void hasItem_itemInStockList_returnsTrue() {
        modelManager.addItem(ARDUINO);
        assertTrue(modelManager.hasItem(ARDUINO));
    }

    @Test
    public void getFilteredItemList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        modelManager.getFilteredItemList().remove(0);
    }

    @Test
    public void equals() {
        StockList stockList = new StockListBuilder().withItem(ARDUINO).withItem(RPLIDAR).build();
        StockList differentStockList = new StockList();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(stockList, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(stockList, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different stockList -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentStockList, userPrefs)));

        // different filteredList -> returns false
        String[] keywords = ARDUINO.getName().fullName.split("\\s+");
        modelManager.updateFilteredItemList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(stockList, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredItemList(PREDICATE_SHOW_ALL_ITEMS);

        // different userPrefs -> returns true
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setStockListFilePath(Paths.get("differentFilePath"));
        assertTrue(modelManager.equals(new ModelManager(stockList, differentUserPrefs)));
    }
}
