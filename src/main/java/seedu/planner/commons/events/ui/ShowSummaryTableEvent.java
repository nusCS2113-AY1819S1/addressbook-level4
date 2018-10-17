package seedu.planner.commons.events.ui;

import javafx.collections.ObservableList;
import seedu.planner.commons.events.BaseEvent;
import seedu.planner.model.summary.Summary;

/**
 * An event that requests to show the summary display
 */
public class ShowSummaryTableEvent extends BaseEvent {

    public final ObservableList<Summary> data;

    public ShowSummaryTableEvent(ObservableList<Summary> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
