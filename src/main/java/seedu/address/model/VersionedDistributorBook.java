package seedu.address.model;

import java.util.ArrayList;
import java.util.List;

/**
 * {@code DistributorBook} that keeps track of its own history.
 */
public class VersionedDistributorBook extends DistributorBook {

    private final List<ReadOnlyDistributorBook> distributorBookStateList;
    private int currentStatePointer;

    public VersionedDistributorBook(ReadOnlyDistributorBook initialState) {
        super(initialState);

        distributorBookStateList = new ArrayList<>();
        distributorBookStateList.add(new DistributorBook(initialState));
        currentStatePointer = 0;
    }

    /**
     * Saves a copy of the current {@code DistributorBook} state at the end of the state list.
     * Undone states are removed from the state list.
     */

    public void commit() {
        removeStatesAfterCurrentPointer();
        distributorBookStateList.add(new DistributorBook(this));
        currentStatePointer++;
    }

    private void removeStatesAfterCurrentPointer() {
        distributorBookStateList.subList(currentStatePointer + 1, distributorBookStateList.size()).clear();
    }

    /**
     * Restores the distributor book to its previous state.
     */
    public void undo() {
        if (!canUndo()) {
            throw new NoUndoableStateException();
        }
        currentStatePointer--;
        resetData(distributorBookStateList.get(currentStatePointer));
    }

    /**
     * Restores the distributor book to its previously undone state.
     */
    public void redo() {
        if (!canRedo()) {
            throw new NoRedoableStateException();
        }
        currentStatePointer++;
        resetData(distributorBookStateList.get(currentStatePointer));
    }

    /**
     * Returns true if {@code undo()} has address book states to undo.
     */
    public boolean canUndo() {
        return currentStatePointer > 0;
    }

    /**
     * Returns true if {@code redo()} has address book states to redo.
     */
    public boolean canRedo() {
        return currentStatePointer < distributorBookStateList.size() - 1;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof VersionedDistributorBook)) {
            return false;
        }

        VersionedDistributorBook otherVersionedDistributorBook = (VersionedDistributorBook) other;

        // state check
        return super.equals(otherVersionedDistributorBook)
                && distributorBookStateList.equals(otherVersionedDistributorBook.distributorBookStateList)
                && currentStatePointer == otherVersionedDistributorBook.currentStatePointer;
    }

    /**
     * Thrown when trying to {@code undo()} but can't.
     */
    public static class NoUndoableStateException extends RuntimeException {
        private NoUndoableStateException() {
            super("Current state pointer at start of addressBookState list, unable to undo.");
        }
    }

    /**
     * Thrown when trying to {@code redo()} but can't.
     */
    public static class NoRedoableStateException extends RuntimeException {
        private NoRedoableStateException() {
            super("Current state pointer at end of addressBookState list, unable to redo.");
        }
    }
}
