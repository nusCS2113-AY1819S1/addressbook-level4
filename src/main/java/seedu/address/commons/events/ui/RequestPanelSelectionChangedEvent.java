package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.request.Request;

/**
 * Represents a selection change in the Book List Panel
 */
public class RequestPanelSelectionChangedEvent extends BaseEvent {


    private final Request newSelection;

    public RequestPanelSelectionChangedEvent(Request newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    public Request getNewSelection() {
        return newSelection;
    }
}
