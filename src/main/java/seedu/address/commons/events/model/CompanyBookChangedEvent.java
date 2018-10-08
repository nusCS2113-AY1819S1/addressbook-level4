package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.ReadOnlyCompanyBook;

/** Indicates the CompanyBook in the model has changed*/
public class CompanyBookChangedEvent extends BaseEvent {

    public final ReadOnlyCompanyBook data;

    public CompanyBookChangedEvent(ReadOnlyCompanyBook data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of companies " + data.getCompanyList().size();
    }
}
