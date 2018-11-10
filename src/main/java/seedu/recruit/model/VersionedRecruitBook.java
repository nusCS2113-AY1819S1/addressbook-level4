package seedu.recruit.model;

import java.util.ArrayList;
import java.util.List;

/**
 * {@code RecruitBook} that keeps track of its own history.
 */

public class VersionedRecruitBook extends RecruitBook {

    private List<RecruitBook> recruitBookStateList;
    private int currentStatePointer;

    public VersionedRecruitBook (ReadOnlyCandidateBook candidateBook, ReadOnlyCompanyBook companyBook) {
        super(candidateBook, companyBook);
        recruitBookStateList = new ArrayList<>();
        recruitBookStateList.add(new RecruitBook(new CandidateBook(candidateBook), new CompanyBook(companyBook)));
        currentStatePointer = 0;
    }

    /**
     * Saves a copy of the current {@code RecruitBook} state at the end of the state list.
     * Undone states are removed from the state list.
     */
    public void commit() {
        removeStatesAfterCurrentPointer();
        recruitBookStateList.add(new RecruitBook(this.getCandidateBook(), this.getCompanyBook()));
        currentStatePointer++;
    }

    private void removeStatesAfterCurrentPointer() {
        recruitBookStateList.subList(currentStatePointer + 1, recruitBookStateList.size()).clear();
    }

    /**
     * Restores the recruit book to its previous state.
     */
    public void undo() {
        if (!canUndo()) {
            throw new VersionedRecruitBook.NoUndoableStateException();
        }
        currentStatePointer--;
        resetData(recruitBookStateList.get(currentStatePointer).getCandidateBook(),
                recruitBookStateList.get(currentStatePointer).getCompanyBook());
    }

    /**
     * Restores the recruit book to its previously undone state.
     */
    public void redo() {
        if (!canRedo()) {
            throw new VersionedRecruitBook.NoRedoableStateException();
        }
        currentStatePointer++;
        resetData(recruitBookStateList.get(currentStatePointer).getCandidateBook(),
                recruitBookStateList.get(currentStatePointer).getCompanyBook());
    }

    /**
     * Returns true if {@code undo()} has recruit book states to undo.
     */
    public boolean canUndo() {
        return currentStatePointer > 0;
    }

    /**
     * Returns true if {@code redo()} has recruit book states to redo.
     */
    public boolean canRedo() {
        return currentStatePointer < recruitBookStateList.size() - 1;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof VersionedRecruitBook)) {
            return false;
        }

        VersionedRecruitBook otherVersionedRecruitBook = (VersionedRecruitBook) other;

        // state check
        return super.equals(otherVersionedRecruitBook)
                && recruitBookStateList.equals(otherVersionedRecruitBook.recruitBookStateList)
                && currentStatePointer == otherVersionedRecruitBook.currentStatePointer;
    }

    /**
     * Thrown when trying to {@code undo()} but can't.
     */
    public static class NoUndoableStateException extends RuntimeException {
        private NoUndoableStateException() {
            super("Current state pointer at start of recruitBookState list, unable to undo.");
        }
    }

    /**
     * Thrown when trying to {@code redo()} but can't.
     */
    public static class NoRedoableStateException extends RuntimeException {
        private NoRedoableStateException() {
            super("Current state pointer at end of recruitBookState list, unable to redo.");
        }
    }


}
