package seedu.planner.commons.events.model;

import seedu.planner.commons.events.BaseEvent;
import seedu.planner.model.ReadOnlyFinancialPlanner;


/**indicates that the limitList has been changed*/
public class LimitListChangedEvent extends BaseEvent {
    public final ReadOnlyFinancialPlanner data;

    public LimitListChangedEvent(ReadOnlyFinancialPlanner limitList) {
        this.data = limitList;
    }

    @Override
    public String toString() {
        return "number of limits " + data.getLimitList().size(); }

}
