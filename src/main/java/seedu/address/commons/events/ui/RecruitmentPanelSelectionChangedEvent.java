package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.recruitment.Recruitment;

/**
 * Represents a selection change in the recruitment List Panel
 */
public class RecruitmentPanelSelectionChangedEvent extends BaseEvent {


    private final Recruitment newSelection;

    public RecruitmentPanelSelectionChangedEvent(Recruitment newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

}
