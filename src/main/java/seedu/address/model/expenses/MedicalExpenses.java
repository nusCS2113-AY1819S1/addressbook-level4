package seedu.address.model.expenses;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's Medical Expenses in the expenses list.
 * Guarantees: immutable; is valid as declared in {@link #isValidMedicalExpenses(String)}
 */
public class MedicalExpenses {

    public static final String MESSAGE_MEDICAL_EXPENSES_CONSTRAINTS =
            "Medical Expenses should only contain numbers, maximum of 6 whole numbers and 2 decimal points and "
                    + "minimum 1 digit long";
    public static final String EMPLOYEE_MEDICAL_EXPENSES_VALIDATION_REGEX = "[-]?[0-9]{1,6}+([.][0-9]{1,2})?";

    public final String medicalExpenses;

    /**
     * Constructs a {@code ExpensesAmount}.
     *
     * @param medicalExpenses A valid Expenses.
     */
    public MedicalExpenses(String medicalExpenses) {
        requireNonNull(medicalExpenses);
        checkArgument(isValidMedicalExpenses(medicalExpenses), MESSAGE_MEDICAL_EXPENSES_CONSTRAINTS);
        this.medicalExpenses = medicalExpenses;
    }

    /**
     * Returns true if a given string is a valid Expenses Amount.
     */
    public static boolean isValidMedicalExpenses(String test) {
        return test.matches(EMPLOYEE_MEDICAL_EXPENSES_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return medicalExpenses;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MedicalExpenses // instanceof handles nulls
                && medicalExpenses.equals(((MedicalExpenses) other).medicalExpenses)); // state check
    }

    @Override
    public int hashCode() {
        return medicalExpenses.hashCode();
    }

}
