package seedu.planner.commons.events.ui;

import seedu.planner.commons.events.BaseEvent;

/**
 * An event that requests to show the summary display
 */
public class ShowSummaryTableEvent extends BaseEvent {

    public ShowSummaryTableEvent() {

    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
