package seedu.planner.commons.events.model;

import seedu.planner.commons.events.BaseEvent;
import seedu.planner.model.ReadOnlyFinancialPlanner;

/**
 * Indicates the Limit Map in the model has changed.
 */
public class LimitMapChangedEvent extends BaseEvent {

    public final ReadOnlyFinancialPlanner data;

    public LimitMapChangedEvent(ReadOnlyFinancialPlanner newData) {
        this.data = newData;
    }

    @Override
    public String toString() {
        return "Number of Limits Added: " + data.getLimitMap().size();
    }
}
