package seedu.address.model.statistic;


/**
 * Represents Inventory in the month's Statistic
 */
public class Expense {
    private static final String QUANTITY_VALIDATION_REGEX = "[-+]?[0-9]*\\.?[0-9]+";
    private volatile String value;

    /**
     * Constructor for Json Jackson
     */
    public Expense() {
        super();
    }

    public Expense(String revenue) {
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
     * Increase expense
     * @param price
     * @param quantity
     */
    public void increase(String price, String quantity) {
        this.value = Float.toString(
                Float.parseFloat(value) + (Float.parseFloat(price) * Float.parseFloat(quantity)));
    }

    /**
     * Decrease expense
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
                || (other instanceof Expense // instanceof handles nulls
                && value.equals(((Expense) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
