package seedu.planner.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.planner.model.record.Date;
import seedu.planner.model.record.Record;
import seedu.planner.model.record.UniqueRecordList;
import seedu.planner.model.summary.Summary;
import seedu.planner.model.summary.SummaryMap;

/**
 * Wraps all data at the planner-book level
 * Duplicates are not allowed (by .isSameRecord comparison)
 */
public class FinancialPlanner implements ReadOnlyFinancialPlanner {

    private final UniqueRecordList records;
    private SummaryMap summaryMap;

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        records = new UniqueRecordList();
    }

    public FinancialPlanner() {}

    /**
     * Creates an FinancialPlanner using the Records in the {@code toBeCopied}
     */
    public FinancialPlanner(ReadOnlyFinancialPlanner toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the record list with {@code records}.
     * {@code records} must not contain duplicate records.
     */
    public void setRecords(List<Record> records) {
        this.records.setRecords(records);
    }

    /**
     * Resets the existing data of this {@code FinancialPlanner} with {@code newData}.
     */
    public void resetData(ReadOnlyFinancialPlanner newData) {
        requireNonNull(newData);

        setRecords(newData.getRecordList());
        setSummaryMap(newData.getSummaryMap());
    }

    //// record-level operations

    /**
     * Returns true if a record with the same identity as {@code record} exists in the financial planner.
     */
    public boolean hasRecord(Record record) {
        requireNonNull(record);
        return records.contains(record);
    }

    /**
     * Adds a record to the financial planner.
     * The record must not already exist in the financial planner.
     */
    public void addRecord(Record p) {
        records.add(p);
    }

    /**
     * Replaces the given record {@code target} in the list with {@code editedRecord}.
     * {@code target} must exist in the financial planner.
     * The record identity of {@code editedRecord} must not be the same as another existing record
     * in the financial planner.
     */
    public void updateRecord(Record target, Record editedRecord) {
        requireNonNull(editedRecord);

        records.setRecord(target, editedRecord);
    }

    /**
     * Removes {@code key} from this {@code FinancialPlanner}.
     * {@code key} must exist in the financial planner.
     */
    public void removeRecord(Record key) {
        records.remove(key);
    }

    //// summary related operations

    /**
     * Add the record in the summary map
     */
    public void addRecordToSummary(Record p) {
        summaryMap.add(p);
    }

    /**
     * Remove the record from the summary map
     */
    public void removeRecordFromSummary(Record key) {
        summaryMap.remove(key);
    }

    /**
     * Update summary map to reflect change in {@code records}
     */
    public void updateSummary(Record target, Record editedRecord) {
        summaryMap.update(target, editedRecord);
    }

    //TODO: Remove this once fixed bug in storage and combined all 3
    public void setSummaryMap(SummaryMap summaryMap) {
        this.summaryMap = summaryMap;
    }

    public List<Summary> getSummaryList(Date startDate, Date endDate) {
        return summaryMap.getSummaryList(startDate, endDate);
    }

    //// util methods

    @Override
    public String toString() {
        return records.asUnmodifiableObservableList().size() + " records";
        // TODO: refine later
    }

    @Override
    public ObservableList<Record> getRecordList() {
        return records.asUnmodifiableObservableList();
    }

    // TODO make it return a read only map
    @Override
    public SummaryMap getSummaryMap() { return summaryMap; }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FinancialPlanner // instanceof handles nulls
                && records.equals(((FinancialPlanner) other).records));
    }

    @Override
    public int hashCode() {
        return records.hashCode();
    }
}
