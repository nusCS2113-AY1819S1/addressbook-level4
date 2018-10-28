package seedu.recruit.commons.events.ui;

import seedu.recruit.commons.events.BaseEvent;

/**
 * An event requesting to view the shortlist panel.
 */
public class ShowShortlistPanelRequestEvent extends BaseEvent {

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
