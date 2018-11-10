package seedu.address.model.drink;

/**
 * Represents a NegativePrice, for use in financial analysis.
 * Note that NegativePrice value is limited to Float.MAX_VALUE
 */
public class NegativePrice extends Price {

    /**
     * Constructs a {@code NegativePrice}.
     *
     * @param input A valid Price. Validity of the input is as declared in {@link #isValidPrice(String)}
     */
    public NegativePrice(String input) {
        super(input);
    }

    @Override
    public float getValue() {
        return -1 * value;
    }

    @Override
    public String toString() {
        return String.format("-%.2f", value);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NegativePrice // instanceof handles nulls
                && (value == (((NegativePrice) other).value))); // state check
    }

    @Override
    public int hashCode() {
        return Float.hashCode(-1 * value);
    }

}
