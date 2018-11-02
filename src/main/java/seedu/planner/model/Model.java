package seedu.planner.model;

import java.util.function.Predicate;

import javafx.collections.ObservableList;

import seedu.planner.model.record.Record;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Record> PREDICATE_SHOW_ALL_RECORDS = unused -> true;

    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyFinancialPlanner newData);


    /** Returns the FinancialPlanner */
    ReadOnlyFinancialPlanner getFinancialPlanner();

    /**
     * Returns true if a record with the same identity as {@code record} exists in the financial planner.
     */
    boolean hasRecord(Record record);

    /**
     * Deletes the given record.
     * The record must exist in the financial planner.
     */
    void deleteRecord(Record target);

    /**
     * Adds the given record.
     * {@code record} must not already exist in the financial planner.
     */
    void addRecord(Record record);

    /**
     * Replaces the given record {@code target} with {@code editedRecord}.
     * {@code target} must exist in the financial planner.
     * The record identity of {@code editedRecord} must not be the same as
     * another existing record in the financial planner.
     */
    void updateRecord(Record target, Record editedRecord);

    /** Returns an unmodifiable view of the filtered record list */
    ObservableList<Record> getFilteredRecordList();

    /**
     * Updates the filter of the filtered record list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredRecordList(Predicate<Record> predicate);

    /**
     * Returns true if the model has previous financial planner states to restore.
     */
    boolean canUndoFinancialPlanner();

    /**
     * Returns true if the model has undone financial planner states to restore.
     */
    boolean canRedoFinancialPlanner();

    /**
     * Restores the model's financial planner to its previous state.
     */
    void undoFinancialPlanner();

    /**
     * Restores the model's financial planner to its previously undone state.
     */
    void redoFinancialPlanner();

    /**
     * Saves the current financial planner state for undo/redo.
     */
    void commitFinancialPlanner();
}
