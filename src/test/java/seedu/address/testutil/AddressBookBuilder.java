package seedu.address.testutil;

import seedu.address.model.StockList;
import seedu.address.model.item.Item;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code StockList ab = new StockListBuilder().withItem("John", "Doe").build();}
 */
public class StockListBuilder {

    private StockList stockList;

    public StockListBuilder() {
        stockList = new StockList();
    }

    public StockListBuilder(StockList stockList) {
        this.stockList = stockList;
    }

    /**
     * Adds a new {@code Item} to the {@code StockList} that we are building.
     */
    public StockListBuilder withItem(Item item) {
        stockList.addItem(item);
        return this;
    }

    public StockList build() {
        return stockList;
    }
}
