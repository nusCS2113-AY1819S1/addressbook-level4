package seedu.planner.commons.events.ui;

import javafx.collections.ObservableList;
import seedu.planner.commons.events.BaseEvent;
import seedu.planner.model.summary.CategoryStatistic;
//@@author tenvinc
/**
 * Indicates a request to update the welcome panel with new information
 */
public class UpdateWelcomePanelEvent extends BaseEvent {

    public final ObservableList<CategoryStatistic> data;
    public final String monthLabel;

    public UpdateWelcomePanelEvent(ObservableList<CategoryStatistic> data, String monthLabel) {
        this.data = data;
        this.monthLabel = monthLabel;
    }

    @Override
    public String toString() {
        return String.format("Updated the welcome panel with information on this month! %d records tracked!",
                data.size());
    }
}
