package seedu.recruit.commons.events.ui;

import seedu.recruit.commons.events.BaseEvent;

/**
 * An event requesting to view the Candidate Book.
 * Deselects whatever selection made on screen.
 */
public class ShowCandidateBookRequestEvent extends BaseEvent {

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
