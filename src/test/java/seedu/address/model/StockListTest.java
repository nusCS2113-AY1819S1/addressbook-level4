package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalItems.ARDUINO;
import static seedu.address.testutil.TypicalItems.getTypicalStockList;

import java.util.Collection;
import java.util.Collections;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.item.Item;

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

    /*@Test
    public void resetData_withDuplicateItems_throwsDuplicateItemException() {
        // Two items with the same identity fields
        Item editedARDUINO = new ItemBuilder(ARDUINO).withAddress(VALID_QUANTITY_LED_YELLOW).withTags(VALID_TAG_LAB2)
                .build();
        List<Item> newItems = Arrays.asList(ARDUINO, editedARDUINO);
        StockListStub newData = new StockListStub(newItems);

        thrown.expect(DuplicateItemException.class);
        stockList.resetData(newData);
    }*/
    //Two items can have same quantities here

    @Test
    public void hasItem_nullItem_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        stockList.hasItem(null);
    }

    @Test
    public void hasItem_itemNotInStockList_returnsFalse() {
        assertFalse(stockList.hasItem(ARDUINO));
    }

    @Test
    public void hasItem_itemInStockList_returnsTrue() {
        stockList.addItem(ARDUINO);
        assertTrue(stockList.hasItem(ARDUINO));
    }
    /*
    @Test
    public void hasItem_itemWithSameIdentityFieldsInStockList_returnsTrue() {
        stockList.addItem(ARDUINO);
        Item editedARDUINO = new ItemBuilder(ARDUINO).withQuantity(VALID_QUANTITY_LED_YELLOW).withTags(VALID_TAG_LAB2)
                .build();
        assertTrue(stockList.hasItem(editedARDUINO));
    }
    */
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
