package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;

/** Indicates the Request Panel has been toggled.*/
public class RequestPanelChangedEvent extends BaseEvent {

    public RequestPanelChangedEvent() {}

    @Override
    public String toString() {
        return "Request Panel Toggled.";
    }
}
