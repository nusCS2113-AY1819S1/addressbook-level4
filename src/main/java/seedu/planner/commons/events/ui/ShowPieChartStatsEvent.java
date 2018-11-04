package seedu.planner.commons.events.ui;

import javafx.collections.ObservableList;
import seedu.planner.commons.events.BaseEvent;
import seedu.planner.model.summary.CategoryStatistic;
//@@author tenvinc
/**
 * An event that requests the pie chart panel to be displayed and created
 */
public class ShowPieChartStatsEvent extends BaseEvent {

    public final ObservableList<CategoryStatistic> data;
    public final String startDate;
    public final String endDate;

    public ShowPieChartStatsEvent(ObservableList<CategoryStatistic> data, String startDate, String endDate) {
        this.data = data;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
