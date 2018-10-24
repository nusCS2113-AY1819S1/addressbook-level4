package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.ReadOnlyExpenseBook;

/** Indicates the ExpenseBook in the model has changed*/
public class ExpenseBookChangedEvent extends BaseEvent {

    public final ReadOnlyExpenseBook data;

    public ExpenseBookChangedEvent(ReadOnlyExpenseBook data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of expenses " + data.getExpenseList().size();
    }
}
