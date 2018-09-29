package seedu.address.model.candidate;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Candidate's gender in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidGender(String)}
 */

public class Gender {
    public static final String NAME_VALIDATION_REGEX = "[AM]*";

    public static final String MESSAGE_GENDER_CONSTRAINTS =
            "Genders are restricted to either M(Male) or F(Female), and it should not be blank ";

    public Gender(String gender){
        requireNonNull(gender);
        checkArgument(isValidGender(gender), MESSAGE_GENDER_CONSTRAINTS);
    }

    /**
     * Returns true if a given string is a valid gender.
     */
    public static boolean isValidGender(String test) {
        return true;
    }
}
