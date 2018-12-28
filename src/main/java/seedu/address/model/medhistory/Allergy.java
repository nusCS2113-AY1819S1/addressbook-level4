package seedu.address.model.medhistory;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

//@@author xShaivan
/**
 * Represents the allergy of medical history.
 * Guarantees: immutable; is valid as declared in {@link #isValidAllergy(String)}
 */

public class Allergy {

    public static final String MESSAGE_ALLERGY_CONSTRAINTS =
            "Allergy can only contain alphanumeric characters, whitespaces, commas and periods.";

    public static final String ALLERGY_VALIDATION_REGEX = "[\\p{Alnum}\\p{Space},.]*";

    public final String value;

    /**
     * Constructs an {@code Allergy}.
     *
     * @param allergy A valid allergy.
     */
    public Allergy(String allergy) {
        requireNonNull(allergy);
        checkArgument(isValidAllergy(allergy), MESSAGE_ALLERGY_CONSTRAINTS);
        value = allergy;
    }

    public static boolean isValidAllergy(String test) {
        return test.matches(ALLERGY_VALIDATION_REGEX);
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
