package seedu.planner.model;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.planner.commons.core.LogsCenter;
import seedu.planner.model.record.Date;
import seedu.planner.model.record.DateBasedLimitList;
import seedu.planner.model.record.DirectoryPath;
import seedu.planner.model.record.Limit;
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
    private DateBasedLimitList limits;
    private DirectoryPath directoryPath;
    private Logger logger = LogsCenter.getLogger(FinancialPlanner.class);

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        records = new UniqueRecordList();
        summaryMap = new SummaryMap();
        limits = new DateBasedLimitList();
        directoryPath = new DirectoryPath();
    }

    public FinancialPlanner() {}

    /**
     * Creates an FinancialPlanner using the Records in the {@code toBeCopied}
     */
    public FinancialPlanner(ReadOnlyFinancialPlanner toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //=======list overwrite operations==================================================================================

    /**
     * Replaces the contents of the record list with {@code records}.
     * {@code records} must not contain duplicate records.
     */
    public void setRecords(List<Record> records) {
        this.records.setRecords(records);
    }

    public void setLimits(List<Limit> limits) {
        this.limits.setLimits(limits);
    }

    /**
     * Resets the existing data of this {@code FinancialPlanner} with {@code newData}.
     */
    public void resetData(ReadOnlyFinancialPlanner newData) {
        requireNonNull(newData);

        setRecords(newData.getRecordList());
        setSummaryMap(newData.getSummaryMap());
        setDirectoryPath(newData.getDirectoryPath().getDirectoryPathValue());
        setLimits(newData.getLimitList());
    }

    /**
     * Resets the existing data of this {@code FinancialPlanner} with the given parameters
     * @param recordList
     * @param summaryMap
     * @param directoryPath
     */
    public void resetData(UniqueRecordList recordList, SummaryMap summaryMap,
                          DateBasedLimitList limitList, DirectoryPath directoryPath) {
        requireNonNull(recordList);
        requireNonNull(summaryMap);
        requireNonNull(directoryPath);
        requireNonNull(limitList);

        setRecords(recordList.asUnmodifiableObservableList());
        setSummaryMap(summaryMap);
        setDirectoryPath(directoryPath);
        setLimitList(limitList);
    }

    //======record-level operations=====================================================================================

    /**
     * Returns true if a record with the same identity as {@code record} exists in the financial planner.
     */
    public boolean hasRecord(Record record) {
        requireNonNull(record);
        return records.contains(record);
    }

    /**
     * returns true if there are two limits share the same dates.
     * @param limitin
     * @return
     */
    public boolean hasSameDateLimit (Limit limitin) {
        requireNonNull(limitin);
        return limits.hasSameDatesLimit(limitin);
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
     * The record identity of {@code editedRecord}
     * must not be the same as another existing record in the financial planner.
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

    /**
     * Sorts the records in this {@code FinancialPlanner}.
     */
    public void sortRecords(String category, Boolean ascending) {
        records.sortRecords(category, ascending);
    }

    //======summary related operations==================================================================================
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

    public void setSummaryMap(SummaryMap summaryMap) {
        this.summaryMap = summaryMap;
    }

    public ObservableList<Summary> getSummaryList(Date startDate, Date endDate) {
        return summaryMap.getSummaryList(startDate, endDate);
    }

    public void setLimitList(DateBasedLimitList limitList) {
        this.limits = limitList;
    }


    /**
     * Add a limit to the financial planner.
     * The newly added limit can not share same dates with the rest.
     * @param limit
     */
    public void addLimit(Limit limit) {
        limits.add(limit); }

    /**
     * check whether the records' money has already exceeded the limit.
     * return true if limit exceeded.
     * @param limit
     * @return
     */
    public boolean isExceededLimit (Limit limit) {
        Double recordsMoney = 0.0;

        for (Record i: records) {
            if (limit.isInsideDatePeriod(i)) {
                recordsMoney += i.getMoneyFlow().toDouble();
            }
        }

        return (limit.isExceeded(recordsMoney));
    }

    /**
     * Removes a limit from the list,
     * @param limitin must already existed.
     */
    public void removeLimit(Limit limitin) {
        limits.remove(limitin); }

    //======Directory Path methods.=====================================================================================

    /**
     * Check if the directory == null or not.
     * @return boolean value.
     */
    public boolean isDirectorySet() {
        return directoryPath.isDirectorySet();
    }

    /**
     * Set the Directory to the chosen Directory path.
     * @param path the given Directory path.
     */
    public void setDirectoryPath(String path) {
        directoryPath.updateDirectoryPath(path);
    }

    /**
     * Set the Directory to the chosen Directory path.
     * @param path the given Directory path.
     */
    public void setDirectoryPath(DirectoryPath path) {
        directoryPath.updateDirectoryPath(path);
    }

    /**
     * Remove the chosen Directory and set back to the User's home directory.
     */
    public void removeDirectoryPath() {
        directoryPath.removeDirectoryPath();
    }

    //======util methods================================================================================================

    @Override
    public String toString() {
        return records.asUnmodifiableObservableList().size() + " records";
    }

    @Override
    public ObservableList<Record> getRecordList() {
        return records.asUnmodifiableObservableList();
    }

    // TODO make it return a read only map
    @Override
    public SummaryMap getSummaryMap() {
        return summaryMap;
    }

    @Override
    public ObservableList<Limit> getLimitList () {
        return limits.asUnmodifiableObservableList();
    }

    @Override
    public DirectoryPath getDirectoryPath() {
        return directoryPath.getDirectoryPath();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FinancialPlanner // instanceof handles nulls
                && records.equals(((FinancialPlanner) other).records))
                && summaryMap.equals(((FinancialPlanner) other).summaryMap);
    }

    @Override
    public int hashCode() {
        return records.hashCode();
    }
}
