package seedu.planner.model;

import javafx.collections.ObservableList;

import seedu.planner.model.record.DateBasedLimitList;
import seedu.planner.model.record.DirectoryPath;
import seedu.planner.model.record.Limit;
import seedu.planner.model.record.Record;
import seedu.planner.model.summary.SummaryMap;

/**
 * Unmodifiable view of an financial planner
 */
public interface ReadOnlyFinancialPlanner {

    /**
     * Returns an unmodifiable view of the records list.
     * This list will not contain any duplicate records.
     */
    ObservableList<Record> getRecordList();

    SummaryMap getSummaryMap();

    //TODO: to be removed when storage is combined
    void setSummaryMap(SummaryMap summaryMap);

    ObservableList<Limit> getLimitList();

    /**
     * @return the Directory Path of the Financial Planner.
     */
    public DirectoryPath getDirectoryPath();

    void setLimitList (DateBasedLimitList limitList);
}
