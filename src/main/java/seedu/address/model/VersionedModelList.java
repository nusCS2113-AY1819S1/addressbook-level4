package seedu.address.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * {@code VersionedModelList} keeps track of a list of models committed defined in ModelTypes.java.
 */

public class VersionedModelList {
    public static final String MESSAGE_NO_REDOABLE_STATE_EXCEPTION =
            "Current state pointer at start of storage list in all Storages, unable to redo.";
    public static final String MESSAGE_NO_UNDOABLE_STATE_EXCEPTION =
            "Current state pointer at start of storage list in all Storages, unable to undo.";
    private boolean hasUndo;
    private int currentStatePointer;
    private List<Set<ModelTypes>> modelTypesStateList;

    public VersionedModelList() {
        currentStatePointer = 0;
        modelTypesStateList = new ArrayList<>();
        hasUndo = false;
    }

    //-----------------------------------------------------------------------------

    /**
     *  Undo Versioned Model List
     */
    public void undo() {
        if (!canUndoStorage()) {
            throw new NoUndoableStateException();
        }
        hasUndo = true;
        currentStatePointer--;
    }

    /**
     *  Redo Versioned Model List
     */
    public void redo() {
        if (!canRedoStorage()) {
            throw new NoRedoableStateException();
        }
        hasUndo = false;
        currentStatePointer++;
    }

    /**
     * Add to the list to keep track of which model is committed.
     * @param type of storage model committed
     */
    public void add (ModelTypes type) {
        Set<ModelTypes> set = new HashSet<>();
        set.add(type);
        modelTypesStateList.add(set);
        if (hasUndo) {
            modelTypesStateList.remove(currentStatePointer);
            hasUndo = false;
        }
        currentStatePointer = modelTypesStateList.size();
    }

    /**
     * Add to the list to keep track of which model is committed.
     * @param set of the types of storage models committed
     */
    public void addMultiple (Set<ModelTypes> set) {
        modelTypesStateList.add(set);
        if (hasUndo) {
            modelTypesStateList.remove(currentStatePointer);
            hasUndo = false;
        }
        currentStatePointer = modelTypesStateList.size();
    }

    /**
     *  Used to check which model was last committed with a command.
     *  Important for undo and redo class.
     */
    public Set<ModelTypes> getLastCommitType () {
        if (!canUndoStorage()) {
            throw new NoUndoableStateException();
        }
        return modelTypesStateList.get(currentStatePointer - 1);
    }

    public Set<ModelTypes> getNextCommitType () {
        if (!canRedoStorage()) {
            throw new NoRedoableStateException();
        }
        return modelTypesStateList.get(currentStatePointer);
    }

    /**
     * Returns true if {@code redo()} has states to redo in any of the model.
     */
    public boolean canRedoStorage() {
        return currentStatePointer < modelTypesStateList.size();
    }

    /**
     * Returns true if {@code undo()} has states to undo in any of the model.
     */
    public boolean canUndoStorage() {
        return currentStatePointer > 0;
    }


    /**
     * Thrown when trying to {@code undo()} but can't.
     */
    public static class NoUndoableStateException extends RuntimeException {
        private NoUndoableStateException() {
            super(MESSAGE_NO_UNDOABLE_STATE_EXCEPTION);
        }
    }

    /**
     * Thrown when trying to {@code redo()} but can't.
     */
    public static class NoRedoableStateException extends RuntimeException {
        private NoRedoableStateException() {
            super(MESSAGE_NO_REDOABLE_STATE_EXCEPTION);
        }
    }
}
