package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.expense.Expense;

/**
 * Unmodifiable view of an expense book
 */
public interface ReadOnlyExpenseBook {
    /**
     * Returns an unmodifiable view of the expenses list.
     */
    ObservableList<Expense> getExpenseList();
}
