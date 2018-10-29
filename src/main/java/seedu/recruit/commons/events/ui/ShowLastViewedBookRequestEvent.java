package seedu.recruit.commons.events.ui;

import seedu.recruit.commons.events.BaseEvent;

/**
 * An event requesting to switch book.
 */
public class ShowLastViewedBookRequestEvent extends BaseEvent {

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
