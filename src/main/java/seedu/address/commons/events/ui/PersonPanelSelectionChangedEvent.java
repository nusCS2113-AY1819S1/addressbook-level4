package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.candidate.Candidate;

/**
 * Represents a selection change in the Candidate List Panel
 */
public class PersonPanelSelectionChangedEvent extends BaseEvent {


    private final Candidate newSelection;

    public PersonPanelSelectionChangedEvent(Candidate newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    public Candidate getNewSelection() {
        return newSelection;
    }
}
