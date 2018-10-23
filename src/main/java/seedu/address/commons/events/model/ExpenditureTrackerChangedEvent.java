package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.ReadOnlyExpenditureTracker;

/** Indicates that the Expenditure Tracker has been changed*/
public class ExpenditureTrackerChangedEvent extends BaseEvent {

    public final ReadOnlyExpenditureTracker data;

    public ExpenditureTrackerChangedEvent(ReadOnlyExpenditureTracker data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of expenditures " + data.getExpenditureList().size();
    }
}
