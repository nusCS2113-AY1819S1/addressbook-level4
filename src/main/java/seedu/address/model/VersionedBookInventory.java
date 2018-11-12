package seedu.address.model;

import java.util.ArrayList;
import java.util.List;

/**
 * {@code BookInventory} that keeps track of its own history.
 */
public class VersionedBookInventory extends BookInventory {

    private final List<ReadOnlyBookInventory> bookInventoryStateList;
    private int currentStatePointer;

    public VersionedBookInventory(ReadOnlyBookInventory initialState) {
        super(initialState);

        bookInventoryStateList = new ArrayList<>();
        bookInventoryStateList.add(new BookInventory(initialState));
        currentStatePointer = 0;
    }

    /**
     * Saves a copy of the current {@code BookInventory} state at the end of the state list.
     * Undone states are removed from the state list.
     */
    public void commit() {
        removeStatesAfterCurrentPointer();
        bookInventoryStateList.add(new BookInventory(this));
        currentStatePointer++;
    }

    private void removeStatesAfterCurrentPointer() {
        bookInventoryStateList.subList(currentStatePointer + 1, bookInventoryStateList.size()).clear();
    }

    /**
     * Restores the BookInventory to its previous state.
     */
    public void undo() {
        if (!canUndo()) {
            throw new NoUndoableStateException();
        }
        currentStatePointer--;
        resetData(bookInventoryStateList.get(currentStatePointer));
    }

    /**
     * Restores the BookInventory to its previously undone state.
     */
    public void redo() {
        if (!canRedo()) {
            throw new NoRedoableStateException();
        }
        currentStatePointer++;
        resetData(bookInventoryStateList.get(currentStatePointer));
    }

    /**
     * Returns true if {@code undo()} has BookInventory states to undo.
     */
    public boolean canUndo() {
        return currentStatePointer > 0;
    }

    /**
     * Returns true if {@code redo()} has BookInventory states to redo.
     */
    public boolean canRedo() {
        return currentStatePointer < bookInventoryStateList.size() - 1;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof VersionedBookInventory)) {
            return false;
        }

        VersionedBookInventory otherVersionedBookInventory = (VersionedBookInventory) other;

        // state check
        return super.equals(otherVersionedBookInventory)
                && bookInventoryStateList.equals(otherVersionedBookInventory.bookInventoryStateList)
                && currentStatePointer == otherVersionedBookInventory.currentStatePointer;
    }

    /**
     * Thrown when trying to {@code undo()} but can't.
     */
    public static class NoUndoableStateException extends RuntimeException {
        private NoUndoableStateException() {
            super("Current state pointer at start of bookInventoryState list, unable to undo.");
        }
    }

    /**
     * Thrown when trying to {@code redo()} but can't.
     */
    public static class NoRedoableStateException extends RuntimeException {
        private NoRedoableStateException() {
            super("Current state pointer at end of bookInventoryState list, unable to redo.");
        }
    }
}
