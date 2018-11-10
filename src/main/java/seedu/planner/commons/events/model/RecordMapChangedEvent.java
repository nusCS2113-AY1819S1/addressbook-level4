package seedu.planner.commons.events.model;

import seedu.planner.commons.events.BaseEvent;
import seedu.planner.model.ReadOnlyFinancialPlanner;
import seedu.planner.logic.autocomplete.CustomSuggestionProvider;

//@author tztzt
/** Indicates that the summary map in the model has been changed*/
public class RecordMapChangedEvent extends BaseEvent {

    public final ReadOnlyFinancialPlanner data;

    public RecordMapChangedEvent(ReadOnlyFinancialPlanner newData) {
        this.data = newData;
        CustomSuggestionProvider.updateRecordMap(newData.getRecordMap());
    }

    @Override
    public String toString() {
        return "number of tags added: " + data.getRecordMap().getAsReadOnlyTagMap().size();
    }
}
