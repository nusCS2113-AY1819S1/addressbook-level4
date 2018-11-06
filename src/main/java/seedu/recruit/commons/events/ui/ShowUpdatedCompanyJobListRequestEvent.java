package seedu.recruit.commons.events.ui;

import seedu.recruit.commons.events.BaseEvent;

/**
 * An event requesting to fetch the latest update of job list.
 */
public class ShowUpdatedCompanyJobListRequestEvent extends BaseEvent {

    public final int totalNumberOfJobOffersInSelectedCompany;

    public ShowUpdatedCompanyJobListRequestEvent(int totalNumberOfJobOffersInSelectedCompany) {
        this.totalNumberOfJobOffersInSelectedCompany = totalNumberOfJobOffersInSelectedCompany;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
