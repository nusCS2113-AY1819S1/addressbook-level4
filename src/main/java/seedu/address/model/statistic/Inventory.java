package seedu.address.model.statistic;

import javafx.collections.ObservableList;
import seedu.address.model.ReadOnlyBookInventory;
import seedu.address.model.book.Book;

/**
 * Represents Revenue in the month's Statistic
 */
public class Inventory {
    private static final String QUANTITY_VALIDATION_REGEX = "[-+]?[0-9]*\\.?[0-9]+";
    private volatile String value;

    /**
     * Constructor for Json Jackson
     */
    public Inventory() {
        super();
    }

    public Inventory(String revenue) {
        value = revenue;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public static boolean isValid(String test) {
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

