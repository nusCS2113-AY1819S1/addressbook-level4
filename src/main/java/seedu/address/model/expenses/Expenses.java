package seedu.address.model.expenses;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.model.person.EmployeeId;

/**
 * Represents an Expenses of an employee in the expensesList.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Expenses {

    // Identity fields
    private final EmployeeId id;

    // Data fields
    private final ExpensesAmount expensesAmount;
    private final TravelExpenses travelExpenses;
    private final MedicalExpenses medicalExpenses;
    private final MiscellaneousExpenses miscellaneousExpenses;

    /**
     * Every field must be present and not null.
     */
    public Expenses(EmployeeId id, ExpensesAmount expensesAmount, TravelExpenses travelExpenses,
                    MedicalExpenses medicalExpenses, MiscellaneousExpenses miscellaneousExpenses) {
        requireAllNonNull(id, expensesAmount, travelExpenses, medicalExpenses, miscellaneousExpenses);
        this.id = id;
        this.expensesAmount = expensesAmount;
        this.travelExpenses = travelExpenses;
        this.medicalExpenses = medicalExpenses;
        this.miscellaneousExpenses = miscellaneousExpenses;
    }

    public EmployeeId getEmployeeId() {
        return id;
    }

    public ExpensesAmount getExpensesAmount() {
        return expensesAmount;
    }

    public TravelExpenses getTravelExpenses() {
        return travelExpenses;
    }

    public MedicalExpenses getMedicalExpenses() {
        return medicalExpenses;
    }

    public MiscellaneousExpenses getMiscellaneousExpenses() {
        return miscellaneousExpenses;
    }

    /**
     * Returns true if both persons of the same id have expenses that is the same.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSameExpensesRequest(Expenses otherExpenses) {
        if (otherExpenses == this) {
            return true;
        }

        return otherExpenses != null
                && otherExpenses.getEmployeeId().equals(getEmployeeId());
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Expenses)) {
            return false;
        }

        Expenses otherExpenses = (Expenses) other;
        return otherExpenses.getEmployeeId().equals(getEmployeeId());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(expensesAmount, travelExpenses, medicalExpenses, miscellaneousExpenses);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getEmployeeId())
                .append(" Expenses Amount: ")
                .append(getExpensesAmount())
                .append(" Travel Expenses: ")
                .append(getTravelExpenses())
                .append(" Medical Expenses: ")
                .append(getMedicalExpenses())
                .append(" Miscellaneous Expenses: ")
                .append(getMiscellaneousExpenses());
        return builder.toString();
    }

}
