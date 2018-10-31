//@@author QzSG
package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.ReadOnlyTaskBook;

/** Indicates a ExpenseBook online restore request*/
public class TaskBookOnlineRestoreEvent extends BaseEvent {

    public final ReadOnlyTaskBook data;

    public TaskBookOnlineRestoreEvent(ReadOnlyTaskBook data) {
        this.data = data;
    }
    @Override
    public String toString() {
        return "Restoring online backup";
    }
}
