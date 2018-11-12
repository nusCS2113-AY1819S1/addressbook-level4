package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's Salary in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidSalary(String)}
 */
public class Salary {
    public static final String MESSAGE_SALARY_CONSTRAINTS =
            "Salary should only contain numbers, and it should not be blank. Only a maximum of 6 whole numbers and "
                    + "2 decimal place are allowed. (Max Salary store value is 999999.99)\n";
    public static final String SALARY_VALIDATION_REGEX = "[%]?[-]?[0-9]{1,6}([.][0-9]{1,2})?";
    public final String value;

    /**
     * Constructs a {@code salary}.
     *
     * @param salary A valid salary.
     */

    public Salary(String salary) {
        requireNonNull(salary);
        checkArgument(isValidSalary(salary), MESSAGE_SALARY_CONSTRAINTS);
        value = salary;
    }

    /**
     * Returns true if a given string is a valid salary.
     */
    public static boolean isValidSalary(String test) {
        return test.matches(SALARY_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Salary // instanceof handles nulls
                && value.equals(((Salary) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
