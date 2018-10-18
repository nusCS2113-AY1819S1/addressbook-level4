package seedu.address.commons.events.storage;

import seedu.address.commons.events.BaseEvent;

//@@author QzSG
/**
 * Indicates an exception during a file restore
 */
public class DataRestoreExceptionEvent extends BaseEvent {

    public final Exception exception;

    public DataRestoreExceptionEvent(Exception exception) {
        this.exception = exception;
    }

    @Override
    public String toString() {
        return exception.toString();
    }

}
