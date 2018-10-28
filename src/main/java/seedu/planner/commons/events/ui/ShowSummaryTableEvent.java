package seedu.planner.commons.events.ui;

import javafx.collections.ObservableList;
import seedu.planner.commons.events.BaseEvent;
import seedu.planner.ui.SummaryEntry;

/**
 * An event that requests to show the summary display
 */
public class ShowSummaryTableEvent extends BaseEvent {

    public final ObservableList<SummaryEntry> data;

    public ShowSummaryTableEvent(ObservableList<SummaryEntry> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
