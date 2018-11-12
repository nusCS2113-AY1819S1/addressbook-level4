package seedu.address.model;

import java.util.ArrayList;
import java.util.List;

/**
 * {@code TrackedDataList} that keeps track of its own history.
 */
public class VersionedTrackedDataList extends TrackedDataList {

    private final List<ReadOnlyTrackedDataList> trackedDataListStateList;
    private int currentStatePointer;

    public VersionedTrackedDataList(ReadOnlyTrackedDataList initialState) {
        super(initialState);

        trackedDataListStateList = new ArrayList<>();
        trackedDataListStateList.add(new TrackedDataList(initialState));
        currentStatePointer = 0;
    }

    /**
     * Saves a copy of the current {@code TrackedDataList} state at the end of the state list.
     * Undone states are removed from the state list.
     */
    public void commit() {
        removeStatesAfterCurrentPointer();
        trackedDataListStateList.add(new TrackedDataList(this));
        currentStatePointer++;
    }

    private void removeStatesAfterCurrentPointer() {
        trackedDataListStateList.subList(currentStatePointer + 1, trackedDataListStateList.size()).clear();
    }

    /**
     * Restores the TrackedDataList to its previous state.
     */
    public void undo() {
        if (!canUndo()) {
            throw new NoUndoableStateException();
        }
        currentStatePointer--;
        resetData(trackedDataListStateList.get(currentStatePointer));
    }

    /**
     * Restores the TrackedDataList to its previously undone state.
     */
    public void redo() {
        if (!canRedo()) {
            throw new NoRedoableStateException();
        }
        currentStatePointer++;
        resetData(trackedDataListStateList.get(currentStatePointer));
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
        return currentStatePointer < trackedDataListStateList.size() - 1;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof VersionedTrackedDataList)) {
            return false;
        }

        VersionedTrackedDataList otherVersionedTrackedDataList = (VersionedTrackedDataList) other;

        // state check
        return super.equals(otherVersionedTrackedDataList)
                && trackedDataListStateList.equals(otherVersionedTrackedDataList.trackedDataListStateList)
                && currentStatePointer == otherVersionedTrackedDataList.currentStatePointer;
    }

    /**
     * Thrown when trying to {@code undo()} but can't.
     */
    public static class NoUndoableStateException extends RuntimeException {
        private NoUndoableStateException() {
            super("Current state pointer at start of trackedDataListState list, unable to undo.");
        }
    }

    /**
     * Thrown when trying to {@code redo()} but can't.
     */
    public static class NoRedoableStateException extends RuntimeException {
        private NoRedoableStateException() {
            super("Current state pointer at end of trackedDataListState list, unable to redo.");
        }
    }
}
