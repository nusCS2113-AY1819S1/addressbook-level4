package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.StockList;
import seedu.address.model.ReadOnlyStockList;
import seedu.address.model.item.Item;

/**
 * An Immutable StockList that is serializable to XML format
 */
@XmlRootElement(name = "stocklist")
public class XmlSerializableStockList {

    public static final String MESSAGE_DUPLICATE_ITEM = "Items list contains duplicate item(s).";

    @XmlElement
    private List<XmlAdaptedItem> items;

    /**
     * Creates an empty XmlSerializableStockList.
     * This empty constructor is required for marshalling.
     */
    public XmlSerializableStockList() {
        items = new ArrayList<>();
    }

    /**
     * Conversion
     */
    public XmlSerializableStockList(ReadOnlyStockList src) {
        this();
        items.addAll(src.getItemList().stream().map(XmlAdaptedItem::new).collect(Collectors.toList()));
    }

    /**
     * Converts this stocklist into the model's {@code StockList} object.
     *
     * @throws IllegalValueException if there were any data constraints violated or duplicates in the
     * {@code XmlAdaptedItem}.
     */
    public StockList toModelType() throws IllegalValueException {
        StockList stockList = new StockList();
        for (XmlAdaptedItem p : items) {
            Item item = p.toModelType();
            if (stockList.hasItem(item)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_ITEM);
            }
            stockList.addItem(item);
        }
        return stockList;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlSerializableStockList)) {
            return false;
        }
        return items.equals(((XmlSerializableStockList) other).items);
    }
}
