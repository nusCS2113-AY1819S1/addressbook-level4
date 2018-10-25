package seedu.recruit.commons.events.ui;

import seedu.recruit.commons.core.index.Index;
import seedu.recruit.commons.events.BaseEvent;

/**
 * Indicates a request to jump to the list of job offers
 */
public class JumpToCompanyJobListRequestEvent extends BaseEvent {

    public final int targetIndex;

    public JumpToCompanyJobListRequestEvent(Index targetIndex) {
        this.targetIndex = targetIndex.getZeroBased();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

}
