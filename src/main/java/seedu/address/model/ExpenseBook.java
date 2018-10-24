package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.model.expense.Expense;
import seedu.address.model.expense.ExpenseList;

/**
 * Wraps all data at the expense-book level
 */
public class ExpenseBook implements ReadOnlyExpenseBook {
    private final ExpenseList expenses;

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        expenses = new ExpenseList();
    }

    public ExpenseBook() {}

    /**
     * Creates an ExpenseBook using the Expenses in the {@code toBeCopied}
     */
    public ExpenseBook(ReadOnlyExpenseBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations
    /**
     * Replaces the contents of the expense list with {@code expenses}.
     */
    public void setExpenses(List<Expense> expenses) {
        this.expenses.setExpenses(expenses);
    }

    /**
     * Resets the existing data of this {@code ExpenseBook} with {@code newData}.
     */
    public void resetData(ReadOnlyExpenseBook newData) {
        requireNonNull(newData);
        setExpenses(newData.getExpenseList());
    }

    //// expense-level operations

    /**
     * Adds an expense to the address book.
     */
    public void addExpense(Expense e) {
        expenses.add(e);
    }

    /**
     * Replaces the given expense {@code target} in the list with {@code editedExpense}.
     * {@code target} must exist in the expense book.
     */
    public void updateExpense(Expense target, Expense editedExpense) {
        requireNonNull(editedExpense);
        expenses.setExpenses(target, editedExpense);
    }

    /**
     * Removes {@code key} from this {@code ExpenseBook}.
     * {@code key} must exist in the expense book.
     */
    public void removeExpense(Expense key) {
        expenses.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return expenses.asUnmodifiableObservableList().size() + " expenses";
        // TODO: refine later
    }

    @Override
    public ObservableList<Expense> getExpenseList() {
        return expenses.asUnmodifiableObservableList();
    }

    @Override
    public int hashCode() {
        return expenses.hashCode();
    }
}
