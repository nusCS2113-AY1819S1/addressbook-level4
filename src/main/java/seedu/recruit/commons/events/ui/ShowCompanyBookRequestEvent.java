package seedu.recruit.commons.events.ui;

import seedu.recruit.commons.events.BaseEvent;
import seedu.recruit.ui.CompanyJobDetailsPanel;

/**
 * Represents a selection change in the Company Details Panel
 */
public class ShowCompanyBookRequestEvent extends BaseEvent {

    private final CompanyJobDetailsPanel newSelection;

    public ShowCompanyBookRequestEvent(CompanyJobDetailsPanel newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    public CompanyJobDetailsPanel getNewSelection() {
        return newSelection;
    }
}
