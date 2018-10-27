package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a person's matriculation number.
 * Guarantees: immutable; is valid as declared in {@link #isValidMatricNo(String)}
 */
public class MatricNo {

    public static final String MESSAGE_MATRIC_NO_CONSTRAINTS =
            "Matriculation numbers are required to start with"
                    + " A, followed by a combination of 7 numbers"
                    + " and end with a checksum letter, and it should not be blank";

    public static final String MATRIC_NO_VALIDATION_REGEX = "^[A]{1}\\d{7}[A-Z]{1}$";

    public final String matricNo;

    /**
     * Constructs a {@code MatricNo}.
     *
     * @param matricNo A valid matric number.
     */
    public MatricNo(String matricNo) {
        requireNonNull(matricNo);
        checkArgument(isValidMatricNo(matricNo), MATRIC_NO_VALIDATION_REGEX);
        this.matricNo = matricNo;
    }

    /**
     * Returns true if a given string is a valid matric number.
     */
    public static boolean isValidMatricNo(String test) {
        return test.matches(MATRIC_NO_VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return matricNo;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MatricNo // instanceof handles nulls
                && matricNo.equals(((MatricNo) other).matricNo)); // state check
    }

    @Override
    public int hashCode() {
        return matricNo.hashCode();
    }

}
