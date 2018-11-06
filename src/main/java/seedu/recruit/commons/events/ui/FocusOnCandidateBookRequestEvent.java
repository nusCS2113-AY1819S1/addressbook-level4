package seedu.recruit.commons.events.ui;

import seedu.recruit.commons.events.BaseEvent;

/**
 * An event requesting to focus on the Candidate Book.
 * Does not deselect what the user has selected on screen.
 */
public class FocusOnCandidateBookRequestEvent extends BaseEvent {

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
