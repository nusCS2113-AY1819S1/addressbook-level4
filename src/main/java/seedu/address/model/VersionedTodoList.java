package seedu.address.model;

import java.util.ArrayList;
import java.util.List;

/**
 * {@code TodoList} that keeps track of its own history.
 */
public class VersionedTodoList extends TodoList {

    private final List<ReadOnlyTodoList> todoListStateList;
    private int currentStatePointer;

    public VersionedTodoList(ReadOnlyTodoList initialState) {
        super(initialState);

        todoListStateList = new ArrayList<>();
        todoListStateList.add(new TodoList(initialState));
        currentStatePointer = 0;
    }

    /**
     * Saves a copy of the current {@code TodoList} state at the end of the state list.
     * Undone states are removed from the state list.
     */
    public void commit() {
        removeStatesAfterCurrentPointer();
        todoListStateList.add(new TodoList(this));
        currentStatePointer++;
    }

    private void removeStatesAfterCurrentPointer() {
        todoListStateList.subList(currentStatePointer + 1, todoListStateList.size()).clear();
    }

    /**
     * Restores the to-do list to its previous state.
     */
    public void undo() {
        if (!canUndo()) {
            throw new NoUndoableStateException();
        }
        currentStatePointer--;
        resetData(todoListStateList.get(currentStatePointer));
    }

    /**
     * Restores the to-do list to its previously undone state.
     */
    public void redo() {
        if (!canRedo()) {
            throw new NoRedoableStateException();
        }
        currentStatePointer++;
        resetData(todoListStateList.get(currentStatePointer));
    }

    /**
     * Returns true if {@code undo()} has to-do list states to undo.
     */
    public boolean canUndo() {
        return currentStatePointer > 0;
    }

    /**
     * Returns true if {@code redo()} has to-do list states to redo.
     */
    public boolean canRedo() {
        return currentStatePointer < todoListStateList.size() - 1;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof VersionedTodoList)) {
            return false;
        }

        VersionedTodoList otherVersionedTodoList = (VersionedTodoList) other;

        // state check
        return super.equals(otherVersionedTodoList)
                && todoListStateList.equals(otherVersionedTodoList.todoListStateList)
                && currentStatePointer == otherVersionedTodoList.currentStatePointer;
    }

    /**
     * Thrown when trying to {@code undo()} but can't.
     */
    public static class NoUndoableStateException extends RuntimeException {
        private NoUndoableStateException() {
            super("Current state pointer at start of todoListState list, unable to undo.");
        }
    }

    /**
     * Thrown when trying to {@code redo()} but can't.
     */
    public static class NoRedoableStateException extends RuntimeException {
        private NoRedoableStateException() {
            super("Current state pointer at end of todoListState list, unable to redo.");
        }
    }
}
