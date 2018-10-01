package seedu.address.model;

import java.util.ArrayList;
import java.util.List;

/**
 * {@code StockList} that keeps track of its own history.
 */
public class VersionedStockList extends StockList {

    private final List<ReadOnlyStockList> stockListStateList;
    private int currentStatePointer;

    public VersionedStockList(ReadOnlyStockList initialState) {
        super(initialState);

        stockListStateList = new ArrayList<>();
        stockListStateList.add(new StockList(initialState));
        currentStatePointer = 0;
    }

    /**
     * Saves a copy of the current {@code StockList} state at the end of the state list.
     * Undone states are removed from the state list.
     */
    public void commit() {
        removeStatesAfterCurrentPointer();
        stockListStateList.add(new StockList(this));
        currentStatePointer++;
    }

    private void removeStatesAfterCurrentPointer() {
        stockListStateList.subList(currentStatePointer + 1, stockListStateList.size()).clear();
    }

    /**
     * Restores the stock list to its previous state.
     */
    public void undo() {
        if (!canUndo()) {
            throw new NoUndoableStateException();
        }
        currentStatePointer--;
        resetData(stockListStateList.get(currentStatePointer));
    }

    /**
     * Restores the stock list to its previously undone state.
     */
    public void redo() {
        if (!canRedo()) {
            throw new NoRedoableStateException();
        }
        currentStatePointer++;
        resetData(stockListStateList.get(currentStatePointer));
    }

    /**
     * Returns true if {@code undo()} has stock list states to undo.
     */
    public boolean canUndo() {
        return currentStatePointer > 0;
    }

    /**
     * Returns true if {@code redo()} has stock list states to redo.
     */
    public boolean canRedo() {
        return currentStatePointer < stockListStateList.size() - 1;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof VersionedStockList)) {
            return false;
        }

        VersionedStockList otherVersionedStockList = (VersionedStockList) other;

        // state check
        return super.equals(otherVersionedStockList)
                && stockListStateList.equals(otherVersionedStockList.stockListStateList)
                && currentStatePointer == otherVersionedStockList.currentStatePointer;
    }

    /**
     * Thrown when trying to {@code undo()} but can't.
     */
    public static class NoUndoableStateException extends RuntimeException {
        private NoUndoableStateException() {
            super("Current state pointer at start of stockListState list, unable to undo.");
        }
    }

    /**
     * Thrown when trying to {@code redo()} but can't.
     */
    public static class NoRedoableStateException extends RuntimeException {
        private NoRedoableStateException() {
            super("Current state pointer at end of stockListState list, unable to redo.");
        }
    }
}
