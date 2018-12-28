package seedu.address.model.person.addinfo;

import static java.util.Objects.requireNonNull;

/**
 * Represents a person's NRIC in Health Book.
 */
public class Nric {
    public static final String MESSAGE_NRIC_CONSTRAINTS = "NRICs should adhere to the following constraints:\n"
            + "1. It should begin with either S or T;\n"
            + "2. The next 7 characters should be digits between 0 - 9, and;\n"
            + "3. It should end of with any alphabet.";
    public static final String MESSAGE_NRIC_INVALID = "The input NRIC is not correct. Please check again.";

    private static final String NRIC_VALIDATION_REGEX = "[ST]" + "[\\d]{7}" + "[A-Z]";
    private static final int CHECKSUM_ADDITIONAL_DIGIT = 4;
    private static final int CHECKSUM_MODULO_DIGIT = 11;

    public final String value;

    /**
     * Constructs an {@code Nric}.
     *
     * @param nric A valid nric.
     */
    public Nric(String nric) {
        requireNonNull(nric);
        value = nric;
    }

    /**
     * Returns if a given string is a valid NRIC.
     */
    public static boolean isValidNric(String test) {
        return test.matches(NRIC_VALIDATION_REGEX);
    }

    /**
     * Returns if a given string is a correct NRIC.
     */
    public static boolean isCorrectNric(String test) {
        final int[] weight = {2, 7, 6, 5, 4, 3, 2};

        char[] input = test.toCharArray();
        int sum = 0;

        for (int i = 0; i < weight.length; i++) {
            sum += weight[i] * Character.getNumericValue(input[i + 1]);
        }

        if (input[0] == 'T') {
            sum += CHECKSUM_ADDITIONAL_DIGIT;
        }

        switch (sum % CHECKSUM_MODULO_DIGIT) {
        case 10:
            return (input[input.length - 1] == 'A');
        case 9:
            return (input[input.length - 1] == 'B');
        case 8:
            return (input[input.length - 1] == 'C');
        case 7:
            return (input[input.length - 1] == 'D');
        case 6:
            return (input[input.length - 1] == 'E');
        case 5:
            return (input[input.length - 1] == 'F');
        case 4:
            return (input[input.length - 1] == 'G');
        case 3:
            return (input[input.length - 1] == 'H');
        case 2:
            return (input[input.length - 1] == 'I');
        case 1:
            return (input[input.length - 1] == 'Z');
        case 0:
            return (input[input.length - 1] == 'J');
        default:
            return false;
        }
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Nric // instanceof handles nulls
                && value.equals(((Nric) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
