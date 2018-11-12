package seedu.planner.model;

import java.util.List;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.planner.model.record.Date;
import seedu.planner.model.record.Limit;
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
     * Returns true if a limit with the same dates exists.
     */
    boolean hasSameDateLimit(Limit limitIn);
    /**
     * Deletes the given record.
     * The record must exist in the financial planner.
     */
    void deleteRecord(Record target);

    /**
     * Deletes the given list of records.
     * The record must exist in the financial planner.
     */
    void deleteListRecord(List<Record> records);

    /**
     * Adds the given record.
     * {@code record} must not already exist in the financial planner.
     */
    void addRecord(Record record);

    /**
     * Adds the given list of records.
     * Given a list of records, we will only add record which is not existing in the model.
     * {@code record} must not already exist in the financial planner.
     */
    void addListUniqueRecord(List<Record> records);

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
     * Replace the limit inside the limit list according to the given limit dates
     * @param target
     * @param editedLimit
     */
    void updateLimit(Limit target, Limit editedLimit);

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
    /**
     *This function will execute all the limits stored inside storage right now.
     * However, only exceeded limits will be displayed.
     * The final output will be in one string
     * and it will return this string to limit command and display.
     * @return
     */
    String autoLimitCheck ();

    /**
     *This function will execute all the limits stored inside
     * storage right now. The final output will be in one string
     * and it will return this string to limit command and display.
     * @return
     */
    String manualLimitCheck ();
    /**
     * This will generate the output string according to the
     * exceeding condition and limit information.
     * @param isExceeded
     * @param limit
     * @return
     */
    String generateLimitOutput (boolean isExceeded, Double totalMoney, Limit limit);

    /**
     * This function will return the total money spent during that period of limit time.
     * @param limitIn
     * @return
     */
    Double getTotalSpend (Limit limitIn);
    /**
     * Return the limit which has the same dates as input.
     * @param dateStart
     * @param dateEnd
     * @return
     */
    Limit getSameDatesLimit (Date dateStart, Date dateEnd);

    ObservableList<Record> getRecordsThisMonth();

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
     * Updates the filtered record list to sort by the given {@code category}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void sortFilteredRecordList(String category, Boolean reversed);

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

    Month getCurrentMonth();
}
