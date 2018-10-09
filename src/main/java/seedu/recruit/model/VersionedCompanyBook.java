package seedu.recruit.model;

import java.util.ArrayList;
import java.util.List;

/**
 * {@code CompanyBook} that keeps track of its own history.
 */
public class VersionedCompanyBook extends CompanyBook {

    private final List<ReadOnlyCompanyBook> companyBookStateList;
    private int currentStatePointer;

    public VersionedCompanyBook(ReadOnlyCompanyBook initialState) {
        super(initialState);

        companyBookStateList = new ArrayList<>();
        companyBookStateList.add(new CompanyBook(initialState));
        currentStatePointer = 0;
    }

    /**
     * Saves a copy of the current {@code CompanyBook} state at the end of the state list.
     * Undone states are removed from the state list.
     */
    public void commit() {
        removeStatesAfterCurrentPointer();
        companyBookStateList.add(new CompanyBook(this));
        currentStatePointer++;
    }

    private void removeStatesAfterCurrentPointer() {
        companyBookStateList.subList(currentStatePointer + 1, companyBookStateList.size()).clear();
    }

    /**
     * Restores the CompanyBook to its previous state.
     */
    public void undo() {
        if (!canUndo()) {
            throw new NoUndoableStateException();
        }
        currentStatePointer--;
        resetData(companyBookStateList.get(currentStatePointer));
    }

    /**
     * Restores the CompanyBook to its previously undone state.
     */
    public void redo() {
        if (!canRedo()) {
            throw new NoRedoableStateException();
        }
        currentStatePointer++;
        resetData(companyBookStateList.get(currentStatePointer));
    }

    /**
     * Returns true if {@code undo()} has CompanyBook states to undo.
     */
    public boolean canUndo() {
        return currentStatePointer > 0;
    }

    /**
     * Returns true if {@code redo()} has CompanyBook states to redo.
     */
    public boolean canRedo() {
        return currentStatePointer < companyBookStateList.size() - 1;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof VersionedCompanyBook)) {
            return false;
        }

        VersionedCompanyBook otherVersionedCompanyBook = (VersionedCompanyBook) other;

        // state check
        return super.equals(otherVersionedCompanyBook)
                && companyBookStateList.equals(otherVersionedCompanyBook.companyBookStateList)
                && currentStatePointer == otherVersionedCompanyBook.currentStatePointer;
    }

    /**
     * Thrown when trying to {@code undo()} but can't.
     */
    public static class NoUndoableStateException extends RuntimeException {
        private NoUndoableStateException() {
            super("Current state pointer at start of companyBookState list, unable to undo.");
        }
    }

    /**
     * Thrown when trying to {@code redo()} but can't.
     */
    public static class NoRedoableStateException extends RuntimeException {
        private NoRedoableStateException() {
            super("Current state pointer at end of companyBookState list, unable to redo.");
        }
    }
}
