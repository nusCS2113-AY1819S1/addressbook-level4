package seedu.address.commons.events.social;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.BaseEvent;

/** Registers a new friend being added to the friend list */
public class AddFriendEvent extends BaseEvent {

    public final int targetIndex;

    public AddFriendEvent(Index targetIndex) {
        this.targetIndex = targetIndex.getZeroBased();
    }

    @Override
    public String toString() {
        return "Friend successfully added.";
    }
}
