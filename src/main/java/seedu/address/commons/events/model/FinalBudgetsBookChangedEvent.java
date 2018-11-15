package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.ReadOnlyFinalBudgetBook;

/** Indicates the AddressBook in the model has changed*/
public class FinalBudgetsBookChangedEvent extends BaseEvent {

    public final ReadOnlyFinalBudgetBook data;

    public FinalBudgetsBookChangedEvent(ReadOnlyFinalBudgetBook data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of final budgets " + data.getClubBudgetsList().size();
    }
}
