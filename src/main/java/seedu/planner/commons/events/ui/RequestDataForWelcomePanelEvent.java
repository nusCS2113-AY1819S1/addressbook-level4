package seedu.planner.commons.events.ui;

import seedu.planner.commons.events.BaseEvent;

/** Indicates that a UI component has not received the required data and needs the data from the model */
public class RequestDataForWelcomePanelEvent extends BaseEvent {

    public final String data;

    public RequestDataForWelcomePanelEvent(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return data;
    }
}
