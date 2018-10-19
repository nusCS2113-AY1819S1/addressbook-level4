package seedu.address.model.statistic;

/**
 * Represents Revenue in the month's Statistic
 * Guarantees: immutable
 */
public class Revenue {
    private static final String QUANTITY_VALIDATION_REGEX = "[-+]?[0-9]*\\.?[0-9]+";
    private static volatile String value;

    /**
     * Constructs an {@code Revenue}.
     *
     * @param revenue
     */
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