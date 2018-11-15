package seedu.address.model;

import java.util.ArrayList;
import java.util.List;

/**
 * {@code ProductDatabase} that keeps track of its own history.
 */
public class VersionedProductDatabase extends ProductDatabase {

    private final List<ReadOnlyProductDatabase> productDatabaseStateList;
    private int currentStatePointer;

    public VersionedProductDatabase(ReadOnlyProductDatabase initialState) {
        super(initialState);

        productDatabaseStateList = new ArrayList<>();
        productDatabaseStateList.add(new ProductDatabase(initialState));
        currentStatePointer = 0;
    }

    /**
     * Saves a copy of the current {@code ProductDatabase} state at the end of the state list.
     * Undone states are removed from the state list.
     */

    public void commit() {
        removeStatesAfterCurrentPointer();
        productDatabaseStateList.add(new ProductDatabase(this));
        currentStatePointer++;
    }

    private void removeStatesAfterCurrentPointer() {
        productDatabaseStateList.subList(currentStatePointer + 1, productDatabaseStateList.size()).clear();
    }

    /**
     * Restores the address book to its previous state.
     */
    public void undo() {
        if (!canUndo()) {
            throw new NoUndoableStateException();
        }
        currentStatePointer--;
        resetData(productDatabaseStateList.get(currentStatePointer));
    }

    /**
     * Restores the address book to its previously undone state.
     */
    public void redo() {
        if (!canRedo()) {
            throw new NoRedoableStateException();
        }
        currentStatePointer++;
        resetData(productDatabaseStateList.get(currentStatePointer));
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
        return currentStatePointer < productDatabaseStateList.size() - 1;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof VersionedProductDatabase)) {
            return false;
        }

        VersionedProductDatabase otherVersionedAddressBook = (VersionedProductDatabase) other;

        // state check
        return super.equals(otherVersionedAddressBook)
                && productDatabaseStateList.equals(otherVersionedAddressBook.productDatabaseStateList)
                && currentStatePointer == otherVersionedAddressBook.currentStatePointer;
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
