package seedu.recruit.commons.events.ui;

import seedu.recruit.commons.events.BaseEvent;
import seedu.recruit.model.joboffer.JobOffer;

/**
 * Represents a selection change in a company's job list in CompanyJobDetailsPanel
 */
public class CompanyJobListDetailsPanelSelectionChangedEvent extends BaseEvent {

    private final JobOffer newSelection;

    public CompanyJobListDetailsPanelSelectionChangedEvent(JobOffer newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    public JobOffer getNewSelection() {
        return newSelection;
    }
}
