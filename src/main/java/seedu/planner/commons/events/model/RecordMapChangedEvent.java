package seedu.planner.commons.events.model;

import seedu.planner.commons.events.BaseEvent;
import seedu.planner.logic.autocomplete.CustomSuggestionProvider;
import seedu.planner.model.ReadOnlyFinancialPlanner;

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
        return data.getRecordMap().getAsReadOnlyTagMap().size() + " tags, "
                + data.getRecordMap().getAsReadOnlyDateMap().size() + " dates, "
                + data.getRecordMap().getAsReadOnlyNameMap().size() + " names";
    }
}
