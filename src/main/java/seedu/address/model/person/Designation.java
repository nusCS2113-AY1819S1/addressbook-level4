package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

public class Designation {

    public static final String MESSAGE_DESIGNATION_CONSTRAINTS =
            "Designation can only be either 'manager' or 'employee'";
    public static final String DESIGNATION_VALIDATION_REGEX = "^[A-Za-z]+$";
    public final String value;
    //public static final String MANAGER_KEY = "Manager";
    //public static final String EMPLOYEE_KEY = "Employee";

    /**
     * Constructs a {@code Designation}.
     *
     * @param designation A valid designation.
     */
    public Designation(String designation) {
        requireNonNull(designation);
        checkArgument(isValidDesignation(designation), MESSAGE_DESIGNATION_CONSTRAINTS);
        value = designation;
    }

    /**
     * Returns true if a given string is a valid phone number.
     */
    public static boolean isValidDesignation(String test) {
        return test.matches(DESIGNATION_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Designation // instanceof handles nulls
                && value.equals(((Designation) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
