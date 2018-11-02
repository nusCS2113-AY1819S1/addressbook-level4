package seedu.planner.model;

import java.util.ArrayList;
import java.util.List;

/**
 * {@code FinancialPlanner} that keeps track of its own history.
 */
public class VersionedFinancialPlanner extends FinancialPlanner {

    private final List<ReadOnlyFinancialPlanner> financialPlannerStateList;
    private int currentStatePointer;

    public VersionedFinancialPlanner(ReadOnlyFinancialPlanner initialState) {
        super(initialState);

        financialPlannerStateList = new ArrayList<>();
        financialPlannerStateList.add(new FinancialPlanner(initialState));
        currentStatePointer = 0;
    }

    /**
     * Saves a copy of the current {@code FinancialPlanner} state at the end of the state list.
     * Undone states are removed from the state list.
     */
    public void commit() {
        removeStatesAfterCurrentPointer();
        financialPlannerStateList.add(new FinancialPlanner(this));
        currentStatePointer++;
    }

    private void removeStatesAfterCurrentPointer() {
        financialPlannerStateList.subList(currentStatePointer + 1, financialPlannerStateList.size()).clear();
    }

    /**
     * Restores the financial planner to its previous state.
     */
    public void undo() {
        if (!canUndo()) {
            throw new NoUndoableStateException();
        }
        currentStatePointer--;
        resetData(financialPlannerStateList.get(currentStatePointer));
    }

    /**
     * Restores the financial planner to its previously undone state.
     */
    public void redo() {
        if (!canRedo()) {
            throw new NoRedoableStateException();
        }
        currentStatePointer++;
        resetData(financialPlannerStateList.get(currentStatePointer));
    }

    /**
     * Returns true if {@code undo()} has financial planner states to undo.
     */
    public boolean canUndo() {
        return currentStatePointer > 0;
    }

    /**
     * Returns true if {@code redo()} has financial planner states to redo.
     */
    public boolean canRedo() {
        return currentStatePointer < financialPlannerStateList.size() - 1;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof VersionedFinancialPlanner)) {
            return false;
        }

        VersionedFinancialPlanner otherVersionedFinancialPlanner = (VersionedFinancialPlanner) other;

        // state check
        return super.equals(otherVersionedFinancialPlanner)
                && financialPlannerStateList.equals(otherVersionedFinancialPlanner.financialPlannerStateList)
                && currentStatePointer == otherVersionedFinancialPlanner.currentStatePointer;
    }

    /**
     * Thrown when trying to {@code undo()} but can't.
     */
    public static class NoUndoableStateException extends RuntimeException {
        private NoUndoableStateException() {
            super("Current state pointer at start of financialPlannerState list, unable to undo.");
        }
    }

    /**
     * Thrown when trying to {@code redo()} but can't.
     */
    public static class NoRedoableStateException extends RuntimeException {
        private NoRedoableStateException() {
            super("Current state pointer at end of financialPlannerState list, unable to redo.");
        }
    }
}
