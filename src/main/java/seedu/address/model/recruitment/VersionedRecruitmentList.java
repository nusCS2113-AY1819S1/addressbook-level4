package seedu.address.model.recruitment;

import java.util.ArrayList;
import java.util.List;

/**
 * {@code AddressBook} that keeps track of its own history.
 */
public class VersionedRecruitmentList extends RecruitmentList {

    private final List<ReadOnlyRecruitmentList> recruitmentListStateList;
    private int currentStatePointer;

    public VersionedRecruitmentList(ReadOnlyRecruitmentList initialState) {
        super(initialState);

        recruitmentListStateList = new ArrayList<>();
        recruitmentListStateList.add(new RecruitmentList(initialState));
        currentStatePointer = 0;
    }

    /**
     * Saves a copy of the current {@code AddressBook} state at the end of the state list.
     * Undone states are removed from the state list.
     */
    public void commit() {
        removeStatesAfterCurrentPointer();
        recruitmentListStateList.add(new RecruitmentList(this));
        currentStatePointer++;
    }

    private void removeStatesAfterCurrentPointer() {
        recruitmentListStateList.subList(currentStatePointer + 1, recruitmentListStateList.size()).clear();
    }

    /**
     * Restores the address book to its previous state.
     */
    public void undo() {
        if (!canUndo()) {
            throw new NoUndoableStateException();
        }
        currentStatePointer--;
        resetData(recruitmentListStateList.get(currentStatePointer));
    }

    /**
     * Restores the address book to its previously undone state.
     */
    public void redo() {
        if (!canRedo()) {
            throw new NoRedoableStateException();
        }
        currentStatePointer++;
        resetData(recruitmentListStateList.get(currentStatePointer));
    }

    /**
     * Returns true if {@code undo()} has address book states to undo.
     */
    public boolean canUndo() {
        return currentStatePointer > 0;
    }

    /**
     * Returns true if {@code redo()} has address book states to redo.
     */
    public boolean canRedo() {
        return currentStatePointer < recruitmentListStateList.size() - 1;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof VersionedRecruitmentList)) {
            return false;
        }

        VersionedRecruitmentList otherVersionedRecruitmentList = (VersionedRecruitmentList) other;

        // state check
        return super.equals(otherVersionedRecruitmentList)
                && recruitmentListStateList.equals(otherVersionedRecruitmentList.recruitmentListStateList)
                && currentStatePointer == otherVersionedRecruitmentList.currentStatePointer;
    }

    /**
     * Thrown when trying to {@code undo()} but can't.
     */
    public static class NoUndoableStateException extends RuntimeException {
        private NoUndoableStateException() {
            super("Current state pointer at start of addressBookState list, unable to undo.");
        }
    }

    /**
     * Thrown when trying to {@code redo()} but can't.
     */
    public static class NoRedoableStateException extends RuntimeException {
        private NoRedoableStateException() {
            super("Current state pointer at end of addressBookState list, unable to redo.");
        }
    }
}
