package seedu.address.model.candidate;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Candidate's gender in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidGender(String)}
 */

public class Gender {
    public static final String GENDER_VALIDATION_REGEX = "[FM]*";

    public static final String MESSAGE_GENDER_CONSTRAINTS =
            "Genders are restricted to either M(Male) or F(Female), and it should not be blank ";

    public final String gender;

    public Gender(String genderInput) {
        requireNonNull(genderInput);
        checkArgument(isValidGender(genderInput), MESSAGE_GENDER_CONSTRAINTS);
        gender = genderInput;
    }

    /**
     * Returns true if a given string is a valid gender.
     */
    public static boolean isValidGender(String test) {
        return test.matches(GENDER_VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Gender // instanceof handles nulls
                && gender.equals(((Gender) other).gender)); // state check
    }

    @Override
    public int hashCode() {
        return gender.hashCode();
    }
}
