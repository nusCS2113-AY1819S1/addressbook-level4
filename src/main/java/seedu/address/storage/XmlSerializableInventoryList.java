package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.InventoryList;
import seedu.address.model.ReadOnlyInventoryList;
import seedu.address.model.drink.Drink;

/**
 * An Immutable Inventory List that is serializable to XML format
 */
@XmlRootElement(name = "inventorylist")
public class XmlSerializableInventoryList {
    public static final String MESSAGE_DUPLICATE_DRINK = "Drinks list contains duplicate drink(s).";

    @XmlElement
    private List<XmlAdaptedDrink> drinks;

    /**
     * Creates an empty XmlSerializableAddressBook.
     * This empty constructor is required for marshalling.
     */
    public XmlSerializableInventoryList() {
        drinks = new ArrayList<>();
    }

    /**
     * Conversion
     */
    public XmlSerializableInventoryList(ReadOnlyInventoryList src) {
        this();
        drinks.addAll(src.getDrinkList().stream().map(XmlAdaptedDrink::new).collect(Collectors.toList()));
    }

    /**
     * Converts this inventorylist into the model's {@code InventoryList} object.
     *
     * @throws IllegalValueException if there were any data constraints violated or duplicates in the
     * {@code XmlAdaptedDrink}.
     */
    public InventoryList toModelType() throws IllegalValueException {
        InventoryList inventoryList = new InventoryList();
        for (XmlAdaptedDrink d : drinks) {
            Drink drink = d.toModelType();
            if (inventoryList.hasDrink(drink)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_DRINK);
            }
            inventoryList.addDrink(drink);
        }
        return inventoryList;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlSerializableInventoryList)) {
            return false;
        }
        return drinks.equals(((XmlSerializableInventoryList) other).drinks);
    }
}
