package seedu.address.model.expense;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.expense.exceptions.ExpenseNotFoundException;

/**
 * A list of expense that does not allow nulls.
 *
 * Supports a minimal set of list operations.
 **/
public class ExpenseList implements Iterable<Expense> {

    private final ObservableList<Expense> internalList = FXCollections.observableArrayList();

    /**
     * Adds an expense to the list.
     */
    public void add(Expense toAdd) {
        requireNonNull(toAdd);
        internalList.add(toAdd);
        insertionSort(internalList);
    }

    /**
     * Replaces the expense {@code target} in the list with {@code editedExpense}.
     * {@code target} must exist in the list.
     */
    public void setExpenses(Expense target, Expense editedExpense) {
        requireAllNonNull(target, editedExpense);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new ExpenseNotFoundException();
        }

        internalList.set(index, editedExpense);
    }

    /**
     * Replaces the contents of this list with {@code expenses}.
     */
    public void setExpenses(List<Expense> expenses) {
        requireAllNonNull(expenses);
        internalList.setAll(expenses);
    }

    /**
     * Removes the equivalent expense from the list.
     * The expense must exist in the list.
     */
    public void remove(Expense toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new ExpenseNotFoundException();
        }
    }


    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Expense> asUnmodifiableObservableList() {
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public Iterator<Expense> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ExpenseList // instanceof handles nulls
                && internalList.equals(((ExpenseList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * @param internalList list of expenses to be sorted
     */
    private void insertionSort(ObservableList<Expense> internalList) {
        int n = internalList.size();
        for (int i = 1; i < n; i++) {
            Expense key = internalList.get(i);
            int j = i - 1;
            while (j >= 0 && internalList.get(j).compareTo(key) > 0) {
                internalList.set(j + 1, internalList.get(j));
                j = j - 1;
            }
            internalList.set(j + 1, key);
        }

    }
}
