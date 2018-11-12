package seedu.planner.commons.events.ui;

import seedu.planner.commons.events.BaseEvent;
import seedu.planner.model.summary.SummaryList;
//@@author tenvinc
/**
 * An event that requests to show the summary display
 */
public class ShowSummaryTableEvent extends BaseEvent {

    public final SummaryList data;
    public final String totalLabel;
    public final String tabTitle;

    public ShowSummaryTableEvent(SummaryList data, String totalLabel, String tabTitle) {
        this.data = data;
        this.totalLabel = totalLabel;
        this.tabTitle = tabTitle;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
