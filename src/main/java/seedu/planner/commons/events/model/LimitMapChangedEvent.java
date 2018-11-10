package seedu.planner.commons.events.model;

import seedu.planner.commons.events.BaseEvent;
import seedu.planner.logic.autocomplete.CustomSuggestionProvider;
import seedu.planner.model.ReadOnlyFinancialPlanner;

public class LimitMapChangedEvent extends BaseEvent {

    public final ReadOnlyFinancialPlanner data;

    public LimitMapChangedEvent(ReadOnlyFinancialPlanner newData) {
        this.data = newData;
        CustomSuggestionProvider.updateLimitMap(newData.getLimitMap());
    }

    @Override
    public String toString() {
        return "Number of Limits Added: " + data.getLimitMap().size();
    }
}
