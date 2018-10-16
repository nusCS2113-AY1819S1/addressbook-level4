package seedu.recruit.commons.events.ui;

import seedu.recruit.commons.events.BaseEvent;
import seedu.recruit.model.candidate.Candidate;

/**
 * Represents a selection change in the Candidate Details Panel
 */
public class CandidateDetailsPanelSelectionChangedEvent extends BaseEvent {

    private final Candidate newSelection;

    public CandidateDetailsPanelSelectionChangedEvent(Candidate newSelection) {
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
