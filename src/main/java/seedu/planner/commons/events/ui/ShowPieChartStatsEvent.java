package seedu.planner.commons.events.ui;

import javafx.collections.ObservableList;
import seedu.planner.commons.events.BaseEvent;
import seedu.planner.model.summary.CategoryStatistic;

/**
 * An event that requests the pie chart panel to be displayed and created
 */
public class ShowPieChartStatsEvent extends BaseEvent {

    public final ObservableList<CategoryStatistic> data;

    public ShowPieChartStatsEvent(ObservableList<CategoryStatistic> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
