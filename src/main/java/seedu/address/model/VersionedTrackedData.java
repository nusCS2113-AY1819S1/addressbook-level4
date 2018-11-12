package seedu.address.model;

import java.util.ArrayList;
import java.util.List;

/**
 * {@code WorkoutBook} that keeps track of its own history.
 */
public class VersionedTrackedData extends TrackedData {

    private final List<ReadOnlyTrackedData> trackedDataStateList;
    private int currentStatePointer;

    public VersionedTrackedData(ReadOnlyTrackedData initialState) {
        super(initialState);

        trackedDataStateList = new ArrayList<>();
        trackedDataStateList.add(new TrackedData(initialState));
        currentStatePointer = 0;
    }

    /**
     * Saves a copy of the current {@code TrackedData} state at the end of the state list.
     * Undone states are removed from the state list.
     */
    public void commit() {
        removeStatesAfterCurrentPointer();
        trackedDataStateList.add(new TrackedData(this));
        currentStatePointer++;
    }

    private void removeStatesAfterCurrentPointer() {
        trackedDataStateList.subList(currentStatePointer + 1, trackedDataStateList.size()).clear();
    }

    /**
     * Restores the tracked data to its previous state.
     */
    public void undo() {
        if (!canUndo()) {
            throw new NoUndoableStateException();
        }
        currentStatePointer--;
        resetData(trackedDataStateList.get(currentStatePointer));
    }

    /**
     * Restores the workout book to its previously undone state.
     */
    public void redo() {
        if (!canRedo()) {
            throw new NoRedoableStateException();
        }
        currentStatePointer++;
        resetData(trackedDataStateList.get(currentStatePointer));
    }

    /**
     * Returns true if {@code undo()} has tracked data states to undo.
     */
    public boolean canUndo() {
        return currentStatePointer > 0;
    }

    /**
     * Returns true if {@code redo()} has tracked data states to redo.
     */
    public boolean canRedo() {
        return currentStatePointer < trackedDataStateList.size() - 1;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof VersionedTrackedData)) {
            return false;
        }

        VersionedTrackedData otherVersionedTrackedData = (VersionedTrackedData) other;

        // state check
        return super.equals(otherVersionedTrackedData)
                && trackedDataStateList.equals(otherVersionedTrackedData.trackedDataStateList)
                && currentStatePointer == otherVersionedTrackedData.currentStatePointer;
    }

    /**
     * Thrown when trying to {@code undo()} but can't.
     */
    public static class NoUndoableStateException extends RuntimeException {
        private NoUndoableStateException() {
            super("Current state pointer at start of trackedDataState list, unable to undo.");
        }
    }

    /**
     * Thrown when trying to {@code redo()} but can't.
     */
    public static class NoRedoableStateException extends RuntimeException {
        private NoRedoableStateException() {
            super("Current state pointer at end of trackedDataState list, unable to redo.");
        }
    }
}
