package seedu.planner.model;

import javafx.collections.ObservableList;
import seedu.planner.model.record.Limit;

/**
 * The readonly version of the limit list.
 */
public interface ReadOnlyLimitList {
    /**
     * this list should not contain limits with same dates
     * @return
     */
    ObservableList<Limit> getLimitList();
}
