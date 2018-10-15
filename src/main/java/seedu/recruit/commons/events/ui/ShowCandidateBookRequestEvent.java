package seedu.recruit.commons.events.ui;

import seedu.recruit.commons.events.BaseEvent;
import seedu.recruit.model.candidate.Candidate;
import seedu.recruit.ui.CandidateDetailsPanel;

/**
 * Represents a selection change in the Company Details Panel
 */
public class ShowCandidateBookRequestEvent extends BaseEvent {

    private final CandidateDetailsPanel newSelection;

    public ShowCandidateBookRequestEvent(CandidateDetailsPanel newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    public CandidateDetailsPanel getNewSelection() {
        return newSelection;
    }
}
