package seedu.address.model.statistic;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import javafx.collections.ObservableList;
import seedu.address.model.ReadOnlyBookInventory;
import seedu.address.model.book.Book;

/**
 * Represents Revenue in the month's Statistic
 */
public class Inventory {
    public static final String MESSAGE_INVENTORY_CONSTRAINTS =
            "Inventory should be numerical and in 2 decimal places or none at all\n"
                    + "E.g. 4, 3.02";
    private static final String QUANTITY_VALIDATION_REGEX = "\\d+(\\.\\d{2})?";
    private volatile String value;

    /**
     * Constructor for Json Jackson
     */
    public Inventory() {
        super();
    }

    public Inventory(String inventory) {
        requireNonNull(inventory);
        checkArgument(isValidInventory(inventory), MESSAGE_INVENTORY_CONSTRAINTS);
        value = inventory;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public static boolean isValidInventory(String test) {
        return test.matches(QUANTITY_VALIDATION_REGEX);
    }


    /**
     * increase inventory
     * @param price
     * @param quantity
     */
    public void increase(String price, String quantity) {
        this.value = Float.toString(
                Float.parseFloat(value) + (Float.parseFloat(price) * Float.parseFloat(quantity)));
    }

    /**
     * decrease inventory
     * @param price
     * @param quantity
     */
    public void decrease(String price, String quantity) {
        this.value = Float.toString(
                Float.parseFloat(value) - (Float.parseFloat(price) * Float.parseFloat(quantity)));
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Inventory // instanceof handles nulls
                && value.equals(((Inventory) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    /**
     * Calibrates Inventory according to current inventory.
     * @param readOnlyBookInventory
     */
    public void calibrate(ReadOnlyBookInventory readOnlyBookInventory) {
        this.value = "0";
        ObservableList<Book> bookList = readOnlyBookInventory.getBookList();
        bookList.forEach((book) -> {
            this.increase(book.getCost().toString(), book.getQuantity().getValue());
        });
    }
}

