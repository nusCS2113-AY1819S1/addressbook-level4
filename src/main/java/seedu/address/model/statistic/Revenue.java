package seedu.address.model.statistic;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents Revenue in the month's Statistic
 */
public class Revenue {
    public static final String MESSAGE_REVENUE_CONSTRAINTS =
            "Revenue should be numerical and in 2 decimal places or none at all\n"
                    + "E.g. 4, 3.02";
    private static final String QUANTITY_VALIDATION_REGEX = "\\d+(\\.\\d{2})?";
    private volatile String value;


    /**
     * Constructor for Json Jackson
     */
    public Revenue () {
        super();
    }

    public Revenue(String revenue) {
        requireNonNull(revenue);
        checkArgument(isValidRevenue(revenue), MESSAGE_REVENUE_CONSTRAINTS);
        value = revenue;
    }



    public static boolean isValidRevenue(String test) {
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
