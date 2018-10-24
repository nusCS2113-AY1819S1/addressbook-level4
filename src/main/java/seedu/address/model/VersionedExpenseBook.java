package seedu.address.model;

import java.util.ArrayList;
import java.util.List;

/**
 * {@code ExpenseBook} that keeps track of its own history.
 */
public class VersionedExpenseBook extends ExpenseBook {
    private final List<ReadOnlyExpenseBook> expenseBookStateList;
    private int currentStatePointer;

    public VersionedExpenseBook(ReadOnlyExpenseBook initialState) {
        super(initialState);

        expenseBookStateList = new ArrayList<>();
        expenseBookStateList.add(new ExpenseBook(initialState));
        currentStatePointer = 0;
    }

    /**
     * Saves a copy of the current {@code ExpenseBook} state at the end of the state list.
     * Undone states are removed from the state list.
     */
    public void commit() {
        removeStatesAfterCurrentPointer();
        expenseBookStateList.add(new ExpenseBook(this));
        currentStatePointer++;
    }

    private void removeStatesAfterCurrentPointer() {
        expenseBookStateList.subList(currentStatePointer + 1, expenseBookStateList.size()).clear();
    }

    /**
     * Restores the expense book to its previous state.
     */
    public void undo() {
        if (!canUndo()) {
            throw new NoUndoableStateException();
        }
        currentStatePointer--;
        resetData(expenseBookStateList.get(currentStatePointer));
    }

    /**
     * Restores the expense book to its previously undone state.
     */
    public void redo() {
        if (!canRedo()) {
            throw new NoRedoableStateException();
        }
        currentStatePointer++;
        resetData(expenseBookStateList.get(currentStatePointer));
    }

    /**
     * Returns true if {@code undo()} has expense book states to undo.
     */
    public boolean canUndo() {
        return currentStatePointer > 0;
    }

    /**
     * Returns true if {@code redo()} has expense book states to redo.
     */
    public boolean canRedo() {
        return currentStatePointer < expenseBookStateList.size() - 1;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof VersionedExpenseBook)) {
            return false;
        }

        VersionedExpenseBook otherVersionedExpenseBook = (VersionedExpenseBook) other;

        // state check
        return super.equals(otherVersionedExpenseBook)
                && expenseBookStateList.equals(otherVersionedExpenseBook.expenseBookStateList)
                && currentStatePointer == otherVersionedExpenseBook.currentStatePointer;
    }

    /**
     * Thrown when trying to {@code undo()} but can't.
     */
    public static class NoUndoableStateException extends RuntimeException {
        private NoUndoableStateException() {
            super("Current state pointer at start of expenseBookState list, unable to undo.");
        }
    }

    /**
     * Thrown when trying to {@code redo()} but can't.
     */
    public static class NoRedoableStateException extends RuntimeException {
        private NoRedoableStateException() {
            super("Current state pointer at end of expenseBookState list, unable to redo.");
        }
    }

}
