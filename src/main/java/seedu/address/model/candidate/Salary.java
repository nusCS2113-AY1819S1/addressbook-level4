package seedu.address.model.candidate;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Candidate's desired salary in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidSalary(String)}
 */

public class Salary {
    public static final String SALARY_VALIDATION_REGEX = "[\\d]+";

    public static final String MESSAGE_SALARY_CONSTRAINTS =
            "Desired salary should contain digits only and it should not be blank ";

    public final String salary;

    public Salary(String salaryInput) {
        requireNonNull(salaryInput);
        checkArgument(isValidSalary(salaryInput), MESSAGE_SALARY_CONSTRAINTS);
        salary = salaryInput;
    }

    /**
     * Returns true if a given string is a valid salary.
     */
    public static boolean isValidSalary(String test) {
        return test.matches(SALARY_VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Salary // instanceof handles nulls
                && salary.equals(((Salary) other).salary)); // state check
    }

    @Override
    public int hashCode() {
        return salary.hashCode();
    }

}
