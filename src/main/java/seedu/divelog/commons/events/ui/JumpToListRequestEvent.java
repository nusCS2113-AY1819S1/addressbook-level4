package seedu.divelog.commons.events.ui;

import seedu.divelog.commons.core.index.Index;
import seedu.divelog.commons.events.BaseEvent;

/**
 * Indicates a request to jump to the list of dive sessions
 */
public class JumpToListRequestEvent extends BaseEvent {

    public final int targetIndex;

    public JumpToListRequestEvent(Index targetIndex) {
        this.targetIndex = targetIndex.getZeroBased();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

}
