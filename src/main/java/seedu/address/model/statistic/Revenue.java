package seedu.address.model.statistic;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

/**
 * Represents Revenue in the month's Statistic
 * Guarantees: immutable
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Revenue {
    private static final String QUANTITY_VALIDATION_REGEX = "[-+]?[0-9]*\\.?[0-9]+";
    private volatile String value;

    /**
     * Constructs an {@code Revenue}.
     *
     * @param revenue
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

    public void increase(float earnedRevenue) {
        this.value = Float.toString(Float.parseFloat(value) + earnedRevenue);
    }

    public void decrease(float amount) {
        value = Float.toString(Float.parseFloat(value) - amount);
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
