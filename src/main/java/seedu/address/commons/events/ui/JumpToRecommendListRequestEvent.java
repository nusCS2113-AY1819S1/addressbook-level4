package seedu.address.commons.events.ui;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.BaseEvent;

/**
 * Indicates a request to jump to the list of workouts
 */
public class JumpToRecommendListRequestEvent extends BaseEvent {

    public final int targetIndex;

    public JumpToRecommendListRequestEvent(Index targetIndex) {
        this.targetIndex = targetIndex.getZeroBased();
    }

    public JumpToRecommendListRequestEvent(int targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

}
