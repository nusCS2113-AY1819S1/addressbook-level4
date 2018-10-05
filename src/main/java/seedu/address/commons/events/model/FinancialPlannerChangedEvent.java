package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.ReadOnlyFinancialPlanner;

/** Indicates the FinancialPlanner in the model has changed*/
public class FinancialPlannerChangedEvent extends BaseEvent {

    public final ReadOnlyFinancialPlanner data;

    public FinancialPlannerChangedEvent(ReadOnlyFinancialPlanner data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of records " + data.getRecordList().size();
    }
}
