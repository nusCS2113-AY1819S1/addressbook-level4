package seedu.divelog.commons.events.ui;

import seedu.divelog.commons.events.BaseEvent;
import seedu.divelog.model.dive.DiveSession;

/**
 * Represents a selection change in the Dive Session List Panel
 */
public class DivePanelSelectionChangedEvent extends BaseEvent {


    private final DiveSession newSelection;

    public DivePanelSelectionChangedEvent(DiveSession newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    public DiveSession getNewSelection() {
        return newSelection;
    }
}
