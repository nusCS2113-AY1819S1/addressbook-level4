package seedu.planner.commons.events.model;

import seedu.planner.commons.events.BaseEvent;
import seedu.planner.model.ReadOnlyFinancialPlanner;

/**
 * Indicate the directory path of the model changed.
 */
public class DirectoryPathChangedEvent extends BaseEvent {
    public final ReadOnlyFinancialPlanner data;

    public DirectoryPathChangedEvent(ReadOnlyFinancialPlanner data) {
        this.data = data;
    }
    @Override
    public String toString() {
        return String.format("The directory path is changed to %1$s", data.getDirectoryPath().getDirectoryPathValue());
    }
}
