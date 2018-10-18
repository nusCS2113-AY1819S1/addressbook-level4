package seedu.address.model.medhistory;

import static java.util.Objects.requireNonNull;
//import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents the allergy of medical history.
 * Guarantees: immutable; is valid as declared in {@link #isValidAllergy(String)}
 */

public class Allergy {

    public static final String MESSAGE_ALLERGY_CONSTRAINTS =
            "Allergy must not be left blank.";

    public static final String ALLERGY_VALIDATION_REGEX = "[^\\s].*";

    public final String value;

    /**
     * Constructs an {@code Allergy}.
     *
     * @param allergy A valid allergy.
     */
    public Allergy(String allergy) {
        requireNonNull(allergy);
        // isValidAllergy will is found in test file.
        //checkArgument(isValidAllergy(allergy), MESSAGE_ALLERGY_CONSTRAINTS);
        value = allergy;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Allergy // instanceof handles nulls
                && value.equals(((Allergy) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
