package seedu.address.model;

import java.util.ArrayList;
import java.util.List;

/**
 * {@code JobBook} that keeps track of its own history.
 */
public class VersionedJobBook extends JobBook {

    private final List<ReadOnlyJobBook> jobBookStateList;
    private int currentStatePointer;

    public VersionedJobBook(ReadOnlyJobBook initialState) {
        super(initialState);

        jobBookStateList = new ArrayList<>();
        jobBookStateList.add(new JobBook(initialState));
        currentStatePointer = 0;
    }

    /**
     * Saves a copy of the current {@code JobBook} state at the end of the state list.
     * Undone states are removed from the state list.
     */
    public void commit() {
        removeStatesAfterCurrentPointer();
        jobBookStateList.add(new JobBook(this));
        currentStatePointer++;
    }

    private void removeStatesAfterCurrentPointer() {
        jobBookStateList.subList(currentStatePointer + 1, jobBookStateList.size()).clear();
    }

    /**
     * Restores the job book to its previous state.
     */
    public void undo() {
        if (!canUndo()) {
            throw new NoUndoableStateException();
        }
        currentStatePointer--;
        resetData(jobBookStateList.get(currentStatePointer));
    }

    /**
     * Restores the job book to its previously undone state.
     */
    public void redo() {
        if (!canRedo()) {
            throw new NoRedoableStateException();
        }
        currentStatePointer++;
        resetData(jobBookStateList.get(currentStatePointer));
    }

    /**
     * Returns true if {@code undo()} has job book states to undo.
     */
    public boolean canUndo() {
        return currentStatePointer > 0;
    }

    /**
     * Returns true if {@code redo()} has job book states to redo.
     */
    public boolean canRedo() {
        return currentStatePointer < jobBookStateList.size() - 1;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof VersionedJobBook)) {
            return false;
        }

        VersionedJobBook otherVersionedJobBook = (VersionedJobBook) other;

        // state check
        return super.equals(otherVersionedJobBook)
                && jobBookStateList.equals(otherVersionedJobBook.jobBookStateList)
                && currentStatePointer == otherVersionedJobBook.currentStatePointer;
    }

    /**
     * Thrown when trying to {@code undo()} but can't.
     */
    public static class NoUndoableStateException extends RuntimeException {
        private NoUndoableStateException() {
            super("Current state pointer at start of jobBookState list, unable to undo.");
        }
    }

    /**
     * Thrown when trying to {@code redo()} but can't.
     */
    public static class NoRedoableStateException extends RuntimeException {
        private NoRedoableStateException() {
            super("Current state pointer at end of jobBookState list, unable to redo.");
        }
    }
}
