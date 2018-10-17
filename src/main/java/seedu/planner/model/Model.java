package seedu.planner.model;

import java.util.List;
import java.util.function.Predicate;

import javafx.collections.ObservableList;

import seedu.planner.model.record.Date;
import seedu.planner.model.record.Limit;
import seedu.planner.model.record.Record;
import seedu.planner.model.summary.Summary;

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
     * Returns true if a limit with the same dates exists.
     */
    boolean hasSameDateLimit(Limit limitIn);
    /**
     * Deletes the given record.
     * The record must exist in the financial planner.
     */
    void deleteRecord(Record target);

    void deleteListRecord(List<Record> targetList);

    /**
     * Adds the given record.
     * {@code record} must not already exist in the financial planner.
     */

    void addRecord(Record record);

    /**
     * To add the limit.
     * @param limitIn
     */
    void addLimit(Limit limitIn);
    /**
     * Replaces the given record {@code target} with {@code editedRecord}.
     * {@code target} must exist in the financial planner.
     * The record identity of {@code editedRecord} must not be the same as
     * another existing record in the financial planner.
     */
    void updateRecord(Record target, Record editedRecord);

    /**
     * To delete a existing limit.
     * @param target
     */
    void deleteLimit(Limit target);

    /**
     * This function will check whether the limit have been exceeded.
     * @param limitIn
     * @return
     */
    boolean isExceededLimit (Limit limitIn);

    /** Returns an unmodifiable view of the filtered record list */
    ObservableList<Record> getFilteredRecordList();

    /** Returns an unmodifiable view of the filtered limit list */
    ObservableList<Limit> getLimitList();

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

    ObservableList<Summary> getSummaryList(Date startDate, Date endDate);
}
