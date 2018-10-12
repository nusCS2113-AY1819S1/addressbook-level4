package seedu.planner.model;

import javafx.collections.ObservableList;

import seedu.planner.model.record.Limit;
import seedu.planner.model.record.Record;



/**
 * Unmodifiable view of an financial planner
 */
public interface ReadOnlyFinancialPlanner {

    /**
     * Returns an unmodifiable view of the records list.
     * This list will not contain any duplicate records.
     */
    ObservableList<Record> getRecordList();
    ObservableList<Limit> getLimitList();
}
