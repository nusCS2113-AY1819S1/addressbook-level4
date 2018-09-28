package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.TypicalItems.ALICE;
import static seedu.address.testutil.TypicalItems.getTypicalStockList;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.item.Item;
import seedu.address.model.item.exceptions.DuplicateItemException;
import seedu.address.testutil.ItemBuilder;

public class StockListTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final StockList stockList = new StockList();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), stockList.getItemList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        stockList.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyStockList_replacesData() {
        StockList newData = getTypicalStockList();
        stockList.resetData(newData);
        assertEquals(newData, stockList);
    }

    @Test
    public void resetData_withDuplicateItems_throwsDuplicateItemException() {
        // Two items with the same identity fields
        Item editedAlice = new ItemBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        List<Item> newItems = Arrays.asList(ALICE, editedAlice);
        StockListStub newData = new StockListStub(newItems);

        thrown.expect(DuplicateItemException.class);
        stockList.resetData(newData);
    }

    @Test
    public void hasItem_nullItem_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        stockList.hasItem(null);
    }

    @Test
    public void hasItem_itemNotInStockList_returnsFalse() {
        assertFalse(stockList.hasItem(ALICE));
    }

    @Test
    public void hasItem_itemInStockList_returnsTrue() {
        stockList.addItem(ALICE);
        assertTrue(stockList.hasItem(ALICE));
    }

    @Test
    public void hasItem_itemWithSameIdentityFieldsInStockList_returnsTrue() {
        stockList.addItem(ALICE);
        Item editedAlice = new ItemBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(stockList.hasItem(editedAlice));
    }

    @Test
    public void getItemList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        stockList.getItemList().remove(0);
    }

    /**
     * A stub ReadOnlyStockList whose items list can violate interface constraints.
     */
    private static class StockListStub implements ReadOnlyStockList {
        private final ObservableList<Item> items = FXCollections.observableArrayList();

        StockListStub(Collection<Item> items) {
            this.items.setAll(items);
        }

        @Override
        public ObservableList<Item> getItemList() {
            return items;
        }
    }

}
