package seedu.address.model.medhistory;

import static java.util.Objects.requireNonNull;
//import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents the previous country visited of medical history.
 * Guarantees: immutable; is valid as declared in {@link #isValidprevCountry(String)}
 */

public class PrevCountry {

    public static final String MESSAGE_PREVCOUNTRY_CONSTRAINTS =
            "prevCountry must not be left blank.";

    public static final String PREVCOUNTRY_VALIDATION_REGEX = "[^\\s].*";

    public final String value;

    /**
     * Constructs an {@code PrevCountry}.
     *
     * @param prevCountry A valid prevCountry.
     */
    public PrevCountry(String prevCountry) {
        requireNonNull(prevCountry);
        // isValidprevCountry will is found in test file.
        //checkArgument(isValidprevCountry(prevCountry), MESSAGE_PREVCOUNTRY_CONSTRAINTS);
        value = prevCountry;
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
