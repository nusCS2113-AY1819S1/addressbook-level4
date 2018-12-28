package seedu.address.model.medhistory;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

//@@author xShaivan
/**
 * Represents the previous country visited of medical history.
 * Guarantees: immutable; is valid as declared in {@link #isValidPrevCountry(String)}
 */

public class PrevCountry {

    public static final String MESSAGE_PREVCOUNTRY_CONSTRAINTS =
            "Previous Country can only contain alphanumeric characters, whitespaces, commas and periods.";

    public static final String PREVCOUNTRY_VALIDATION_REGEX = "[\\p{Alnum}\\p{Space},.]*";

    public final String value;

    /**
     * Constructs an {@code PrevCountry}.
     *
     * @param prevCountry A valid prevCountry.
     */
    public PrevCountry(String prevCountry) {
        requireNonNull(prevCountry);
        checkArgument(isValidPrevCountry(prevCountry), MESSAGE_PREVCOUNTRY_CONSTRAINTS);
        value = prevCountry;
    }

    public static boolean isValidPrevCountry(String test) {
        return test.matches(PREVCOUNTRY_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PrevCountry // instanceof handles nulls
                && value.equals(((PrevCountry) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
