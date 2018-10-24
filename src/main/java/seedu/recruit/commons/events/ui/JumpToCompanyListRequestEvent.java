package seedu.recruit.commons.events.ui;

import seedu.recruit.commons.core.index.Index;
import seedu.recruit.commons.events.BaseEvent;

/**
 * Indicates a request to jump to the list of companies
 */
public class JumpToCompanyListRequestEvent extends BaseEvent {

    public final int targetIndex;

    public JumpToCompanyListRequestEvent(Index targetIndex) {
        this.targetIndex = targetIndex.getZeroBased();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

}
