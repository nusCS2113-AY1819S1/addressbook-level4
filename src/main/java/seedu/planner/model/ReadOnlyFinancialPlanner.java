package seedu.planner.model;

import javafx.collections.ObservableList;
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

}
