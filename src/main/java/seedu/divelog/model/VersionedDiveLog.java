package seedu.divelog.model;

import java.util.ArrayList;
import java.util.List;

/**
 * {@code DiveLog} that keeps track of its own history.
 */
public class VersionedDiveLog extends DiveLog {

    private final List<ReadOnlyDiveLog> diveLogStateList;
    private int currentStatePointer;


    public VersionedDiveLog(ReadOnlyDiveLog initialState) {
        super(initialState);

        diveLogStateList = new ArrayList<>();
        diveLogStateList.add(new DiveLog(initialState));
        currentStatePointer = 0;
    }

    /**
     * Saves a copy of the current {@code DiveLog} state at the end of the state list.
     * Undone states are removed from the state list.
     */
    public void commit() {
        removeStatesAfterCurrentPointer();
        diveLogStateList.add(new DiveLog(this));
        currentStatePointer++;
    }

    private void removeStatesAfterCurrentPointer() {
        diveLogStateList.subList(currentStatePointer + 1, diveLogStateList.size()).clear();
    }

    /**
     * Restores the divelog book to its previous state.
     */
    public void undo() {
        if (!canUndo()) {
            throw new NoUndoableStateException();
        }
        currentStatePointer--;
        resetData(diveLogStateList.get(currentStatePointer));
    }

    /**
     * Restores the divelog book to its previously undone state.
     */
    public void redo() {
        if (!canRedo()) {
            throw new NoRedoableStateException();
        }
        currentStatePointer++;
        resetData(diveLogStateList.get(currentStatePointer));
    }

    /**
     * Returns true if {@code undo()} has divelog book states to undo.
     */
    public boolean canUndo() {
        return currentStatePointer > 0;
    }

    /**
     * Returns true if {@code redo()} has divelog book states to redo.
     */
    public boolean canRedo() {
        return currentStatePointer < diveLogStateList.size() - 1;
    }

    @Override
    public boolean equals(Object other) {

        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof VersionedDiveLog)) {
            return false;
        }

        VersionedDiveLog otherVersionedDiveLog = (VersionedDiveLog) other;

        // state check
        return super.equals(otherVersionedDiveLog)
                && diveLogStateList.equals(otherVersionedDiveLog.diveLogStateList)
                && currentStatePointer == otherVersionedDiveLog.currentStatePointer;
    }

    /**
     * Thrown when trying to {@code undo()} but can't.
     */
    public static class NoUndoableStateException extends RuntimeException {
        private NoUndoableStateException() {
            super("Current state pointer at start of diveLogState list, unable to undo.");
        }
    }

    /**
     * Thrown when trying to {@code redo()} but can't.
     */
    public static class NoRedoableStateException extends RuntimeException {
        private NoRedoableStateException() {
            super("Current state pointer at end of diveLogState list, unable to redo.");
        }
    }
}
