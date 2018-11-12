package seedu.address.model;

import java.util.ArrayList;
import java.util.List;

import seedu.address.model.exceptions.NoRedoableStateException;
import seedu.address.model.exceptions.NoUndoableStateException;

/**
 * {@code EventList} that keeps track of its own history.
 */
public class VersionedEventList extends EventList {

    private final List<ReadOnlyEventList> eventListStateList;
    private int currentStatePointer;

    public VersionedEventList(ReadOnlyEventList initialState) {
        super(initialState);

        eventListStateList = new ArrayList<>();
        eventListStateList.add(new EventList(initialState));
        currentStatePointer = 0;
    }

    /**
     * Saves a copy of the current {@code EventList} state at the end of the state list.
     * Undone states are removed from the state list.
     */
    public void commit() {
        removeStatesAfterCurrentPointer();
        eventListStateList.add(new EventList(this));
        currentStatePointer++;
    }

    private void removeStatesAfterCurrentPointer() {
        eventListStateList.subList(currentStatePointer + 1, eventListStateList.size()).clear();
    }

    /**
     * Restores the event list to its previous state.
     */
    public void undo() {
        if (!canUndo()) {
            throw new NoUndoableStateException();
        }
        currentStatePointer--;
        resetData(eventListStateList.get(currentStatePointer));
    }

    /**
     * Restores the event list to its previously undone state.
     */
    public void redo() {
        if (!canRedo()) {
            throw new NoRedoableStateException();
        }
        currentStatePointer++;
        resetData(eventListStateList.get(currentStatePointer));
    }

    /**
     * Returns true if {@code undo()} has event list states to undo.
     */
    public boolean canUndo() {
        return currentStatePointer > 0;
    }

    /**
     * Returns true if {@code redo()} has event list states to redo.
     */
    public boolean canRedo() {
        return currentStatePointer < eventListStateList.size() - 1;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof VersionedEventList)) {
            return false;
        }

        VersionedEventList otherVersionedEventList = (VersionedEventList) other;

        // state check
        return super.equals(otherVersionedEventList)
                && eventListStateList.equals(otherVersionedEventList.eventListStateList)
                && currentStatePointer == otherVersionedEventList.currentStatePointer;
    }
}
