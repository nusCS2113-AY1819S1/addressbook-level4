package seedu.address.model;

import java.util.ArrayList;
import java.util.List;

/**
 * {@code ClubBudgetElementsBook} that keeps track of its own history.
 */
public class VersionedClubBudgetElementsBook extends ClubBudgetElementsBook {

    private final List<ReadOnlyClubBudgetElementsBook> clubBudgetElementsBookStateList;
    private int currentStatePointer;

    public VersionedClubBudgetElementsBook(ReadOnlyClubBudgetElementsBook initialState) {
        super(initialState);

        clubBudgetElementsBookStateList = new ArrayList<>();
        clubBudgetElementsBookStateList.add(new ClubBudgetElementsBook(initialState));
        currentStatePointer = 0;
    }

    /**
     * Saves a copy of the current {@code ClubBudgetElementsBook} state at the end of the state list.
     * Undone states are removed from the state list.
     */
    public void commit() {
        removeStatesAfterCurrentPointer();
        clubBudgetElementsBookStateList.add(new ClubBudgetElementsBook(this));
        currentStatePointer++;
    }

    private void removeStatesAfterCurrentPointer() {
        clubBudgetElementsBookStateList.subList(currentStatePointer + 1,
                clubBudgetElementsBookStateList.size()).clear();
    }

    /**
     * Restores the club budget elements book to its previous state.
     */
    public void undo() {
        if (!canUndo()) {
            throw new NoUndoableStateException();
        }
        currentStatePointer--;
        resetData(clubBudgetElementsBookStateList.get(currentStatePointer));
    }

    /**
     * Restores the club budget elements book to its previously undone state.
     */
    public void redo() {
        if (!canRedo()) {
            throw new NoRedoableStateException();
        }
        currentStatePointer++;
        resetData(clubBudgetElementsBookStateList.get(currentStatePointer));
    }

    /**
     * Returns true if {@code undo()} has club budget elements book states to undo.
     */
    public boolean canUndo() {
        return currentStatePointer > 0;
    }

    /**
     * Returns true if {@code redo()} has club budget elements book states to redo.
     */
    public boolean canRedo() {
        return currentStatePointer < clubBudgetElementsBookStateList.size() - 1;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof VersionedClubBudgetElementsBook)) {
            return false;
        }

        VersionedClubBudgetElementsBook otherVersionedClubBudgetElementsBook = (VersionedClubBudgetElementsBook) other;

        // state check
        return super.equals(otherVersionedClubBudgetElementsBook)
                && clubBudgetElementsBookStateList.equals(otherVersionedClubBudgetElementsBook
                .clubBudgetElementsBookStateList)
                && currentStatePointer == otherVersionedClubBudgetElementsBook.currentStatePointer;
    }

    /**
     * Thrown when trying to {@code undo()} but can't.
     */
    public static class NoUndoableStateException extends RuntimeException {
        private NoUndoableStateException() {
            super("Current state pointer at start of clubBudgetElementsBookState list, unable to undo.");
        }
    }

    /**
     * Thrown when trying to {@code redo()} but can't.
     */
    public static class NoRedoableStateException extends RuntimeException {
        private NoRedoableStateException() {
            super("Current state pointer at end of clubBudgetElementsBookState list, unable to redo.");
        }
    }
}
