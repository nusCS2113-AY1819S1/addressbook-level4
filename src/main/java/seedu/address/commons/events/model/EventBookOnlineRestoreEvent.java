//@@author QzSG
package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.ReadOnlyEventBook;

/** Indicates a EventBook online restore request*/
public class EventBookOnlineRestoreEvent extends BaseEvent {

    public final ReadOnlyEventBook data;

    public EventBookOnlineRestoreEvent(ReadOnlyEventBook data) {
        this.data = data;
    }
    @Override
    public String toString() {
        return "Restoring online backup";
    }
}
