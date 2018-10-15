package seedu.planner.commons.events.model;

import seedu.planner.commons.events.BaseEvent;
import seedu.planner.model.ReadOnlyFinancialPlanner;
import seedu.planner.model.summary.SummaryMap;

/** Indicates that the summary map in the model has been changed*/
public class SummaryMapChangedEvent extends BaseEvent {

    public final ReadOnlyFinancialPlanner data;

    public SummaryMapChangedEvent(ReadOnlyFinancialPlanner summaryMap) {
        this.data = summaryMap;
    }

    @Override
    public String toString() { return "number of dates included in summary: " + data.getSummaryMap().size(); }
}
