package seedu.address.model.statistic;


/**
 * Represents Revenue in the month's Statistic
 */
public class Revenue {
    private static final String QUANTITY_VALIDATION_REGEX = "[-+]?[0-9]*\\.?[0-9]+";
    private volatile String value;

    /**
     * Constructor for Json Jackson
     */
    public Revenue () {
        super();
    }

    public Revenue(String revenue) {
        value = revenue;
    }

    public static boolean isValid(String test) {
        return test.matches(QUANTITY_VALIDATION_REGEX);
    }

    /**
     * Increase revenue
     * @param price
     * @param quantity
     */
    public void increase(String price, String quantity) {
        this.value = Float.toString(
                Float.parseFloat(value) + (Float.parseFloat(price) * Float.parseFloat(quantity)));
    }

    /**
     * decrease revenue
     * @param price
     * @param quantity
     */
    public void decrease(String price, String quantity) {
        this.value = Float.toString(
                Float.parseFloat(value) - (Float.parseFloat(price) * Float.parseFloat(quantity)));
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Revenue // instanceof handles nulls
                && value.equals(((Revenue) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
