package seedu.address.commons.events.ui;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.BaseEvent;

/**
 * Indicates a request to jump to the list of groups.
 */
public class JumpToGroupListRequestEvent extends BaseEvent {

    public final int targetIndex;

    /**
     * Receives target index value for event.
     *
     * @param targetIndex Contains value to set {@code this.targetIndex} with.
     */
    public JumpToGroupListRequestEvent(Index targetIndex) {
        this.targetIndex = targetIndex.getZeroBased();
    }

    /**
     * Returns class's simple name.
     *
     * @return Class's simple name.
     */
    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

}
