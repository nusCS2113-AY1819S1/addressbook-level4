//@@author ian-tjahjono
package seedu.address.model;

import java.util.ArrayList;
import java.util.List;

/**
 * {@code EventBook} that keeps track of its own history.
 */
public class VersionedEventBook extends EventBook {
    private final List<ReadOnlyEventBook> eventBookStateList;
    private int currentStatePointer;

    public VersionedEventBook(ReadOnlyEventBook initialState) {
        super(initialState);

        eventBookStateList = new ArrayList<>();
        eventBookStateList.add(new EventBook(initialState));
        currentStatePointer = 0;
    }

    /**
     * Saves a copy of the current {@code EventBook} state at the end of the state list.
     * Undone states are removed from the state list.
     */
    public void commit() {
        removeStatesAfterCurrentPointer();
        eventBookStateList.add(new EventBook(this));
        currentStatePointer++;
    }

    private void removeStatesAfterCurrentPointer() {
        eventBookStateList.subList(currentStatePointer + 1, eventBookStateList.size()).clear();
    }

    /**
     * Restores the event book to its previous state.
     */
    public void undo() {
        if (!canUndo()) {
            throw new NoUndoableStateException();
        }
        currentStatePointer--;
        resetData(eventBookStateList.get(currentStatePointer));
    }

    /**
     * Restores the event book to its previously undone state.
     */
    public void redo() {
        if (!canRedo()) {
            throw new NoRedoableStateException();
        }
        currentStatePointer++;
        resetData(eventBookStateList.get(currentStatePointer));
    }

    /**
     * Returns true if {@code undo()} has event book states to undo.
     */
    public boolean canUndo() {
        return currentStatePointer > 0;
    }

    /**
     * Returns true if {@code redo()} has event book states to redo.
     */
    public boolean canRedo() {
        return currentStatePointer < eventBookStateList.size() - 1;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof VersionedEventBook)) {
            return false;
        }

        VersionedEventBook otherVersionedEventBook = (VersionedEventBook) other;

        // state check
        return super.equals(otherVersionedEventBook)
                && eventBookStateList.equals(otherVersionedEventBook.eventBookStateList)
                && currentStatePointer == otherVersionedEventBook.currentStatePointer;
    }

    /**
     * Thrown when trying to {@code undo()} but can't.
     */
    public static class NoUndoableStateException extends RuntimeException {
        private NoUndoableStateException() {
            super("Current state pointer at start of eventBookState list, unable to undo.");
        }
    }

    /**
     * Thrown when trying to {@code redo()} but can't.
     */
    public static class NoRedoableStateException extends RuntimeException {
        private NoRedoableStateException() {
            super("Current state pointer at end of eventBookState list, unable to redo.");
        }
    }

}
