package seedu.planner.commons.events.ui;

import seedu.planner.commons.core.index.Index;
import seedu.planner.commons.events.BaseEvent;

/**
 * Indicates a request to jump to the list of records
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
