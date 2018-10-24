package seedu.recruit.commons.events.ui;

import seedu.recruit.commons.events.BaseEvent;
import seedu.recruit.model.company.Company;

/**
 * Represents a selection change in the company list in CompanyJobDetailsPanel
 */
public class CompanyListDetailsPanelSelectionChangedEvent extends BaseEvent {

    private final Company newSelection;

    public CompanyListDetailsPanelSelectionChangedEvent(Company newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    public Company getNewSelection() {
        return newSelection;
    }
}
