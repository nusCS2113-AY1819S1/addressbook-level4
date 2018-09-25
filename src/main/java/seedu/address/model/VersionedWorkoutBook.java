package seedu.address.model;

import java.util.ArrayList;
import java.util.List;

/**
 * {@code WorkoutBook} that keeps track of its own history.
 */
public class VersionedWorkoutBook extends WorkoutBook {

    private final List<ReadOnlyWorkoutBook> workoutBookStateList;
    private int currentStatePointer;

    public VersionedWorkoutBook(ReadOnlyWorkoutBook initialState) {
        super(initialState);

        workoutBookStateList = new ArrayList<>();
        workoutBookStateList.add(new WorkoutBook(initialState));
        currentStatePointer = 0;
    }

    /**
     * Saves a copy of the current {@code WorkoutBook} state at the end of the state list.
     * Undone states are removed from the state list.
     */
    public void commit() {
        removeStatesAfterCurrentPointer();
        workoutBookStateList.add(new WorkoutBook(this));
        currentStatePointer++;
    }

    private void removeStatesAfterCurrentPointer() {
        workoutBookStateList.subList(currentStatePointer + 1, workoutBookStateList.size()).clear();
    }

    /**
     * Restores the workout book to its previous state.
     */
    public void undo() {
        if (!canUndo()) {
            throw new NoUndoableStateException();
        }
        currentStatePointer--;
        resetData(workoutBookStateList.get(currentStatePointer));
    }

    /**
     * Restores the workout book to its previously undone state.
     */
    public void redo() {
        if (!canRedo()) {
            throw new NoRedoableStateException();
        }
        currentStatePointer++;
        resetData(workoutBookStateList.get(currentStatePointer));
    }

    /**
     * Returns true if {@code undo()} has workout book states to undo.
     */
    public boolean canUndo() {
        return currentStatePointer > 0;
    }

    /**
     * Returns true if {@code redo()} has workout book states to redo.
     */
    public boolean canRedo() {
        return currentStatePointer < workoutBookStateList.size() - 1;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof VersionedWorkoutBook)) {
            return false;
        }

        VersionedWorkoutBook otherVersionedWorkoutBook = (VersionedWorkoutBook) other;

        // state check
        return super.equals(otherVersionedWorkoutBook)
                && workoutBookStateList.equals(otherVersionedWorkoutBook.workoutBookStateList)
                && currentStatePointer == otherVersionedWorkoutBook.currentStatePointer;
    }

    /**
     * Thrown when trying to {@code undo()} but can't.
     */
    public static class NoUndoableStateException extends RuntimeException {
        private NoUndoableStateException() {
            super("Current state pointer at start of workoutBookState list, unable to undo.");
        }
    }

    /**
     * Thrown when trying to {@code redo()} but can't.
     */
    public static class NoRedoableStateException extends RuntimeException {
        private NoRedoableStateException() {
            super("Current state pointer at end of workoutBookState list, unable to redo.");
        }
    }
}
