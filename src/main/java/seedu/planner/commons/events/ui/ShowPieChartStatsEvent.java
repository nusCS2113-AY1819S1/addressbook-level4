package seedu.planner.commons.events.ui;

import seedu.planner.commons.events.BaseEvent;

/**
 * An event that requests the pie chart panel to be displayed and created
 */
public class ShowPieChartStatsEvent extends BaseEvent {
    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
