package seedu.address.testutil;

import seedu.address.model.ExpenseBook;
import seedu.address.model.expense.Expense;

/**
 * A utility class to help with building ExpenseBook objects.
 * Example usage: <br>
 *     {@code ExpenseBook eb = new ExpenseBookBuilder().withExpense("John", "Doe").build();}
 */
public class ExpenseBookBuilder {

    private ExpenseBook expenseBook;

    public ExpenseBookBuilder() {
        expenseBook = new ExpenseBook();
    }

    public ExpenseBookBuilder(ExpenseBook expenseBook) {
        this.expenseBook = expenseBook;
    }

    /**
     * Adds a new {@code Expense} to the {@code ExpenseBook} that we are building.
     */
    public ExpenseBookBuilder withExpense(Expense expense) {
        expenseBook.addExpense(expense);
        return this;
    }

    public ExpenseBook build() {
        return expenseBook;
    }
}
