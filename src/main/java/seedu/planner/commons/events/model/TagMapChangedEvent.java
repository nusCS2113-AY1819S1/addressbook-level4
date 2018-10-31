package seedu.planner.commons.events.model;

import seedu.planner.commons.events.BaseEvent;
import seedu.planner.model.ReadOnlyFinancialPlanner;
import seedu.planner.ui.CustomSuggestionProvider;

/** Indicates that the summary map in the model has been changed*/
public class TagMapChangedEvent extends BaseEvent {

    public final ReadOnlyFinancialPlanner data;

    public TagMapChangedEvent(ReadOnlyFinancialPlanner tagMap) {
        this.data = tagMap;
        CustomSuggestionProvider.updateTagSet(tagMap.getTagMap());
    }

    @Override
    public String toString() {
        return "number of tags added: " + data.getTagMap().size();
    }
}
