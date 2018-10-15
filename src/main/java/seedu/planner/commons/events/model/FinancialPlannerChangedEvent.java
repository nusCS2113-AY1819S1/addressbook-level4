package seedu.planner.commons.events.model;

import javafx.collections.ObservableList;
import seedu.planner.commons.events.BaseEvent;
import seedu.planner.model.ReadOnlyFinancialPlanner;
import seedu.planner.model.record.Record;
import seedu.planner.model.record.UniqueRecordList;

/** Indicates the FinancialPlanner in the model has changed*/
public class FinancialPlannerChangedEvent extends BaseEvent {

    public final ReadOnlyFinancialPlanner data;

    public FinancialPlannerChangedEvent(ReadOnlyFinancialPlanner data) {
        this.data = data;
    }

    @Override
    public String toString() { return "number of records " + data.getRecordList().size(); }
}
