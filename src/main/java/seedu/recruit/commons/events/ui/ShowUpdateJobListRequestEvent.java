package seedu.recruit.commons.events.ui;

import javafx.collections.ObservableList;

import seedu.recruit.commons.events.BaseEvent;
import seedu.recruit.model.Model;
import seedu.recruit.model.ModelManager;
import seedu.recruit.model.company.Company;
import seedu.recruit.model.joboffer.JobOffer;

/**
 * An event requesting to update job list.
 */
public class ShowUpdateJobListRequestEvent extends BaseEvent {

    public ShowUpdateJobListRequestEvent(Model model) {
        ObservableList<JobOffer> updatedJobList = model.getFilteredCompanyJobList();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
