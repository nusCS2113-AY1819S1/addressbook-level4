package seedu.address.model;

import java.util.ArrayList;
import java.util.List;

/**
 * {@code AccountList} that keeps track of its own history.
 */
public class VersionedAccountList extends AccountList {

    private final List<ReadOnlyAccountList> accountListStateList;
    private int currentStatePointer;

    public VersionedAccountList(ReadOnlyAccountList initialState) {
        super(initialState);

        accountListStateList = new ArrayList<>();
        accountListStateList.add(new AccountList(initialState));
        currentStatePointer = 0;
    }

    /**
     * Saves a copy of the current {@code AccountList} state at the end of the state list.
     * Undone states are removed from the state list.
     */
    public void commit() {
        removeStatesAfterCurrentPointer();
        accountListStateList.add(new AccountList(this));
        currentStatePointer++;
    }

    private void removeStatesAfterCurrentPointer() {
        accountListStateList.subList(currentStatePointer + 1, accountListStateList.size()).clear();
    }

    /**
     * Restores the account list to its previous state.
     */
    public void undo() {
        if (!canUndo()) {
            throw new NoUndoableStateException();
        }
        currentStatePointer--;
        resetData(accountListStateList.get(currentStatePointer));
    }

    /**
     * Restores the account list to its previously undone state.
     */
    public void redo() {
        if (!canRedo()) {
            throw new NoRedoableStateException();
        }
        currentStatePointer++;
        resetData(accountListStateList.get(currentStatePointer));
    }

    /**
     * Returns true if {@code undo()} has account list states to undo.
     */
    public boolean canUndo() {
        return currentStatePointer > 0;
    }

    /**
     * Returns true if {@code redo()} has stock list states to redo.
     */
    public boolean canRedo() {
        return currentStatePointer < accountListStateList.size() - 1;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof VersionedAccountList)) {
            return false;
        }

        VersionedAccountList otherVersionedAccountList = (VersionedAccountList) other;

        // state check
        return super.equals(otherVersionedAccountList)
                && accountListStateList.equals(otherVersionedAccountList.accountListStateList)
                && currentStatePointer == otherVersionedAccountList.currentStatePointer;
    }

    /**
     * Thrown when trying to {@code undo()} but can't.
     */
    public static class NoUndoableStateException extends RuntimeException {
        private NoUndoableStateException() {
            super("Current state pointer at start of accountListState list, unable to undo.");
        }
    }

    /**
     * Thrown when trying to {@code redo()} but can't.
     */
    public static class NoRedoableStateException extends RuntimeException {
        private NoRedoableStateException() {
            super("Current state pointer at end of accountListState list, unable to redo.");
        }
    }
}
