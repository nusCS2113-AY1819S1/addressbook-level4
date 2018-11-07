package seedu.address.model;

import java.util.ArrayList;
import java.util.List;

/**
 * {@code AddressBook} that keeps track of its own history.
 */
public class VersionedFinalBudgetsBook extends FinalBudgetsBook {

    private final List<ReadOnlyFinalBudgetBook> finalBudgetsBookStateList;
    private int currentStatePointer;

    public VersionedFinalBudgetsBook(ReadOnlyFinalBudgetBook initialState) {
        super(initialState);

        finalBudgetsBookStateList = new ArrayList<>();
        finalBudgetsBookStateList.add(new FinalBudgetsBook(initialState));
        currentStatePointer = 0;
    }

    /**
     * Saves a copy of the current {@code FinalBudgetsBook} state at the end of the state list.
     * Undone states are removed from the state list.
     */
    public void commit() {
        removeStatesAfterCurrentPointer();
        finalBudgetsBookStateList.add(new FinalBudgetsBook(this));
        currentStatePointer++;
    }

    private void removeStatesAfterCurrentPointer() {
        finalBudgetsBookStateList.subList(currentStatePointer + 1, finalBudgetsBookStateList.size()).clear();
    }

    /**
     * Restores the final budgets book to its previous state.
     */
    public void undo() {
        if (!canUndo()) {
            throw new NoUndoableStateException();
        }
        currentStatePointer--;
        resetData(finalBudgetsBookStateList.get(currentStatePointer));
    }

    /**
     * Restores the final budgets book to its previously undone state.
     */
    public void redo() {
        if (!canRedo()) {
            throw new NoRedoableStateException();
        }
        currentStatePointer++;
        resetData(finalBudgetsBookStateList.get(currentStatePointer));
    }

    /**
     * Returns true if {@code undo()} has final budgets book states to undo.
     */
    public boolean canUndo() {
        return currentStatePointer > 0;
    }

    /**
     * Returns true if {@code redo()} has final budgets book states to redo.
     */
    public boolean canRedo() {
        return currentStatePointer < finalBudgetsBookStateList.size() - 1;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof VersionedFinalBudgetsBook)) {
            return false;
        }

        VersionedFinalBudgetsBook otherVersionedFinalBudgetsBook = (VersionedFinalBudgetsBook) other;

        // state check
        return super.equals(otherVersionedFinalBudgetsBook)
                && finalBudgetsBookStateList.equals(otherVersionedFinalBudgetsBook.finalBudgetsBookStateList)
                && currentStatePointer == otherVersionedFinalBudgetsBook.currentStatePointer;
    }

    /**
     * Thrown when trying to {@code undo()} but can't.
     */
    public static class NoUndoableStateException extends RuntimeException {
        private NoUndoableStateException() {
            super("Current state pointer at start of finalBudgetsBookState list, unable to undo.");
        }
    }

    /**
     * Thrown when trying to {@code redo()} but can't.
     */
    public static class NoRedoableStateException extends RuntimeException {
        private NoRedoableStateException() {
            super("Current state pointer at end of finalBudgetsBookState list, unable to redo.");
        }
    }
}
