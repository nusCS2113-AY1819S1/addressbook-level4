package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.ReadOnlyJobBook;

/** Indicates the JobBook in the model has changed*/
public class JobBookChangedEvent extends BaseEvent {

    public final ReadOnlyJobBook data;

    public JobBookChangedEvent(ReadOnlyJobBook data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of job offers " + data.getJobOfferList().size();
    }
}
