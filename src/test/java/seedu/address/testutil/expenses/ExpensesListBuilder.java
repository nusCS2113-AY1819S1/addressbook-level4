package seedu.address.testutil.expenses;

import seedu.address.model.expenses.Expenses;
import seedu.address.model.expenses.ExpensesList;

/**
 * A utility class to help with building ExpensesList objects.
 * Example usage: <br>
 *     {@code ExpensesList el = new ExpensesListBuilder().withExpenses("John_claim", "Doe_claim").build();}
 */
public class ExpensesListBuilder {
    private ExpensesList expensesList;

    public ExpensesListBuilder() {
        expensesList = new ExpensesList();
    }

    public ExpensesListBuilder(ExpensesList expensesList) {
        this.expensesList = expensesList;
    }

    /**
     * Adds a new {@code expenses} to the {@code ExpensesList} that we are building.
     */
    public ExpensesListBuilder withExpenses(Expenses expenses) {
        expensesList.addExpenses(expenses);
        return this;
    }

    public ExpensesList build() {
        return expensesList;
    }
}
